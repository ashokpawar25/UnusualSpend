package com.ttp.creditCardManager;

import com.ttp.domain.Category;
import com.ttp.domain.Transaction;
import com.ttp.domain.exceptions.transaction.InvalidCardIdException;
import com.ttp.domain.CreditCard;
import com.ttp.domain.User;
import com.ttp.domain.exceptions.transaction.InvalidAmountException;
import com.ttp.domain.exceptions.user.InvalideEmailException;
import com.ttp.domain.exceptions.user.InvalideUserNameException;
import com.ttp.domain.exceptions.transaction.InvalideCategoryException;
import com.ttp.domain.exceptions.transaction.InvalideTransactionIdException;
import com.ttp.domain.exceptions.user.InvalideUserIdException;
import com.ttp.dto.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CreditCardManagerTest {
    @Test
    void shouldAbleToMapUserToCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        boolean isMapped = creditCardManager.mapCardToUser(creditCard, user);

        //Assert
        Assertions.assertTrue(isMapped);
    }

    @Test
    void shouldAbleToAddTransactionForCreditCard() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

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
    void shouldAbleToAddMultipleTransactionsForCreditCard() throws InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

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
    void shouldAbleToFilterTransactionsByMonth() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 100, LocalDate.of(2024, 03, 17), 1);
        List<Transaction> transactions = List.of(transaction1, transaction2);

        creditCardManager.addTransaction(transactions);

        List<Transaction> currentMonthTransactions = creditCardManager.filterTransactionsByMonth(LocalDate.now().getMonth());

        //Assert
        Assertions.assertEquals(transaction2, currentMonthTransactions.get(0));
    }

    @Test
    void shouldAbleToFindRecordOfUserSpend() throws InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalidCardIdException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 150, LocalDate.of(2024, 03, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.groceries, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.groceries, 120, LocalDate.of(2024, 03, 17), 1);

        List<Transaction> transactions = List.of(transaction1, transaction2,transaction3,transaction4);

        creditCardManager.addTransaction(transactions);
        int thresholdPercentage = 30;
        Map<Integer , UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);

        //assert
        Assertions.assertEquals(1,userRecord.size());
        Assertions.assertEquals(1,userRecord.get(1).getUnUsualSpendList().size());
        Assertions.assertEquals(1,userRecord.get(1).getUsualSpendList().size());

    }

    @Test
    void shouldAbleToSendEmailForUnusualSpendUsers() throws InvalidCardIdException, InvalideEmailException, InvalideUserNameException, InvalideUserIdException, InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException {
        //Arrange
        CreditCard creditCard = CreditCard.create(1);

        int userID = 1;
        String userName = "Ashok Pawar";
        String userEmail = "ashokpawar25052001@gmail.com";
        User user = User.create(userID, userName, userEmail);

        //Act
        CreditCardManager creditCardManager = new CreditCardManager();
        creditCardManager.creditCards.add(creditCard);
        creditCardManager.mapCardToUser(creditCard, user);

        Transaction transaction1 = Transaction.create(101, Category.travel, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction2 = Transaction.create(102, Category.travel, 150, LocalDate.of(2024, 03, 17), 1);
        Transaction transaction3 = Transaction.create(103, Category.groceries, 100, LocalDate.of(2024, 02, 17), 1);
        Transaction transaction4 = Transaction.create(104, Category.groceries, 120, LocalDate.of(2024, 03, 17), 1);

        List<Transaction> transactions = List.of(transaction1, transaction2,transaction3,transaction4);

        creditCardManager.addTransaction(transactions);
        int thresholdPercentage = 30;
        Map<Integer , UserRecord> userRecord = creditCardManager.getUserRecord(thresholdPercentage);
        boolean isSend = creditCardManager.sendEmail(userRecord);

        //Assert
        Assertions.assertTrue(isSend);
    }
}
