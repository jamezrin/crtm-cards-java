package com.github.jamezrin.crtmcards.exceptions;

public class InactiveCardNumberException extends NotExistentCardNumberException {
    public InactiveCardNumberException(String message) {
        super(message);
    }
}
