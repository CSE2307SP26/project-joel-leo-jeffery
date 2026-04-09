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

    @Test
    public void testTransferSuccess() {
        BankAccount acc1 = new BankAccount();
        BankAccount acc2 = new BankAccount();
        acc1.deposit(100);
        acc1.transferTo(acc2, 40);
        assertEquals(60, acc1.getBalance(), 0.01);
        assertEquals(40, acc2.getBalance(), 0.01);
    }

    @Test
    public void testTransferInvalidAmount() {
        BankAccount acc1 = new BankAccount();
        BankAccount acc2 = new BankAccount();
        acc1.deposit(50);
        try {
            acc1.transferTo(acc2, 100);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(50, acc1.getBalance(), 0.01);
            assertEquals(0, acc2.getBalance(), 0.01);
        }
    }
}
