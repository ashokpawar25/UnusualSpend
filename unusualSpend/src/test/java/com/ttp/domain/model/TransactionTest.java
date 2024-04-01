package com.ttp.domain.model;

import com.ttp.domain.exceptions.transaction.InvalidAmountException;
import com.ttp.domain.exceptions.transaction.InvalidCategoryException;
import com.ttp.domain.exceptions.transaction.InvalidTransactionIdException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    void shouldBeAbleToCreateTransaction() throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        // arrange
        int transactionId = 1;
        Category category = Category.groceries;
        int transactionAmount = 100;
        LocalDate transactionDate = LocalDate.of(2024, 3, 17);
        int creditCardId = 1;
        Transaction expectedTransaction = new Transaction(transactionId, category, transactionAmount, transactionDate, creditCardId);

        // act
        Transaction actualTransaction = Transaction.create(transactionId, category, transactionAmount, transactionDate, creditCardId);

        // assert
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenInvalidTransactionIdIsPassed() {
        assertThrows(InvalidTransactionIdException.class, () -> Transaction.create(-1, Category.travel, 100, LocalDate.of(2024, 3, 17), 1));
        assertThrows(InvalidTransactionIdException.class, () -> Transaction.create(0, Category.travel, 100, LocalDate.of(2024, 3, 17), 1));
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenInvalidCategoryIsPassed() {
        assertThrows(InvalidCategoryException.class, () -> Transaction.create(1, null, 100, LocalDate.of(2024, 3, 17), 1));
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenInvalidAmountIsPassed() {
        assertThrows(InvalidCategoryException.class, () -> Transaction.create(1, null, 0, LocalDate.of(2024, 3, 17), 1));
        assertThrows(InvalidCategoryException.class, () -> Transaction.create(1, null, -1, LocalDate.of(2024, 3, 17), 1));
    }


}
