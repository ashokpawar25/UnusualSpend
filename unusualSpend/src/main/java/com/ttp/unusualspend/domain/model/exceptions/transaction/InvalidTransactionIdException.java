package com.ttp.unusualspend.domain.model.exceptions.transaction;

public class InvalidTransactionIdException extends Exception
{
    public InvalidTransactionIdException(String message)
    {
        super(message);
    }
}
