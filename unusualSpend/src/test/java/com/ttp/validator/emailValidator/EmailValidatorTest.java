package com.ttp.validator.emailValidator;

import com.ttp.domain.validator.emailValidator.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailValidatorTest {
    @Test
    void shouldValidateAnEmptyEmail() {
        boolean isEmpty = EmailValidator.isEmptyEmailId("");
        boolean notEmpty = EmailValidator.isEmptyEmailId("EmailId");
        Assertions.assertTrue(isEmpty);
        Assertions.assertFalse(notEmpty);
    }

    @Test
    void shouldValidateNullEmailId() {
        boolean isNull = EmailValidator.isNullEmailId(null);
        boolean notNull = EmailValidator.isNullEmailId("");
        Assertions.assertTrue(isNull);
        Assertions.assertFalse(notNull);
    }

    @Test
    void shouldValidateCorrectEmailId() {
        boolean isCorrect = EmailValidator.isValidEmail("ashokpawar8020@gmail.com");
        boolean dotMissing = EmailValidator.isValidEmail("ashokpawar@8020gmailcom");
        boolean symbolMissing = EmailValidator.isValidEmail("ashokpawar8020gmail.com");
        Assertions.assertTrue(isCorrect);
        Assertions.assertFalse(dotMissing);
        Assertions.assertFalse(symbolMissing);
    }
}
