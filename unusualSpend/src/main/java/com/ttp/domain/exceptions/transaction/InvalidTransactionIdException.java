package com.ttp.domain.exceptions.transaction;

public class InvalidTransactionIdException extends Exception
{
    public InvalidTransactionIdException(String message)
    {
        super(message);
    }
}
