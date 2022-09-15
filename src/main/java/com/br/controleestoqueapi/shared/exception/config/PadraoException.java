package com.br.controleestoqueapi.shared.exception.config;

import java.util.Locale;

public class PadraoException extends Exception {

    private final String messageKey;
    private final Locale locale;

    public PadraoException(String messageKey) {
        this(messageKey, Locale.getDefault());
    }

    public PadraoException(String messageKey, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
    }

    public String getLocalizedMessage() {
        return Messages.getMessageForLocale(messageKey, locale);
    }
}
