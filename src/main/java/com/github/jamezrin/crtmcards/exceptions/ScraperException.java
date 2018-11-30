package com.github.jamezrin.crtmcards.exceptions;

public class ScraperException extends Exception {
    public ScraperException() {
    }

    public ScraperException(String message) {
        super(message);
    }

    public ScraperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScraperException(Throwable cause) {
        super(cause);
    }

    public ScraperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
