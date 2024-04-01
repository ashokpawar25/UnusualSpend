package com.ttp.creditCardManager;

import com.ttp.unusualspend.domain.model.Category;
import com.ttp.unusualspend.domain.model.CreditCard;
import com.ttp.unusualspend.domain.model.Transaction;
import com.ttp.unusualspend.domain.model.User;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidAmountException;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidCardIdException;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidCategoryException;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidTransactionIdException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideEmailException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserIdException;
import com.ttp.unusualspend.domain.model.exceptions.user.InvalideUserNameException;
import com.ttp.unusualspend.dto.UserRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardManagerTest {
    @Test
    void shouldAbleToMapUserToCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        // arrange
        CreditCardManager creditCardManager = new CreditCardManager();
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";

        // act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        boolean isMapped = creditCardManager.mapCardToUser(creditCard, user);

        // assert
        assertTrue(isMapped);
    }

    @Test
    void shouldBeAbleToAddTransactionForCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        // arrange
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        CreditCardManager creditCardManager = new CreditCardManager();
        Transaction transaction = Transaction.create(101, Category.TRAVEL, 100, LocalDate.of(2024, 3, 17), 1);
        List<Transaction> expectedTransactionList = List.of(transaction);

        // act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(expectedTransactionList);

        List<Transaction> transactions = creditCardManager.getTransactions();

        // assert
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void shouldBeAbleToAddMultipleTransactionsForCreditCard() throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        // arrange
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        CreditCardManager creditCardManager = new CreditCardManager();
        Transaction transaction1 = Transaction.create(101, Category.TRAVEL, 100, LocalDate.of(2024, 03, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.TRAVEL, 100, LocalDate.of(2024, 03, 17), 1);
        List<Transaction> expectedTransactionList = List.of(transaction1, transaction2);

        // act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(expectedTransactionList);
        List<Transaction> transactions = creditCardManager.getTransactions();

        // assert
        assertEquals(2, transactions.size());
    }

    @Test
    void shouldBeAbleToFilterTransactionsByMonth() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        // arrange
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        CreditCardManager creditCardManager = new CreditCardManager();
        Month currentMonth = LocalDate.now().getMonth();
        Month previousMonth = LocalDate.now().minusMonths(1).getMonth();
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear;
        if (currentMonth == Month.JANUARY) {
            previousMonth = Month.DECEMBER;
            previousYear = currentYear - 1;
        }

        Transaction transaction1 = Transaction.create(101, Category.TRAVEL, 100, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.TRAVEL, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2);

        // act
        CreditCard creditCard = CreditCard.create(1);
        User user = User.create(userId, userName, userEmail);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(transactions);
        List<Transaction> currentMonthTransactions = creditCardManager.filterTransactionsByMonth(LocalDate.now().getMonth());

        // assert
        assertEquals(transaction1, currentMonthTransactions.get(0));
    }

    @Test
    void shouldBeAbleToFindRecordOfUserSpend() throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        // arrange
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        CreditCardManager creditCardManager = new CreditCardManager();
        Month currentMonth = LocalDate.now().getMonth();
        Month previousMonth = LocalDate.now().minusMonths(1).getMonth();
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear;
        if (currentMonth == Month.JANUARY) {
            previousMonth = Month.DECEMBER;
            previousYear = currentYear - 1;
        }
        Transaction transaction1 = Transaction.create(101, Category.TRAVEL, 150, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.TRAVEL, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.GROCERIES, 110, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.GROCERIES, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        // act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(transactions);
        double thresholdPercentage = 20.0;
        Map<Integer, UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);

        // assert
        assertEquals(1, userRecord.size());
        assertEquals(1, userRecord.get(1).getUnUsualSpendList().size());
        assertEquals(1, userRecord.get(1).getUsualSpendList().size());

    }

    @Test
    void shouldAbleToSendEmailToUser() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        // arrange
        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        CreditCardManager creditCardManager = new CreditCardManager();
        Month currentMonth = LocalDate.now().getMonth();
        Month previousMonth = LocalDate.now().minusMonths(1).getMonth();
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear;
        if (currentMonth == Month.JANUARY) {
            previousMonth = Month.DECEMBER;
            previousYear = currentYear - 1;
        }
        Transaction transaction1 = Transaction.create(101, Category.TRAVEL, 150, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.TRAVEL, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.GROCERIES, 120, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.GROCERIES, 140, LocalDate.of(previousYear, previousMonth, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        // act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);


        creditCardManager.addTransaction(transactions);
        double thresholdPercentage = 30;
        Map<Integer, UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);
        boolean isSend = creditCardManager.sendEmail(userRecord);

        // assert
        assertTrue(isSend);
    }
}
