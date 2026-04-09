package main;

import com.bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount {

    private final String accountId;
    private final String accountHolderName;
    private boolean active;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(String accountHolderName) {
        this.accountId = UUID.randomUUID().toString().substring(0, 8);
        this.accountHolderName = accountHolderName;
        this.active = true;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
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
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
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

    public void transfer(BankAccount destinationAccount, double amount) {
        if (!this.active || !destinationAccount.isActive()) {
            throw new IllegalStateException("One or both accounts not active");
        }
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add(new Transaction(Transaction.Type.TRANSFER_OUT, amount, this.balance, "Transfer to " + destinationAccount.getAccountId()));
            
            destinationAccount.balance += amount; 
            destinationAccount.transactionHistory.add(new Transaction(Transaction.Type.TRANSFER_IN, amount, destinationAccount.balance, "Transfer from " + this.accountId));
        } else {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    public void collectFee(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount > 0) {
            this.balance -= amount;
            this.transactionHistory.add(new Transaction(Transaction.Type.FEE, amount, this.balance, "Fee Collection"));
        } else {
            throw new IllegalArgumentException("Invalid fee amount");
        }
    }

    @Override
    public String toString() {
        return accountId + " - " + accountHolderName + " ($" + String.format("%.2f", balance) + ")";
    }

    public void close() {
        if (!active) {
            throw new IllegalStateException("Account is already closed.");
        }
        this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }
}