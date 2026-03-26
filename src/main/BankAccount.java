package main;

import com.bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount() {
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
}
