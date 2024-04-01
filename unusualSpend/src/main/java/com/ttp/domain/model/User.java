package com.ttp.domain;

import com.ttp.domain.validator.emailValidator.EmailValidator;
import com.ttp.domain.exceptions.user.InvalideEmailException;
import com.ttp.domain.exceptions.user.InvalideUserNameException;
import com.ttp.domain.exceptions.user.InvalideUserIdException;
import com.ttp.domain.validator.userNameValidator.UserNameValidator;

public class User {
    int userId;
    String name;
    String email;

    public User() {

    }

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public static User create(int userId, String name, String email) throws InvalideUserIdException, InvalideEmailException, InvalideUserNameException {
        if (!isValidUserId(userId)) throw new InvalideUserIdException("User Id sholud be positve");
        if(!EmailValidator.validateEmail(email)) throw new InvalideEmailException(email);
        if(!UserNameValidator.validateUserName(name)) throw new InvalideUserNameException(name);
        return new User(userId, name, email);
    }

    private static boolean isValidUserId(int userId)
    {
        return userId>0;
    }



    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
