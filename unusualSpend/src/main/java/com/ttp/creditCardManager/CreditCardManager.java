package com.ttp.creditCardManager;

import com.ttp.domain.Category;
import com.ttp.domain.CreditCard;
import com.ttp.domain.Transaction;
import com.ttp.domain.User;
import com.ttp.dto.AmountAndCategory;
import com.ttp.handler.EmailHandler;
import com.ttp.dto.UserRecord;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreditCardManager {

    List<CreditCard> creditCards = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();
    public boolean mapCardToUser(CreditCard creditCard, User user)
    {
        creditCard.setUser(user);
        return true;
    }

    public void addTransaction(List<Transaction> transactions)
    {
        for(Transaction transaction:transactions)
        {
            this.transactions.add(transaction);
        }

    }

    public List<Transaction> getTransactions()
    {
        return this.transactions;
    }

    public List<Transaction> filterTransactionsByMonth(Month month)
    {
        return transactions.stream().filter(x-> x.getTransactionDate().getMonth().equals(month)).collect(Collectors.toList());
    }

    public boolean sendEmail(Map<Integer, UserRecord> userRecords)
    {
        EmailHandler emailHandler = new EmailHandler();
        for(Map.Entry<Integer, UserRecord> entry : userRecords.entrySet())
        {
            CreditCard creditCard = null;
            int creditCardId = entry.getKey();
            for(CreditCard creditCard1:creditCards)
            {
                if(creditCard1.getCardId() == creditCardId)
                {
                    creditCard = creditCard1;
                    break;
                }
            }
            String email = creditCard.getUser().getEmail();
            String name = creditCard.getUser().getName();
            int totalSpent = 0;
            int unusualSpent = 0;
            int usualSpent = 0;
            UserRecord record = entry.getValue();
            List<AmountAndCategory> usualSpendList = record.getUsualSpendList();
            List<AmountAndCategory> unUsualSpendList = record.getUnUsualSpendList();
            String subject = "Regarding unusual spend for current month";
            StringBuilder body = new StringBuilder(" \n hello "+ name +"!\n We have detected unusually high spending on your card in these categories:\n ");
            for (AmountAndCategory spend:unUsualSpendList)
            {
                body.append(" * You spent "+ spend.getAmount() +" on "+ spend.getCategory()+"\n");
                totalSpent += spend.getAmount();
                unusualSpent += spend.getAmount();
            }
            body.append("\n And Usual spending in these categories\n");
            for(AmountAndCategory spend:usualSpendList)
            {
                body.append(" * You spent "+ spend.getAmount() +" on "+ spend.getCategory()+"\n");
                totalSpent += spend.getAmount();
                usualSpent += spend.getAmount();
            }
            body.append("Thanks,\n" +
                    "\n" +
                    "The Credit Card Company\n");
            body.insert(0,"Total spending of "+ totalSpent +" detected!");
            emailHandler.sendEmail(subject,body.toString(),email);
        }
        return true;
    }

    public Map<Integer, UserRecord> getUserRecord(int thresholdPercentage)
    {
        double criteria = 1 + (thresholdPercentage/100);
        Map<Integer ,UserRecord> userRecords = new HashMap<>();

        List<Transaction> currentMonthTransactions = this.filterTransactionsByMonth(LocalDate.now().getMonth());
        List<Transaction> previousMonthTransactions = this.filterTransactionsByMonth(LocalDate.now().minusMonths(1).getMonth());
        for(Transaction currentTransaction:currentMonthTransactions)
        {
            for(Transaction previousTransaction:previousMonthTransactions)
            {
                if(currentTransaction.getCategory().equals(previousTransaction.getCategory()) && currentTransaction.getCreditCardId() == previousTransaction.getCreditCardId())
                {
                    if(userRecords.containsKey(currentTransaction.getCreditCardId()))
                    {
                        UserRecord userRecordList = userRecords.get(currentTransaction.getCreditCardId());
                        List<AmountAndCategory> usualSpendRecord = userRecordList.getUsualSpendList();
                        List<AmountAndCategory> unusualSpendRecord = userRecordList.getUnUsualSpendList();
                        int transactionID = currentTransaction.getCreditCardId();
                        if (currentTransaction.getAmount() >= (previousTransaction.getAmount()*criteria))
                        {
                            Category category = currentTransaction.getCategory();
                            int amount = currentTransaction.getAmount()-previousTransaction.getAmount();
                            unusualSpendRecord.add(new AmountAndCategory(category,amount));
                        }
                        else
                        {
                            Category category = currentTransaction.getCategory();
                            int amount = currentTransaction.getAmount();
                            usualSpendRecord.add(new AmountAndCategory(category,amount));
                        }
                        userRecords.put(transactionID,userRecordList);
                    }
                    else if(!userRecords.containsKey(currentTransaction.getCreditCardId()))
                    {
                        List<AmountAndCategory> usualSpendRecord = new ArrayList<>();
                        List<AmountAndCategory> unusualSpendRecord = new ArrayList<>();
                        UserRecord userRecordList = new UserRecord(usualSpendRecord,unusualSpendRecord);
                        int creditCardId = currentTransaction.getCreditCardId();

                        if (currentTransaction.getAmount() >= (previousTransaction.getAmount()*criteria))
                        {
                            Category category = currentTransaction.getCategory();
                            int amount = currentTransaction.getAmount()-previousTransaction.getAmount();
                            unusualSpendRecord.add(new AmountAndCategory(category,amount));
                        }
                        else
                        {
                            Category category = currentTransaction.getCategory();
                            int amount = currentTransaction.getAmount();
                            usualSpendRecord.add(new AmountAndCategory(category,amount));
                        }
                        userRecords.put(creditCardId,userRecordList);
                    }
                }
            }
        }
        return userRecords;
    }


}
