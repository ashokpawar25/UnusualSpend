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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class CreditCardManagerTest {
    @Test
    void shouldAbleToMapUserToCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userId, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        boolean isMapped = creditCardManager.mapCardToUser(creditCard, user);

        //Assert
        Assertions.assertTrue(isMapped);
    }

    @Test
    void shouldAbleToAddTransactionForCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userId, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 03, 17), 1);
        List<Transaction> expectedTransactionList = List.of(transaction);
        creditCardManager.addTransaction(expectedTransactionList);

        List<Transaction> transactions = creditCardManager.getTransactions();

        //Assert
        Assertions.assertEquals(transaction, transactions.get(0));
    }

    @Test
    void shouldAbleToAddMultipleTransactionsForCreditCard() throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userId, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 03, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 100, LocalDate.of(2024, 03, 17), 1);
        List<Transaction> expectedTransactionList = List.of(transaction1, transaction2);

        creditCardManager.addTransaction(expectedTransactionList);

        List<Transaction> transactions = creditCardManager.getTransactions();

        //Assert
        Assertions.assertEquals(2, transactions.size());
    }

    @Test
    void shouldAbleToFilterTransactionsByMonth() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        //Arrange
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

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2);

        //Act
        CreditCard creditCard = CreditCard.create(1);
        User user = User.create(userId, userName, userEmail);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(transactions);
        List<Transaction> currentMonthTransactions = creditCardManager.filterTransactionsByMonth(LocalDate.now().getMonth());

        //Assert
        Assertions.assertEquals(transaction1, currentMonthTransactions.get(0));
    }

    @Test
    void shouldAbleToFindRecordOfUserSpend() throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        //Arrange

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
        Transaction transaction1 = Transaction.create(101, Category.travel, 150, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.groceries, 110, LocalDate.of(currentYear, currentMonth, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.groceries, 100, LocalDate.of(previousYear, previousMonth, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        //Act
        User user = User.create(userId, userName, userEmail);
        CreditCard creditCard = CreditCard.create(1);
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);
        creditCardManager.addTransaction(transactions);
        double thresholdPercentage = 20.0;
        Map<Integer, UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);
        System.out.println(userRecord.size());

        //assert
        Assertions.assertEquals(1, userRecord.size());
        Assertions.assertEquals(1, userRecord.get(1).getUnUsualSpendList().size());
        Assertions.assertEquals(1, userRecord.get(1).getUsualSpendList().size());

    }

    @Test
    void shouldAbleToSendEmailForUnusualSpendUsers() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userId = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userId, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 150, LocalDate.of(2024, 03, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.groceries, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.groceries, 120, LocalDate.of(2024, 03, 17), 1);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        creditCardManager.addTransaction(transactions);
        int thresholdPercentage = 30;
        Map<Integer, UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);
        boolean isSend = creditCardManager.sendEmail(userRecord);

        //Assert
        Assertions.assertTrue(isSend);
    }
}
