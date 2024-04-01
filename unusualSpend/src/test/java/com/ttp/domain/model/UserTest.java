package com.ttp.domain.model;

import com.ttp.unusualspend.domain.model.User;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideEmailException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserNameException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldBeAbleToCreateInstanceOfUser() throws InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        User user = User.create(1,"Ashok Pawar","ashokpawar8020@gmail.com");
        assertNotNull(user);
    }

    @Test
    void shouldBeAbleToThrowExceptionForInvalidUserId(){
        assertThrows(InvalideUserIdException.class, () -> User.create(-1, "Ashok Pawar", "ashokpawar8020@gmail.com"));
        assertThrows(InvalideUserIdException.class, () -> User.create(0, "Ashok Pawar", "ashokpawar8020@gmail.com"));
    }

    @Test
    void shouldBeAbleToThrowExceptionForInvalidEmailId(){
        assertThrows(InvalideEmailException.class, () -> User.create(1, "Ashok Pawar", null));
        assertThrows(InvalideEmailException.class, () -> User.create(2, "Ashok Pawar", ""));
        assertThrows(InvalideEmailException.class, () -> User.create(2, "Ashok Pawar", "ashokpawar8020@gmailcom"));
    }

    @Test
    void shouldThrowExceptionForInvalidUserName(){
        assertThrows(InvalideUserNameException.class, () -> User.create(4, "av al", "sample@gmail.com"));
    }
}
