package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BankAccount {

    private double balance;
    private boolean closed;
    private boolean locked;
    private boolean lowBalanceAlertEnabled;
    private double lowBalanceAlertThreshold;
    private ArrayList<String> transactionHistory;
    private ArrayList<FeeRecord> feeHistory;
    private ArrayList<FeeRecord> scheduledFees;
    private String accountName;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
        this.locked = false;
        this.lowBalanceAlertEnabled = false;
        this.lowBalanceAlertThreshold = 0;
        this.transactionHistory = new ArrayList<String>();
        this.feeHistory = new ArrayList<FeeRecord>();
        this.scheduledFees = new ArrayList<FeeRecord>();
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

    public void chargeFee(double amount, String reason) {
        if(this.closed || this.locked) {
            throw new IllegalStateException();
        }
        if(amount <= 0 || amount > this.balance || reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        String normalizedReason = reason.trim();
        this.balance -= amount;
        this.feeHistory.add(new FeeRecord(amount, LocalDate.now(), normalizedReason, "Charged"));
        this.transactionHistory.add("Fee charged $" + String.format("%.2f", amount) + " for " + normalizedReason);
    }

    public void scheduleFee(double amount, String reason) {
        if(this.closed || this.locked) {
            throw new IllegalStateException();
        }
        if(amount <= 0 || reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        String normalizedReason = reason.trim();
        FeeRecord scheduledFee = new FeeRecord(amount, LocalDate.now(), normalizedReason, "Scheduled");
        this.scheduledFees.add(scheduledFee);
        this.feeHistory.add(scheduledFee);
        this.transactionHistory.add("Fee scheduled $" + String.format("%.2f", amount) + " for " + normalizedReason);
    }

    public void waiveScheduledFee(int scheduledFeeIndex) {
        if(scheduledFeeIndex < 0 || scheduledFeeIndex >= this.scheduledFees.size()) {
            throw new IllegalArgumentException();
        }

        FeeRecord scheduledFee = this.scheduledFees.remove(scheduledFeeIndex);
        this.feeHistory.add(new FeeRecord(scheduledFee.getAmount(), LocalDate.now(), scheduledFee.getReason(), "Waived"));
        this.transactionHistory.add("Scheduled fee waived $" + String.format("%.2f", scheduledFee.getAmount())
            + " for " + scheduledFee.getReason());
    }

    public ArrayList<FeeRecord> getFeeHistory() {
        return new ArrayList<FeeRecord>(this.feeHistory);
    }

    public ArrayList<FeeRecord> getScheduledFees() {
        return new ArrayList<FeeRecord>(this.scheduledFees);
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

    public void setLowBalanceAlertThreshold(double threshold) {
        if(threshold <= 0) {
            throw new IllegalArgumentException();
        }

        this.lowBalanceAlertEnabled = true;
        this.lowBalanceAlertThreshold = threshold;
    }

    public void clearLowBalanceAlertThreshold() {
        this.lowBalanceAlertEnabled = false;
        this.lowBalanceAlertThreshold = 0;
    }

    public boolean hasLowBalanceAlert() {
        return this.lowBalanceAlertEnabled;
    }

    public double getLowBalanceAlertThreshold() {
        return this.lowBalanceAlertThreshold;
    }

    public boolean isLowBalanceAlertTriggered(double previousBalance) {
        return this.lowBalanceAlertEnabled
            && previousBalance >= this.lowBalanceAlertThreshold
            && this.balance < this.lowBalanceAlertThreshold;
    }

    public String getAccountSummary(int accountNumber) {
        String accountStatus = "Open";
        if(this.closed) {
            accountStatus = "Closed";
        } else if(this.locked) {
            accountStatus = "Locked";
        }

        return "Account " + accountNumber + " (" + this.accountName + "): Balance $"
            + String.format("%.2f", this.balance) + ", " + accountStatus;
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

    public static class FeeRecord {
        private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        private final double amount;
        private final LocalDate date;
        private final String reason;
        private final String status;

        public FeeRecord(double amount, LocalDate date, String reason, String status) {
            this.amount = amount;
            this.date = date;
            this.reason = reason;
            this.status = status;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getReason() {
            return reason;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return date.format(DATE_FORMAT) + " - " + status + " - $"
                + String.format("%.2f", amount) + " - " + reason;
        }
    }
}
