package com.ttp.domain.creditCard;

import com.ttp.domain.exceptions.transaction.InvalidCardIdException;
import com.ttp.domain.CreditCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class creditCardTest {
    @Test
    void shouldAbleToCreateCreditCard() throws InvalidCardIdException {
        //Arrange
        int creditCardId = 1;
        CreditCard actual = new CreditCard(creditCardId);

        //Act
        CreditCard expected = CreditCard.create(creditCardId);

        //Assert
        Assertions.assertEquals(actual, expected);
    }
}
