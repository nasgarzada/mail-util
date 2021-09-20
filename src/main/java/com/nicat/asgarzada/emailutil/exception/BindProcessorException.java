package com.nicat.asgarzada.emailutil.exception;

public class BindProcessorException extends RuntimeException{
    public BindProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindProcessorException(String message) {
        super(message);
    }
}
