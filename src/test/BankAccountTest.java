package test;

import main.BankAccount;

import com.bank.model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.withdraw(100);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testFailedWithdrawDoesNotAddTransaction() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.withdraw(100);
        } catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals(1, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testTransactionHistoryStartsEmpty() {
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testDepositAddsTransaction() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        assertEquals(1, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testMultipleTransactionsTracked() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(200);
        testAccount.deposit(50);
        testAccount.withdraw(30);
        assertEquals(3, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testTransactionHasCorrectType() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(25);
        assertEquals(Transaction.Type.DEPOSIT, testAccount.getTransactionHistory().get(0).getType());
        assertEquals(Transaction.Type.WITHDRAWAL, testAccount.getTransactionHistory().get(1).getType());
    }

    @Test
    public void testTransactionHasCorrectBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(500);
        testAccount.withdraw(150);
        assertEquals(500.0, testAccount.getTransactionHistory().get(0).getBalanceAfter(), 0.01);
        assertEquals(350.0, testAccount.getTransactionHistory().get(1).getBalanceAfter(), 0.01);
    }


}
