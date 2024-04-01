package com.ttp.domain.model;

import com.ttp.domain.exceptions.user.InvalideEmailException;
import com.ttp.domain.exceptions.user.InvalideUserNameException;
import com.ttp.domain.exceptions.user.InvalideUserIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldBeAbleToCreateInstanceOfUser() throws InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        User user = User.create(1,"Ashok Pawar","ashokpawar8020@gmail.com");
        assertNotNull(user);
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenUserIdIsInvalid(){
        assertThrows(InvalideUserIdException.class, () -> User.create(-1, "Ashok Pawar", "ashokpawar8020@gmail.com"));
        assertThrows(InvalideUserIdException.class, () -> User.create(0, "Ashok Pawar", "ashokpawar8020@gmail.com"));
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenEmailIdIsInvalid(){
        assertThrows(InvalideEmailException.class, () -> User.create(1, "Ashok Pawar", null));
        assertThrows(InvalideEmailException.class, () -> User.create(2, "Ashok Pawar", ""));
        assertThrows(InvalideEmailException.class, () -> User.create(2, "Ashok Pawar", "ashokpawar8020@gmailcom"));
    }

    @Test
    void shouldThrowExceptionWhenUserNameIsInvalid(){
        assertThrows(InvalideUserNameException.class, () -> User.create(4, "av al", "sample@gmail.com"));
    }
}
