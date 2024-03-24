package com.ttp.domain.transaction;

import com.ttp.domain.Category;
import com.ttp.domain.Transaction;
import com.ttp.domain.exceptions.transaction.InvalidAmountException;
import com.ttp.domain.exceptions.transaction.InvalideCategoryException;
import com.ttp.domain.exceptions.transaction.InvalideTransactionIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TransactionTest {
    @Test
    void shouldAbleToCreateTransaction() throws InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException {
        //Arrange
        int transactionID = 1;
        Category category = Category.groceries;
        int transactionAmount = 100;
        LocalDate transactionDate = LocalDate.of(2024, 03, 17);
        int creditCardId = 1;

        //Act
        Transaction expectedTransaction = new Transaction(transactionID, category, transactionAmount, transactionDate, creditCardId);
        Transaction actualTransaction = Transaction.create(transactionID, category, transactionAmount, transactionDate, creditCardId);

        //Assert
        Assertions.assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void shouldThrowExceptionWhenInvalidTransactionID() {
        int transactionID = -1;
        Category category = Category.groceries;
        int transactionAmount = 100;
        LocalDate transactionDate = LocalDate.of(2024, 03, 17);
        int creditCardId = 1;

        //Assert
        Assertions.assertThrows(InvalideTransactionIdException.class, () -> Transaction.create(transactionID, category, transactionAmount, transactionDate, creditCardId));
    }

    @Test
    void shouldThrowExceptionWhenInvalidCategory() {
        int transactionID = 1;
        Category category = null;
        int transactionAmount = 100;
        LocalDate transactionDate = LocalDate.of(2024, 03, 17);
        int creditCardId = 1;

        //Assert
        Assertions.assertThrows(InvalideCategoryException.class, () -> Transaction.create(transactionID, category, transactionAmount, transactionDate, creditCardId));

    }

    @Test
    void shouldThrowExceptionWhenInvalidAmount() {
        int transactionID = 1;
        Category category = Category.groceries;
        int transactionAmount = 0;
        LocalDate transactionDate = LocalDate.of(2024, 03, 17);
        int creditCardId = 1;

        //Assert
        Assertions.assertThrows(InvalidAmountException.class, () -> Transaction.create(transactionID, category, transactionAmount, transactionDate, creditCardId));

    }


}
