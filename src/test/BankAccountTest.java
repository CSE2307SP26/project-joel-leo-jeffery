package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testInitialBalance() {
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testOpenAccountSummary() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals("Account 1: Balance $50.00, Open", testAccount.getAccountSummary(1));
    }

    @Test
    public void testClosedAccountSummary() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        assertEquals("Account 2: Balance $0.00, Closed", testAccount.getAccountSummary(2));
    }

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testBalanceAfterMultipleDeposits() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.deposit(25);
        assertEquals(75, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(0);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testNegativeWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdrawTooMuch() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(150);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdrawTooMuchKeepsBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(150);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(100, testAccount.getBalance(), 0.01);
        }
    }

    @Test
    public void testReopenClosedAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        testAccount.reopenAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testReopenOpenAccountDoesNothing() {
        BankAccount testAccount = new BankAccount();
        testAccount.reopenAccount();
        assertEquals(false, testAccount.isClosed());
    public void testTransactionHistoryStartsWithAccountOpened() {
        BankAccount testAccount = new BankAccount();
        assertEquals("Account opened with balance $0.00", testAccount.getTransactionHistory().get(0));
    }

    @Test
    public void testTransactionHistoryAfterDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertTrue(testAccount.getTransactionHistory().contains("Deposited $50.00"));
    }

    @Test
    public void testTransactionHistoryAfterWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertTrue(testAccount.getTransactionHistory().contains("Withdrew $40.00"));
    }

    @Test
    public void testCloseAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        assertTrue(testAccount.isClosed());
    }

    @Test
    public void testTransactionHistoryAfterCloseAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        assertTrue(testAccount.getTransactionHistory().contains("Account closed"));
    }

    @Test
    public void testDepositIntoClosedAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.closeAccount();
        try {
            testAccount.deposit(50);
            fail();
        } catch (IllegalStateException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testWithdrawFromClosedAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.closeAccount();
        try {
            testAccount.withdraw(50);
            fail();
        } catch (IllegalStateException e) {
            // do nothing, test passes
        }
    }
}
