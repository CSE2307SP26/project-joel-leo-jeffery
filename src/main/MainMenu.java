package main;

import java.util.Scanner;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 9;
    private static final int MAX_SELECTION = 9;

    private ArrayList<BankAccount> userAccounts;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccounts = new ArrayList<BankAccount>();
        this.userAccounts.add(new BankAccount());
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
        System.out.println("8. View all account summaries");
        System.out.println("9. Reopen a closed account");
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
                break;
            case 3:
                performWithdrawal();
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                createNewAccount();
                break;
            case 6:
                closeExistingAccount();
                break;
            case 7:
                viewAllAccountSummaries();
                break;
            case 8:
                reopenClosedAccount();
                break;
            case 9:
                break;
        }
    }

    public void performDeposit() {
        BankAccount selectedAccount = getSelectedAccount();

        if(selectedAccount.isClosed()) {
            System.out.println("This account is closed.");
            return;
        }

        double depositAmount = -1;
        while(depositAmount <= 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        selectedAccount.deposit(depositAmount);
    }

    public void viewTransactionHistory() {
        BankAccount selectedAccount = getSelectedAccount();

        System.out.println("Transaction History:");
        for(String transaction : selectedAccount.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    public void createNewAccount() {
        userAccounts.add(new BankAccount());
        System.out.println("A new account has been created.");
        System.out.println("This is account number " + userAccounts.size() + ".");
        System.out.println("You now have " + userAccounts.size() + " account(s).");
    }

    public void closeExistingAccount() {
        BankAccount selectedAccount = getSelectedAccount();

        if(selectedAccount.isClosed()) {
            System.out.println("This account is already closed.");
        } else {
            selectedAccount.closeAccount();
            System.out.println("The account has been closed.");
        }
    }

    public void reopenClosedAccount() {
        BankAccount selectedAccount = getSelectedAccount();

        if(selectedAccount.isClosed()) {
            selectedAccount.reopenAccount();
            System.out.println("The account has been reopened.");
        } else {
            System.out.println("This account is already open.");
        }
    }

    public void performBalanceCheck() {
        BankAccount selectedAccount = getSelectedAccount();
        System.out.println("Current balance: " + selectedAccount.getBalance());
    }

    public void performWithdrawal() {
        BankAccount selectedAccount = getSelectedAccount();

        if(selectedAccount.isClosed()) {
            System.out.println("This account is closed.");
            return;
        }

        double withdrawalAmount = -1;
        while(withdrawalAmount <= 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawalAmount = keyboardInput.nextInt();
        }
        selectedAccount.withdraw(withdrawalAmount);
    }

    public void viewAllAccountSummaries() {
        System.out.println("Account Summary:");
        for(int i = 0; i < userAccounts.size(); i++) {
            System.out.println(userAccounts.get(i).getAccountSummary(i + 1));
        }
    }

    private BankAccount getSelectedAccount() {
        int accountNumber = 0;
        while(accountNumber < 1 || accountNumber > userAccounts.size()) {
            System.out.print("Which account would you like to use: ");
            accountNumber = keyboardInput.nextInt();
        }
        return userAccounts.get(accountNumber - 1);
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
