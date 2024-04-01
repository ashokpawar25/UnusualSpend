package com.ttp.unusualspend.domain.model.exceptions.transaction;

public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message)
    {
        super(message);
    }
}
