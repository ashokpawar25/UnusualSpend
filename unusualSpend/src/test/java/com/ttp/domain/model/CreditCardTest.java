package com.ttp.domain.model;

import com.ttp.unusualspend.domain.model.CreditCard;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidCardIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreditCardTest {
    @Test
    void shouldBeAbleToCreateCreditCard() throws InvalidCardIdException {
        // arrange
        int cardId = 1;
        CreditCard actual = new CreditCard(cardId);

        // act
        CreditCard expected = CreditCard.create(cardId);

        // assert
        assertEquals(actual, expected);
    }

    @Test
    void shouldBeAbleToThrowExceptionForInvalidCardId() {
        assertThrows(InvalidCardIdException.class, () -> CreditCard.create(0));
        assertThrows(InvalidCardIdException.class, () -> CreditCard.create(-1));
    }
}
