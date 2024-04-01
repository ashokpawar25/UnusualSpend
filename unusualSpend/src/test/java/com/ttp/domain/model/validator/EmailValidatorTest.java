package com.ttp.domain.model.validator;

import com.ttp.domain.exceptions.user.InvalideEmailException;
import com.ttp.domain.model.User;
import com.ttp.domain.validator.emailValidator.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidatorTest {
    @Test
    void shouldBeAbleToValidateCorrectEmailId() {
        // arrange & act
        boolean isCorrect = EmailValidator.isValidEmail("ashokpawar8020@gmail.com");

        // assert
        Assertions.assertTrue(isCorrect);
    }

    @Test
    void shouldBeAbleToValidateAnEmptyEmail() {
        // arrange & act
        boolean isEmpty = EmailValidator.isEmptyEmailId("");
        boolean notEmpty = EmailValidator.isEmptyEmailId("EmailId");

        // assert
        Assertions.assertTrue(isEmpty);
        Assertions.assertFalse(notEmpty);
    }

    @Test
    void shouldBeAbleToValidateNullEmailId() {
        // arrange & act
        boolean isNull = EmailValidator.isNullEmailId(null);
        boolean notNull = EmailValidator.isNullEmailId("");

        // assert
        Assertions.assertTrue(isNull);
        Assertions.assertFalse(notNull);
    }

    @Test
    void shouldBeAbleToValidateIncorrectEmailId()
    {
        assertThrows(InvalideEmailException.class,()-> User.create(3,"Ashok Pawar","ashok"));
        assertThrows(InvalideEmailException.class,()->User.create(4,"Ashok Pawar","ashok@"));
        assertThrows(InvalideEmailException.class,()->User.create(5,"Ashok Pawar","@gmail"));
        assertThrows(InvalideEmailException.class,()->User.create(6,"Ashok Pawar","."));
        assertThrows(InvalideEmailException.class,()->User.create(7,"Ashok Pawar","@"));
        assertThrows(InvalideEmailException.class,()->User.create(8,"Ashok Pawar","@."));
        assertThrows(InvalideEmailException.class,()->User.create(9,"Ashok Pawar","ashok@gmail"));
        assertThrows(InvalideEmailException.class,()->User.create(10,"Ashok Pawar",".com"));
        assertThrows(InvalideEmailException.class,()->User.create(11,"Ashok Pawar","ashok@gmail."));
        assertThrows(InvalideEmailException.class,()->User.create(12,"Ashok Pawar","ashok.com"));
        assertThrows(InvalideEmailException.class,()->User.create(13,"Ashok Pawar","@ashok.com"));
    }
}