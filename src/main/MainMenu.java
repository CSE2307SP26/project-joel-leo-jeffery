package main;

import java.util.Scanner;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 5;
	private static final int MAX_SELECTION = 5;

	private ArrayList<BankAccount> userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new ArrayList<BankAccount>();
        this.userAccount.add(new BankAccount());
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. View transaction history");
        System.out.println("3. Create a new account");
        System.out.println("4. Close the account");
        System.out.println("5. Exit the app");
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
                viewTransactionHistory();
                break;
            case 3:
                createNewAccount();
                break;
            case 4:
                closeExistingAccount();
                break;
        }
    }

    public void performDeposit() {
        if(userAccount.get(0).isClosed()) {
            System.out.println("This account is closed.");
            return;
        }

        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.get(0).deposit(depositAmount);
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for(String transaction : userAccount.get(0).getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    public void createNewAccount() {
        userAccount.add(new BankAccount());
        System.out.println("A new account has been created.");
        System.out.println("You now have " + userAccount.size() + " account(s).");
    }

    public void closeExistingAccount() {
        if(userAccount.get(0).isClosed()) {
            System.out.println("This account is already closed.");
        } else {
            userAccount.get(0).closeAccount();
            System.out.println("The account has been closed.");
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

}
