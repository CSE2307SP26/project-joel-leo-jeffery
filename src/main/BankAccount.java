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

      public void transferTo(BankAccount otherAccount, double amount) {
        if(otherAccount == null) {
            throw new IllegalArgumentException();
        }
        if(this.closed || otherAccount.isClosed()) {
            throw new IllegalStateException();
        }
        if(amount <= 0 || amount > this.balance) {
            throw new IllegalArgumentException();
        }
        this.balance -= amount;
        otherAccount.deposit(amount);
        this.transactionHistory.add("Transferred $" + String.format("%.2f", amount));
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

    public double getBalance() {
        return this.balance;
    }
}
