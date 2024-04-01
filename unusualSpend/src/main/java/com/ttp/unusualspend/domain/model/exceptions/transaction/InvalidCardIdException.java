package com.ttp.unusualspend.domain.model.exceptions.transaction;

public class InvalidCardIdException extends Exception {
    public InvalidCardIdException(String message)
    {
        super(message);
    }
}
