package test;

import main.BankAccount;

import com.bank.model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount("Test User");
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount testAccount = new BankAccount("Test User");
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
        BankAccount testAccount = new BankAccount("Test User");
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
        BankAccount testAccount = new BankAccount("Test User");
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testDepositAddsTransaction() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        assertEquals(1, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testMultipleTransactionsTracked() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(200);
        testAccount.deposit(50);
        testAccount.withdraw(30);
        assertEquals(3, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testTransactionHasCorrectType() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.withdraw(25);
        assertEquals(Transaction.Type.DEPOSIT, testAccount.getTransactionHistory().get(0).getType());
        assertEquals(Transaction.Type.WITHDRAWAL, testAccount.getTransactionHistory().get(1).getType());
    }

    @Test
    public void testTransactionHasCorrectBalance() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(500);
        testAccount.withdraw(150);
        assertEquals(500.0, testAccount.getTransactionHistory().get(0).getBalanceAfter(), 0.01);
        assertEquals(350.0, testAccount.getTransactionHistory().get(1).getBalanceAfter(), 0.01);
    }


    //Create a new bank account
    @Test
    public void testAccountHasOwnerName() {
        BankAccount testAccount = new BankAccount("Erik");
        assertEquals("Erik", testAccount.getAccountHolderName());
    }

    @Test
    public void testAccountHasUniqueId() {
        BankAccount account1 = new BankAccount("Erik");
        BankAccount account2 = new BankAccount("Louis");
        assertNotEquals(account1.getAccountId(), account2.getAccountId());
    }

    @Test
    public void testMultipleAccountsIndependent() {
        BankAccount account1 = new BankAccount("Erik");
        BankAccount account2 = new BankAccount("Louis");
        account1.deposit(500);
        account2.deposit(200);
        assertEquals(500, account1.getBalance(), 0.01);
        assertEquals(200, account2.getBalance(), 0.01);
    }

    //Close a bank account
    @Test
    public void testCloseAccount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.close();
        assertEquals(false, testAccount.isActive());
    }


    @Test
    public void testDepositIntoClosedAccount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.close();
        assertThrows(IllegalStateException.class, () -> testAccount.deposit(100));
    }

    @Test
    public void testWithdrawFromClosedAccount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.close();
        assertThrows(IllegalStateException.class, () -> testAccount.withdraw(50));
    }

    @Test
    public void testCloseAlreadyClosedAccount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.close();
        assertThrows(IllegalStateException.class, () -> testAccount.close());
    }

    @Test
    public void testNewAccountIsActive() {
        BankAccount testAccount = new BankAccount("Test User");
        assertEquals(true, testAccount.isActive());
    }

    //transfer
    
    @Test
    public void testTransferSuccess() {
        BankAccount account1 = new BankAccount("User 1");
        BankAccount account2 = new BankAccount("User 2");
        account1.deposit(100);
        account1.transfer(account2, 40);
        assertEquals(60, account1.getBalance(), 0.01);
        assertEquals(40, account2.getBalance(), 0.01);
    }

    @Test
    public void testTransferInsufficientFunds() {
        BankAccount account1 = new BankAccount("User 1");
        BankAccount account2 = new BankAccount("User 2");
        account1.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account1.transfer(account2, 100));
    }

    @Test
    public void testTransferWithClosedAccount() {
        BankAccount account1 = new BankAccount("User 1");
        BankAccount account2 = new BankAccount("User 2");
        account1.deposit(100);
        account2.close();
        assertThrows(IllegalStateException.class, () -> account1.transfer(account2, 40));
    }

    //collect Fee

    @Test
    public void testCollectFeeSuccess() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.collectFee(15);
        assertEquals(85, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testCollectFeeNegativeAmount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> testAccount.collectFee(-10));
    }

    //Interest

    @Test
    public void testAddInterest() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(1000);
        testAccount.addInterest(10);
        assertEquals(1100.0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testAddInterestRecordsTransaction() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(500);
        testAccount.addInterest(5);
        assertEquals(2, testAccount.getTransactionHistory().size());
        assertEquals(Transaction.Type.INTEREST, testAccount.getTransactionHistory().get(1).getType());
    }

    @Test
    public void testAddInterestToClosedAccount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.close();
        assertThrows(IllegalStateException.class, () -> testAccount.addInterest(5));
    }

    @Test
    public void testAddZeroInterest() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> testAccount.addInterest(0));
    }

    //Bug fixes

    @Test
    public void testCollectFeeExceedsBalance() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> testAccount.collectFee(100));
    }

    //Nickname

    @Test
    public void testSetNickname() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.setNickname("Savings");
        assertEquals("Savings", testAccount.getNickname());
    }

    @Test
    public void testNicknameStartsEmpty() {
        BankAccount testAccount = new BankAccount("Test User");
        assertEquals("", testAccount.getNickname());
    }

    //Mini-statement

    @Test
    public void testRecentTransactionsReturnsCorrectCount() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.deposit(200);
        testAccount.deposit(300);
        testAccount.withdraw(50);
        assertEquals(2, testAccount.getRecentTransactions(2).size());
    }

    @Test
    public void testRecentTransactionsReturnsLatest() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        testAccount.deposit(200);
        testAccount.withdraw(50);
        List<Transaction> recent = testAccount.getRecentTransactions(1);
        assertEquals(Transaction.Type.WITHDRAWAL, recent.get(0).getType());
    }

    @Test
    public void testRecentTransactionsMoreThanExist() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        assertEquals(1, testAccount.getRecentTransactions(5).size());
    }

    @Test
    public void testRecentTransactionsZero() {
        BankAccount testAccount = new BankAccount("Test User");
        testAccount.deposit(100);
        assertEquals(0, testAccount.getRecentTransactions(0).size());
    }
}