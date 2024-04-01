package com.ttp.domain.model.validator;

import com.ttp.unusualspend.domain.model.validator.UserNameValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserNameValidatorTest {
    @Test
    void shouldValidateUserName() {
        Assertions.assertTrue(UserNameValidator.isValidUserName("Ashok Pawar"));
        Assertions.assertFalse(UserNameValidator.validateUserName("A b"));
        Assertions.assertFalse(UserNameValidator.validateUserName("A ab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("A abc"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Ab ab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Ab abc"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Ab a"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abc a"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abc ab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abc abc  "));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abc  ab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abc @ab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abcab"));
        Assertions.assertFalse(UserNameValidator.validateUserName("Abca@b"));
        Assertions.assertTrue(UserNameValidator.validateUserName("Abc abc"));

    }
}
