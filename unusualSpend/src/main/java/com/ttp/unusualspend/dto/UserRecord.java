package com.ttp.unusualspend.dto;

import java.util.List;

public class UserRecord
{
    private List<AmountAndCategory> usualSpendList;
    private List<AmountAndCategory> unUsualSpendList;

    public UserRecord( List<AmountAndCategory> usualSpendList, List<AmountAndCategory> unUsualSpendList) {
        this.usualSpendList = usualSpendList;
        this.unUsualSpendList = unUsualSpendList;
    }

    public List<AmountAndCategory> getUsualSpendList() {
        return usualSpendList;
    }

    public List<AmountAndCategory> getUnUsualSpendList() {
        return unUsualSpendList;
    }

}
