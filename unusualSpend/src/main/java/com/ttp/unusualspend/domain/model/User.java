package com.ttp.unusualspend.domain.model;

import com.ttp.unusualspend.domain.model.exceptions.user.InvalideEmailException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserIdException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserNameException;
import com.ttp.unusualspend.domain.model.validator.EmailValidator;
import com.ttp.unusualspend.domain.model.validator.UserNameValidator;

public class User {
    private final int userId;
    private final String name;
    private final String email;

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public static User create(int userId, String name, String email) throws InvalideUserIdException, InvalideEmailException, InvalideUserNameException {
        if (!isValidUserId(userId)) throw new InvalideUserIdException("User Id should be positive");
        if(!EmailValidator.validateEmail(email)) throw new InvalideEmailException(email);
        if(!UserNameValidator.validateUserName(name)) throw new InvalideUserNameException(name);
        return new User(userId, name, email);
    }

    private static boolean isValidUserId(int userId)
    {
        return userId>0;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
