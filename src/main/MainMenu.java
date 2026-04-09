package main;

import com.bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 8;
    private static final int MAX_SELECTION = 8;

    private List<BankAccount> accounts;
    private BankAccount currentAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.accounts = new ArrayList<>();
        this.currentAccount = null;
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        if (currentAccount != null) {
            System.out.println("Current account: " + currentAccount);
        } else {
            System.out.println("No account selected.");
        }
        System.out.println("1. Create a new account");
        System.out.println("2. Select an account");
        System.out.println("3. Make a deposit");
        System.out.println("4. Make a withdrawal");
        System.out.println("5. Check balance");
        System.out.println("6. View transaction history");
        System.out.println("7. Close this account");
        System.out.println("8. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                createAccount();
                break;
            case 2:
                selectAccount();
                break;
            case 3:
                performDeposit();
                break;
            case 4:
                performWithdrawal();
                break;
            case 5:
                performCheckBalance();
                break;
            case 6:
                viewTransactionHistory();
                break;
            case 7:
                closeAccount();
                break;
        }
    }

    public void createAccount() {
        keyboardInput.nextLine();
        System.out.print("Enter account holder name: ");
        String name = keyboardInput.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        BankAccount newAccount = new BankAccount(name);
        accounts.add(newAccount);
        currentAccount = newAccount;
        System.out.println("Account created: " + newAccount);
    }

    public void selectAccount() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts exist. Create one first.");
            return;
        }
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + accounts.get(i));
        }
        int choice = getUserSelection(accounts.size());
        currentAccount = accounts.get(choice - 1);
        System.out.println("Selected: " + currentAccount);
    }

    public void performDeposit() {
        if (currentAccount == null) {
            System.out.println("No account selected. Create or select one first.");
            return;
        }
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        currentAccount.deposit(depositAmount);
        System.out.println("Deposit successful. Balance: $" + String.format("%.2f", currentAccount.getBalance()));
    }

    public void performWithdrawal() {
        if (currentAccount == null) {
            System.out.println("No account selected. Create or select one first.");
            return;
        }
        double withdrawAmount = -1;
        while(withdrawAmount < 0 || withdrawAmount > currentAccount.getBalance()) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        currentAccount.withdraw(withdrawAmount);
        System.out.println("Withdrawal successful.");
    }

    public void performCheckBalance() {
        if (currentAccount == null) {
            System.out.println("No account selected. Create or select one first.");
            return;
        }
        System.out.println("Your balance is: $" + String.format("%.2f", currentAccount.getBalance()));
    }

    public void viewTransactionHistory() {
        if (currentAccount == null) {
            System.out.println("No account selected. Create or select one first.");
            return;
        }
        if (currentAccount.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("Transaction history:");
        for (Transaction t : currentAccount.getTransactionHistory()) {
            System.out.println("  " + t);
        }
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

    public void closeAccount() {
        if (currentAccount == null) {
            System.out.println("No account selected. Create or select one first.");
            return;
        }
        currentAccount.close();
        System.out.println("Account closed: " + currentAccount);
        currentAccount = null;
    }
}