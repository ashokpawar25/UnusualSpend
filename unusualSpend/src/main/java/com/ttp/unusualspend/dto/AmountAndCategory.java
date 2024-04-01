package com.ttp.unusualspend.dto;

import com.ttp.unusualspend.domain.model.Category;

public class AmountAndCategory
{
    private Category category;
    private int amount;

    public AmountAndCategory(Category category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

}
