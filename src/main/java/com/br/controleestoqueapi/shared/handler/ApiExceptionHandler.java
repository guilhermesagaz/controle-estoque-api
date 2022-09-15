package com.br.controleestoqueapi.shared.handler;

import com.br.controleestoqueapi.service.MessageSourceService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.br.controleestoqueapi.shared.message.MessageSourceKeys.*;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@Autowired
	private MessageSourceService messageService;
	
	//Tratando as validações do BeanValidator
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public List<Erro> onMethodArgumentNotValidException(HttpServletRequest req,
			MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		return criarErrosValidation(bindingResult);
	}
	
	//Exceções que precisam de uma mensagem padrão
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public List<Erro> onIllegalArgumentException(HttpServletRequest req, IllegalArgumentException e) {
		List<Erro> errors = criarErrosException(e, ILLEGAL_ARGUMENT);
		return errors;
	}
	
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(NullPointerException.class)
	public List<Erro> onNullPointerException(HttpServletRequest req, NullPointerException e) {
		List<Erro> errors = criarErrosException(e, NULL_POINTER);
		return errors;
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public List<Erro> onDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException e) {
		List<Erro> errors = criarErrosException(e, REGISTRO_DUPLICADO);
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public List<Erro> onDataAccessApiUsageException(HttpServletRequest req, InvalidDataAccessApiUsageException e) {
		List<Erro> errors = criarErrosException(e, ILLEGAL_ARGUMENT);
		return errors;
	}

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public List<Erro> onHttpRequestMethodNotSupportedException(HttpServletRequest req,
			HttpRequestMethodNotSupportedException e) {
		List<Erro> errors = criarErrosException(e, REQUEST_METHOD);
		return errors;
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Erro> onHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
		List<Erro> errors = criarErrosException(e, MESSAGE_NOT_READABLE);
		return errors;
	}
	
	//Exceções com mensagens padrões
	@ExceptionHandler({ ValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Erro> onValidationException(HttpServletRequest req, ValidationException e) {
		List<Erro> errors = criarErrosException(e);
		return errors;
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Erro> onMethodArgumentTypeMismatchException(HttpServletRequest req,
			MethodArgumentTypeMismatchException e) {

		List<Erro> errors = criarErrosException(e);

		return errors;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object onException(HttpServletRequest req, Exception e) {
		if (req.getServletPath().contains("/views")) {
			return new ModelAndView("error");
		} else {
			List<Erro> errors = criarErrosException(e);
			return errors;
		}
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ EntityNotFoundException.class })
	public List<Erro> onEntityNotFoundException(HttpServletRequest req, EntityNotFoundException e) {
		List<Erro> errors = criarErrosException(e);
		return errors;
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Erro> onConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
		List<Erro> errors = criarErrosException(e);
		return errors;
	}

	private List<Erro> criarErrosValidation(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemDesenvolvedor = "Anotação: '@" + fieldError.getCode() + 
					"' da Classe: " + fieldError.getObjectName();
			String mensagem = messageService.getMessageFieldSource(fieldError);
			erros.add(new Erro(mensagemDesenvolvedor, mensagem));
		}
		return erros;
	}
	
	private List<Erro> criarErrosException(Exception e, String mensagem) {
		List<Erro> errors = new ArrayList<Erro>();
		Throwable cause = findRootCause(e);
		
		String message = cause.getMessage();
		String mensagemDesenvolvedor = e.getLocalizedMessage();
		if(message != null && message.startsWith("{key.")) {
			mensagem = messageService.getMessage(message);
		}else {
			mensagemDesenvolvedor = message;
		}
		if (mensagem == null) {
			mensagemDesenvolvedor = cause.fillInStackTrace().toString();
			mensagem = message;
		}
		if (mensagem.startsWith("{key.")) {
			mensagem = messageService.getMessage(mensagem);
			if(mensagem.equals("Erro de integridade de dados.")){
				if(mensagemDesenvolvedor.contains("=")) {
					var x = mensagemDesenvolvedor.split("=");
					if (x[1].contains(" already ")) {
						var y = x[1].split(" already ");
						mensagem = mensagem + " " + y[0];
					}
				}
			}
		}


		errors.add(new Erro(mensagemDesenvolvedor, mensagem));
		return errors;
	}
	
	private List<Erro> criarErrosException(Exception e) {
		
		return criarErrosException(e,null);
	}
	
	private static Throwable findRootCause(Throwable throwable) {
		Objects.requireNonNull(throwable);
		Throwable rootCause = throwable;
		while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
			rootCause = rootCause.getCause();
		}
		return rootCause;
	}
	

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Erro {
		private String mensagemDesenvolvedor;
		private String mensagem;
	}
}
