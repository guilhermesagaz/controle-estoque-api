package com.br.controleestoqueapi.shared.validator.data;

import com.br.controleestoqueapi.shared.util.DataUtils;
import com.br.controleestoqueapi.shared.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeParseException;

public class StringAsLocalDateValidator implements ConstraintValidator<StringAsLocalDateValid, String> {
    private String value;

    @Override
    public void initialize(StringAsLocalDateValid constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isNotEmpty(value)) {
            try {
                DataUtils.stringToLocalDate(value);
            } catch (DateTimeParseException e) {
                return false;
            }
        }

        return true;
    }
}
