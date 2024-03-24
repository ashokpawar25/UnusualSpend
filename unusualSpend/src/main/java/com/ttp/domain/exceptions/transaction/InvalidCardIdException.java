package com.ttp.domain.exceptions.transaction;

public class InvalidCardIdException extends Exception {
    public InvalidCardIdException(String message)
    {
        super(message);
    }
}
