package com.ttp.domain.validator.userNameValidator;

public class UserNameValidator {
    public static boolean validateUserName(String name)
    {
        if(isValidUserName(name)) return true;
        return false;
    }

    public static boolean isValidUserName(String name)
    {
        return name.matches("[a-zA-z]{3,} [a-zA-z]{3,}");
    }
}
