package com.ebito.printed_form.exception;

public abstract class AbstractException extends RuntimeException {
    public AbstractException(String message) {
        super(message);
    }
    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }
    public AbstractException(Throwable cause) {
        super(cause);
    }

}
