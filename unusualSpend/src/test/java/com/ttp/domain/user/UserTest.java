package com.ttp.domain.user;

import com.ttp.domain.exceptions.user.InvalideEmailException;
import com.ttp.domain.User;
import com.ttp.domain.exceptions.user.InvalideUserNameException;
import com.ttp.domain.exceptions.user.InvalideUserIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    User user = new User();

    @Test
    void shouldThrowExceptionWhenUserIdIsInvalid() throws InvalideUserIdException {
        Assertions.assertThrows(Exception.class, () -> User.create(-1, "Ashok", "ashokpawar8020@gmail.com"));
        Assertions.assertThrows(Exception.class, () -> User.create(0, "Ashok", "ashokpawar8020@gmail.com"));
    }

    @Test
    void shouldthrowExeceptionWhenUserEmailIsInvalid() throws InvalideUserIdException {
        Assertions.assertThrows(InvalideEmailException.class, () -> User.create(3, "Ashok Pawar", null));
        Assertions.assertThrows(InvalideEmailException.class, () -> User.create(4, "Ashok Pawar", ""));
        Assertions.assertDoesNotThrow(() -> User.create(2, "Ashok Pawar", "ashokpawar8020@gmail.com"));
    }

    @Test
    void shouldThrowExceptioinWhenUserNameIsInvalid() throws InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        Assertions.assertThrows(InvalideUserNameException.class, () -> User.create(4, "av al", "sample@gmail.com"));
        Assertions.assertDoesNotThrow(() -> User.create(3, "sample Name", "sample@gmail.com"));
    }
}
