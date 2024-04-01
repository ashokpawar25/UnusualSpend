package com.ttp.userHandler;

import com.ttp.unusualspend.domain.model.User;
import com.ttp.handler.UserHandler;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideEmailException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserNameException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserHandlerTest
{
    @Test
    void shouldAbleToAddNewUser() throws InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        UserHandler userHandler = new UserHandler();
        User user = User.create(1, "Sample User", "sampleemail@gmail.com");
        userHandler.addUser(user);
        Assertions.assertEquals(1,userHandler.users.size());
    }
}
