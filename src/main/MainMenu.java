package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdrawal");
        System.out.println("3. Check balance");
        System.out.println("2. Exit the app");

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
                performDeposit();
                break;
            case 2:
                performWithdrawal();
                break;
            case 3:
                preformCheckBalance();
                break;

        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
    }

    public void performWithdrawal() {
        double withdrawAmount = -1;
        while(withdrawAmount < 0 || withdrawAmount > userAccount.getBalance()) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        
        try {
            userAccount.withdraw(withdrawAmount);
            System.out.println("Withdrawal successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid amount or insufficient funds.");
        }
    }

    public void preformCheckBalance() {
        System.out.println("Your balance is: " + userAccount.getBalance());
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

}
