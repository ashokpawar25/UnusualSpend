package com.ttp.unusualspend.domain.model;

import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidAmountException;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidCategoryException;
import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidTransactionIdException;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

public class Transaction {

    private final int transactionId;
    private final Category category;
    private final int amount;
    private final LocalDate transactionDate;
    private final int creditCardId;
    
    public Transaction(int transactionId, Category category, int amount, LocalDate transactionDate, int creditCardId) {
        this.transactionId = transactionId;
        this.category = category;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.creditCardId = creditCardId;
    }

    public static Transaction create(int transactionID, Category category, int transactionAmount, LocalDate transactionDate, int creditCardId) throws InvalidTransactionIdException, InvalidCategoryException, InvalidAmountException {
        if(transactionID == 0 || transactionID < 0) throw new InvalidTransactionIdException("Invalid Transaction Id");
        if(!EnumSet.allOf(Category.class).contains(category)) throw new InvalidCategoryException("Invalid Category found");
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
        return transactionId == that.transactionId && amount == that.amount && creditCardId == that.creditCardId && category == that.category && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, category, amount, transactionDate, creditCardId);
    }
}