package com.ebito.document_generator.exception;

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
