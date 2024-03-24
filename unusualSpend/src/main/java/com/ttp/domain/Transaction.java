package com.ttp.domain;

import com.ttp.domain.exceptions.transaction.InvalidAmountException;
import com.ttp.domain.exceptions.transaction.InvalideCategoryException;
import com.ttp.domain.exceptions.transaction.InvalideTransactionIdException;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

public class Transaction {

    int transactionID;
    Category category;
    int amount;
    LocalDate transactionDate;
    int creditCardId;

    public Transaction()
    {

    }

    public Transaction(int transactionID, Category category, int amount, LocalDate transactionDate, int creditCardId) {
        this.transactionID = transactionID;
        this.category = category;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.creditCardId = creditCardId;
    }

    public static Transaction create(int transactionID, Category category, int transactionAmount, LocalDate transactionDate, int creditCardId) throws InvalideTransactionIdException, InvalideCategoryException, InvalidAmountException {
        if(transactionID == 0 || transactionID < 0) throw new InvalideTransactionIdException("Invalid Transaction Id");
        if(!EnumSet.allOf(Category.class).contains(category)) throw new InvalideCategoryException("Invalid Category found");
        if(transactionAmount == 0 || transactionAmount < 0) throw new InvalidAmountException("Amount should be greater than 0");
        return new Transaction(transactionID,category,transactionAmount,transactionDate,creditCardId);
    }

    public Category getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID == that.transactionID && amount == that.amount && creditCardId == that.creditCardId && category == that.category && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, category, amount, transactionDate, creditCardId);
    }
}
