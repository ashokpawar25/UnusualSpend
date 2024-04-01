package com.ttp.unusualspend.domain.model.exceptions.transaction;

public class InvalidCategoryException extends Exception {
    public InvalidCategoryException(String message)
    {
        super(message);
    }
}
