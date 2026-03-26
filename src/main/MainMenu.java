package main;

import java.util.Scanner;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 7;
	  private static final int MAX_SELECTION = 7;

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
      
        System.out.println("2. Check account balance");
        System.out.println("3. Make a withdrawal");

        System.out.println("4. View transaction history");
        System.out.println("5. Create a new account");
        System.out.println("6. Close the account");
      
        System.out.println("10. Exit the app");
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
                performBalanceCheck();
            case 3:
                performWithdrawal();
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                createNewAccount();
                break;
            case 6:
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

    public void performBalanceCheck() {
        System.out.println("Current balance: " + userAccount.getBalance());
    }

    public void performWithdrawal() {
        double withdrawalAmount = -1;
        while(withdrawalAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawalAmount = keyboardInput.nextInt();
        }
        userAccount.withdraw(withdrawalAmount);
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
