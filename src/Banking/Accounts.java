package Banking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

// Base class for SavingsAccount and CurrentAccount
public abstract class Accounts implements Transaction, Serializable {

	private static final long serialVersionUID = 1L;

	// ================= ACCOUNT COUNTER =================

	private static int accountCounter = 1000;

	// ================= ACCOUNT DETAILS =================

	protected String accountNumber;
	protected double balance;
	protected Customer_ customer;

	// ================= TRANSACTION HISTORY =================

	protected ArrayList<String> transactions;

	// ================= RECENT ACTIONS =================

	protected transient Stack<String> recentActions;

	// ================= ACCOUNT SECURITY =================

	protected boolean locked;

	// ================= CONSTRUCTOR =================

	public Accounts(Customer_ customer, double openingBalance) {

		if (customer == null) {
			throw new IllegalArgumentException("Customer cannot be null.");
		}

		if (openingBalance < 0) {
			throw new IllegalArgumentException("Opening balance cannot be negative.");
		}

		accountCounter++;

		accountNumber = "A" + accountCounter;

		this.customer = customer;
		this.balance = openingBalance;

		transactions = new ArrayList<String>();
		recentActions = new Stack<String>();

		locked = false;

		transactions.add("OPENING DEPOSIT: Rs. " + openingBalance);
	}

	// ================= REGISTER EXISTING ACCOUNT =================

	public static void registerExistingAccountNumber(String existingAccountNumber) {

		if (existingAccountNumber == null) {
			return;
		}

		existingAccountNumber = existingAccountNumber.trim();

		if (existingAccountNumber.startsWith("A")) {

			try {

				int number = Integer.parseInt(existingAccountNumber.substring(1));

				if (number > accountCounter) {
					accountCounter = number;
				}

			} catch (NumberFormatException e) {

				System.out.println("Invalid account number: " + existingAccountNumber);
			}
		}
	}

	// ================= ACCOUNT COUNTER =================

	public static void ensureCounterAtLeast(int value) {

		if (value > accountCounter) {
			accountCounter = value;
		}
	}

	// ================= DEPOSIT =================

	@Override
	public void deposit(double amount) {

		deposit(amount, "DEPOSIT");
	}

	// Method Overloading
	public void deposit(double amount, String remark) {

		if (locked) {
			System.out.println("Account is locked.");
			return;
		}

		if (amount <= 0) {
			System.out.println("Deposit amount must be greater than zero.");
			return;
		}

		balance = balance + amount;

		transactions.add(remark + ": Rs. " + amount);

		pushRecent(remark);
	}

	// ================= WITHDRAW =================

	@Override
	public abstract void withdraw(double amount) throws InsufficientBalanceException;

	// ================= INTEREST =================

	public abstract double calculateInterest();

	// ================= ACCOUNT TYPE =================

	public abstract String getAccountType();

	// ================= RECENT ACTION =================

	protected void pushRecent(String action) {

		if (recentActions == null) {
			recentActions = new Stack<String>();
		}

		recentActions.push(action);

		// Keep only last 5 actions
		if (recentActions.size() > 5) {
			recentActions.remove(0);
		}
	}

	// ================= GET BALANCE =================

	public double getBalance() {

		return balance;
	}

	// ================= GET ACCOUNT NUMBER =================

	public String getAccountNumber() {

		return accountNumber;
	}

	// ================= MATCH ACCOUNT NUMBER =================

	public boolean matches(String accountNumber) {

		if (accountNumber == null) {
			return false;
		}

		return this.accountNumber.equalsIgnoreCase(accountNumber.trim());
	}

	// ================= GET CUSTOMER =================

	public Customer_ getCustomer() {

		return customer;
	}

	// ================= GET TRANSACTIONS =================

	public ArrayList<String> getTransactions() {

		return transactions;
	}

	// ================= LOCK STATUS =================

	public boolean isLocked() {

		return locked;
	}

	public void setLocked(boolean locked) {

		this.locked = locked;
	}

	// ================= DISPLAY DETAILS =================

	public void displayAccountDetails() {

		System.out.println("--------------------------------------");

		System.out.println("Account Number : " + accountNumber);

		System.out.println("Account Type   : " + getAccountType());

		System.out.println("Customer Name  : " + customer.getName());

		System.out.println("Balance        : Rs. " + balance);

		System.out.println("Interest       : Rs. " + calculateInterest());

		System.out.println("Status         : " + (locked ? "LOCKED" : "ACTIVE"));

		System.out.println("--------------------------------------");
	}

	// ================= TO STRING =================

	@Override
	public String toString() {

		return accountNumber + " | " + getAccountType() + " | " + customer.getName() + " | Rs. " + balance + " | "
				+ (locked ? "LOCKED" : "ACTIVE");
	}
}