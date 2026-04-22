package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private boolean locked;
    private ArrayList<String> transactionHistory;
    private String accountName;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
        this.locked = false;
        this.transactionHistory = new ArrayList<String>();
        this.transactionHistory.add("Account opened with balance $0.00");
        this.accountName = "Unnamed Account";
    }

    public void deposit(double amount) {
        if(this.closed || this.locked) {
            throw new IllegalStateException();
        }

        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited $" + String.format("%.2f", amount));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if(this.closed || this.locked) {
            throw new IllegalStateException();
        }

        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew $" + String.format("%.2f", amount));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void transferTo(BankAccount otherAccount, double amount) {
        if(otherAccount == null) {
            throw new IllegalArgumentException();
        }
        if(this.closed || otherAccount.isClosed() || this.locked || otherAccount.isLocked()) {
            throw new IllegalStateException();
        }
        if(amount <= 0 || amount > this.balance) {
            throw new IllegalArgumentException();
        }
        this.balance -= amount;
        otherAccount.deposit(amount);
        this.transactionHistory.add("Transferred $" + String.format("%.2f", amount));
    }

    public void addInterestPayment(double interestAmount) {
        if(this.closed || this.locked) {
            throw new IllegalStateException();
        }
        if(interestAmount <= 0) {
            throw new IllegalArgumentException();
        }
        this.balance += interestAmount;
        this.transactionHistory.add("Interest payment added $" + String.format("%.2f", interestAmount));
    }
   
    public ArrayList<String> getTransactionHistory() {
        return this.transactionHistory;
    }
    
    public void closeAccount() {
        if(!this.closed) {
            this.closed = true;
            this.locked = false;
            this.transactionHistory.add("Account closed");
        }
    }

    public void reopenAccount() {
        if(this.closed) {
            this.closed = false;
            this.transactionHistory.add("Account reopened");
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void lockAccount() {
        if(!this.closed && !this.locked) {
            this.locked = true;
            this.transactionHistory.add("Account locked");
        }
    }

    public void unlockAccount() {
        if(!this.closed && this.locked) {
            this.locked = false;
            this.transactionHistory.add("Account unlocked");
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

    public String getAccountSummary(int accountNumber) {
        String accountStatus = "Open";
        if(this.closed) {
            accountStatus = "Closed";
        }
        return "Account " + accountNumber + " (" + this.accountName + "): Balance $" + String.format("%.2f", this.balance) + ", " + accountStatus;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        if(accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.accountName = accountName;
        this.transactionHistory.add("Account renamed to " + accountName);
    }

    public double getBalance() {
        return this.balance;
    }
}
