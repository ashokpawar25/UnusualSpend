package com.ttp.domain.exceptions.transaction;

public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message)
    {
        super(message);
    }
}
