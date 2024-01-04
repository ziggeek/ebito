package com.ebito.printed_form.exception;

public class InternaEception extends AbstractException{
    public InternaEception(String message) {
        super(message);
    }

    public InternaEception(String message, Throwable cause) {
        super(message, cause);
    }

    public InternaEception(Throwable cause) {
        super(cause);
    }
}
