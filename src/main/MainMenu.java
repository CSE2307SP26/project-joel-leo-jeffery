package main;

import java.util.Scanner;
import java.util.ArrayList;

public class MainMenu {

    private static final int EXIT_SELECTION = 15;
    private static final int MAX_SELECTION = 15;

    private ArrayList<BankAccount> userAccounts;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccounts = new ArrayList<BankAccount>();
        this.userAccounts.add(new BankAccount());
        this.userAccounts.get(0).setAccountName("Account 1");
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

        System.out.println("7. Transfer money between accounts");
        System.out.println("8. Add interest payment to an account");

        System.out.println("9. View all accounts and balances");

        System.out.println("10. View all account summaries");
        System.out.println("11. Reopen a closed account");

        System.out.println("12. Rename an account");
        System.out.println("13. Sort accounts by balance");
        System.out.println("14. Delete an empty account");

        System.out.println("15. Exit the app");
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
                transferBetweenAccounts();
                break;
            case 8:
                performInterestPayment();
                break;
            case 9:
                viewAllAccountsAndBalances();
                break;
            case 10:
                viewAllAccountSummaries();
                break;
            case 11:
                reopenClosedAccount();
                break;           
            case 12:
                renameAccount();
                break;
            case 13:
                sortAccountsByBalance();
                break;
            case 14:
                deleteEmptyAccount();
                break;
            case 15:
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
        userAccounts.get(userAccounts.size() - 1).setAccountName("Account " + userAccounts.size());
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

    public void performInterestPayment() {
        BankAccount selectedAccount = getSelectedAccount();

        if(selectedAccount.isClosed()) {
            System.out.println("This account is closed.");
            return;
        }

        double interestAmount = -1;
        while(interestAmount <= 0) {
            System.out.print("How much interest would you like to add: ");
            interestAmount = keyboardInput.nextDouble();
        }
        selectedAccount.addInterestPayment(interestAmount);
        System.out.println("Interest payment applied.");
    }

    public void transferBetweenAccounts() {
        int fromAccountNumber = getAccountNumber("transfer from");
        int toAccountNumber = getDifferentAccountNumber("transfer to", fromAccountNumber);
        BankAccount fromAccount = userAccounts.get(fromAccountNumber - 1);
        BankAccount toAccount = userAccounts.get(toAccountNumber - 1);

        if(fromAccount.isClosed() || toAccount.isClosed()) {
            System.out.println("Cannot transfer using a closed account.");
            return;
        }
        double transferAmount = -1;
        while(transferAmount <= 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextInt();
        }
        try {
            fromAccount.transferTo(toAccount, transferAmount);
            System.out.println("Transfer completed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transfer.");
        } catch (IllegalStateException e) {
            System.out.println("Cannot transfer using a closed account.");
        }   
    }

    public void viewAllAccountsAndBalances() {
        System.out.println("All accounts and balances:");
        for(int i = 0; i < userAccounts.size(); i++) {
            BankAccount account = userAccounts.get(i);
            String status = account.isClosed() ? "closed" : "open";
            System.out.println("Account " + (i + 1) + " (" + account.getAccountName() + "): $" + account.getBalance() + " (" + status + ")");
        }
    }

    private int getAccountNumber(String action) {
        int accountNumber = 0;
        while(accountNumber < 1 || accountNumber > userAccounts.size()) {
            System.out.print("Which account would you like to " + action + ": ");
            accountNumber = keyboardInput.nextInt();
        }
        return accountNumber;
    }

    private int getDifferentAccountNumber(String action, int exclude) {
        int accountNumber = 0;
        while(accountNumber < 1 || accountNumber > userAccounts.size() || accountNumber == exclude) {
            System.out.print("Which account would you like to " + action + ": ");
            accountNumber = keyboardInput.nextInt();
        }   
        return accountNumber;
    }

    public void viewAllAccountSummaries() {
        System.out.println("Account Summary:");
        for(int i = 0; i < userAccounts.size(); i++) {
            System.out.println(userAccounts.get(i).getAccountSummary(i + 1));
        }
    }

    public void renameAccount() {
        BankAccount selectedAccount = getSelectedAccount();
        System.out.print("Enter new account name: ");
        keyboardInput.nextLine();
        String newName = keyboardInput.nextLine();

        try {
            selectedAccount.setAccountName(newName);
            System.out.println("Account renamed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account name.");
        }
    }

    public void sortAccountsByBalance() {
        ArrayList<BankAccount> sortedAccounts = new ArrayList<BankAccount>(userAccounts);
        for(int i = 0; i < sortedAccounts.size() - 1; i++) {
            for(int j = 0; j < sortedAccounts.size() - 1 - i; j++) {
                if(sortedAccounts.get(j).getBalance() > sortedAccounts.get(j + 1).getBalance()) {
                    BankAccount temp = sortedAccounts.get(j);
                    sortedAccounts.set(j, sortedAccounts.get(j + 1));
                    sortedAccounts.set(j + 1, temp);
                }
            }
        }
        System.out.println("Accounts sorted by balance:");
        for(int i = 0; i < sortedAccounts.size(); i++) {
            BankAccount account = sortedAccounts.get(i);
            System.out.println(
                account.getAccountName() + ": $" +
                String.format("%.2f", account.getBalance())
            );
        }
    }

    public void deleteEmptyAccount() {
        if(userAccounts.size() == 1) {
            System.out.println("Cannot delete your only account.");
            return;
        }
        int accountNumber = getAccountNumber("delete");
        BankAccount selectedAccount = userAccounts.get(accountNumber - 1);
        if(selectedAccount.getBalance() != 0) {
            System.out.println("Only empty accounts can be deleted.");
            return;
        }
        userAccounts.remove(accountNumber - 1);
        System.out.println("Account deleted.");
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
