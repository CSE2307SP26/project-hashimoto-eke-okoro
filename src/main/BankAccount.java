package main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.model.Transaction;

public class BankAccount {

    private final String accountId;
    private final String accountHolderName;
    private String nickname;
    private boolean active;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(String accountHolderName) {
        this.accountId = UUID.randomUUID().toString().substring(0, 8);
        this.accountHolderName = accountHolderName;
        this.active = true;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.nickname = "";
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getNickname() {
        return this.nickname;
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
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid fee amount");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Fee exceeds account balance");
        }
        this.balance -= amount;
        this.transactionHistory.add(new Transaction(Transaction.Type.FEE, amount, this.balance, "Fee Collection"));
    }

    public void addInterest(double rate) {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
        if (rate <= 0) {
            throw new IllegalArgumentException();
        }
        double interestAmount = this.balance * (rate / 100);
        this.balance += interestAmount;
        this.transactionHistory.add(new Transaction(Transaction.Type.INTEREST, interestAmount, balance, "Interest at " + rate + "%"));
    }

    @Override
    public String toString() {
        String displayName = nickname.isEmpty() ? accountHolderName : accountHolderName + " (" + nickname + ")";
        return accountId + " - " + displayName + " ($" + String.format("%.2f", balance) + ")";
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