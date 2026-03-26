package main;

import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<String>();
        this.transactionHistory.add("Account opened with balance $0.00");
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited $" + String.format("%.2f", amount));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public double getBalance() {
        return this.balance;
    }
}
