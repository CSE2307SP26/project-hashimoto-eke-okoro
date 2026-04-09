package main;

import com.bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount {

    private final String accountId;
    private final String accountHolderName;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(String accountHolderName) {
        this.accountId = UUID.randomUUID().toString().substring(0, 8);
        this.accountHolderName = accountHolderName;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance, "Deposit"));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void withdraw(double amount) {
        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance, "Withdrawal"));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    @Override
    public String toString() {
        return accountId + " - " + accountHolderName + " ($" + String.format("%.2f", balance) + ")";
    }
}