package com.ttp.unusualspend.domain.model;

import com.ttp.unusualspend.domain.model.exceptions.transaction.InvalidCardIdException;

import java.util.Objects;

public class CreditCard {
    private final int cardId;
    private User user;
    public CreditCard(int cardId)
    {
        this.cardId=cardId;
    }

    public static CreditCard create(int cardId) throws InvalidCardIdException {
        if(cardId < 0 || cardId == 0)
            throw new InvalidCardIdException("Invalid Card Id");

        return new CreditCard(cardId);
    }

    public int getCardId() {
        return cardId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return cardId == that.cardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId);
    }
}
