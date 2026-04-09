package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private ArrayList<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
        this.transactionHistory = new ArrayList<String>();
        this.transactionHistory.add("Account opened with balance $0.00");
    }

    public void deposit(double amount) {
        if(this.closed) {
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
        if(this.closed) {
            throw new IllegalStateException();
        }

        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew $" + String.format("%.2f", amount));
        } else {
            throw new IllegalArgumentException();
        }
    }
   
    public ArrayList<String> getTransactionHistory() {
        return this.transactionHistory;
    }
    
    public void closeAccount() {
        if(!this.closed) {
            this.closed = true;
            this.transactionHistory.add("Account closed");
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public String getAccountSummary(int accountNumber) {
        String accountStatus = "Open";
        if(this.closed) {
            accountStatus = "Closed";
        }
        return "Account " + accountNumber + ": Balance $" + String.format("%.2f", this.balance) + ", " + accountStatus;
    }

    public double getBalance() {
        return this.balance;
    }
}
