package Banking;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BankApp {
	private static final String CUSTOMER_FILE = "objcust.src";
	private static final String ACCOUNT_FILE = "accounts.src";
	private static final String LOAN_FILE = "loans.src";
	private static final String COMPLAINT_FILE = "complaints.src";
	private static final String TICKET_FILE = "tickets.src";
	private static final String STAFF_FILE = "staff.src";
	private static final String TRANSACTION_LOG_FILE = "transaction_log.txt";
	private static final String FAILED_LOGIN_FILE = "failed_logins.txt";
	private static final String CONSOLE_LOG_FILE = "console_output.txt";
	private static final String BACKUP_DIR = "backup";

	private static final double HIGH_VALUE_LOAN_LIMIT = 100000;
	private static final double HIGH_VALUE_CUSTOMER_LIMIT = 50000;

	private Scanner sc = new Scanner(System.in);

	// ==================================================
	// ===================== MAIN =======================
	// ==================================================
	// Section 9.7: BankApp is the Main class where the program starts.
	// Shows the login screen with all 12 roles, loops until Exit.
	public static void main(String[] args) {
		setupConsoleLogging();
		BankApp app = new BankApp();
		app.loginScreen();
	}

	// Section 8: File Handling --- wraps System.out in a TeeOutputStream so
	// every screen printed from here on (login screen, every role's menu,
	// every result/error message) is written to CONSOLE_LOG_FILE as well as
	// shown on screen. Uses FileOutputStream in append mode, so old sessions
	// are kept, not overwritten.
	private static void setupConsoleLogging() {
		try {
			FileOutputStream fos = new FileOutputStream(CONSOLE_LOG_FILE, true);
			PrintStream teed = new PrintStream(new TeeOutputStream(System.out, fos), true);
			System.setOut(teed);
			System.out.println();
			System.out.println("========== NEW SESSION: " + new java.util.Date() + " ==========");
		} catch (FileNotFoundException e) {
			System.out.println("Warning: could not open " + CONSOLE_LOG_FILE + " for logging: " + e.getMessage());
		}
	}

	private void loginScreen() {
		while (true) {
			System.out.println("===== " + Bank.BANK_NAME + " =====");
			System.out.println("1. Customer Login");
			System.out.println("2. Cashier Login");
			System.out.println("3. Branch Manager Login");
			System.out.println("4. Bank Manager Login");
			System.out.println("5. Loan Officer Login");
			System.out.println("6. Customer Service Executive Login");
			System.out.println("7. Relationship Manager Login");
			System.out.println("8. Accountant Login");
			System.out.println("9. Auditor Login");
			System.out.println("10. Security Officer Login");
			System.out.println("11. IT Administrator Login");
			System.out.println("12. Help Desk Login");
			System.out.println("13. Registration (New Customer)");
			System.out.println("14. Exit");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				customerMenu();
				break;
			case 2:
				cashierMenu();
				break;
			case 3:
				branchManagerMenu();
				break;
			case 4:
				bankManagerMenu();
				break;
			case 5:
				loanOfficerMenu();
				break;
			case 6:
				customerServiceMenu();
				break;
			case 7:
				relationshipManagerMenu();
				break;
			case 8:
				accountantMenu();
				break;
			case 9:
				auditorMenu();
				break;
			case 10:
				securityOfficerMenu();
				break;
			case 11:
				itAdministratorMenu();
				break;
			case 12:
				helpDeskMenu();
				break;
			case 13:
				registrationMenu();
				break;
			case 14:
				System.out.println("Thank you for using " + Bank.BANK_NAME + ". Goodbye!");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// ==================================================
	// ================ REGISTRATION ====================
	// ==================================================
	// Lets a brand-new customer register themselves and open their first
	// account, without needing a Branch Manager to do it for them. Reuses
	// the same createAccount() that Branch Manager uses, so the new
	// customer/account ends up in the exact same objcust.src / accounts.src
	// files as every other account --- one shared data set, as README says.
	private void registrationMenu() {
		boolean back = false;
		while (!back) {
			System.out.println();
			System.out.println("===== NEW CUSTOMER REGISTRATION =====");
			System.out.println("1. Register New Account");
			System.out.println("2. Back to Login Menu");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				registerNewCustomer();
				break;
			case 2:
				back = true;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Registration --- collects the same details Branch Manager collects
	// (Section 9.1: Customer_) and opens the customer's first account.
	// FIX: mobile number and password are now validated (looped until
	// valid) instead of being accepted as-is.
	public void registerNewCustomer() {
		System.out.print("Enter your full name: ");
		String name = sc.next();
		System.out.println("Enter mobile number : ");
		String mobile = readValidMobile();
		System.out.print("Enter address: ");
		String address = sc.next();
		String password = readValidPassword();
		System.out.print("Enter account type (Savings/Current): ");
		String type = sc.next();
		System.out.print("Enter opening deposit: ");
		double openingBalance = readDouble();
		if (openingBalance < 0) {
			System.out.println("Error: opening deposit cannot be negative.");
			return;
		}
		createAccount(name, mobile, address, password, type, openingBalance);
		System.out.println("Registration complete. Use 'Customer Login' from the main menu next time,");
		System.out.println("with the Account Number shown above and the password you just set.");
	}
	private static String generateAccountNumber() {

		Random random = new Random();

		StringBuilder accountNumber = new StringBuilder();

		accountNumber.append(1000 + random.nextInt(9000));

		accountNumber.append(10000000 + random.nextInt(90000000));

		return accountNumber.toString();
	}

	// Keeps asking until the user types a mobile number that satisfies
	// Customer_.isValidMobile() --- 10 digits, starting with 6/7/8/9.
	private String readValidMobile() {
		while (true) {
			System.out.print("Enter mobile number (10 digits, starting with 6-9): ");
			String mobile = sc.next();
			if (Customer_.isValidMobile(mobile)) {
				return mobile;
			}
			System.out.println("Invalid mobile number. " + Customer_.mobileRule());
		}
	}

	// Keeps asking until the user types a password that satisfies
	// Customer_.isValidPassword() --- upper + lower + digit + special char.
	private String readValidPassword() {
		while (true) {
			System.out.print("Set a password: ");
			String password = sc.next();
			if (Customer_.isValidPassword(password)) {
				return password;
			}
			System.out.println("Weak password. " + Customer_.passwordRule());
		}
	}

	// ==================================================
	// ================ CUSTOMER SECTION ===============
	// ==================================================
	// Section 4 planned menu: Check Balance, Mini Statement, Transfer Funds,
	// Apply for Loan, Check Loan Status
	// Section 9: Key methods --- checkBalance(), viewStatement(),
	// transferFunds(), applyForLoan()
	// FIX: customer now has to log in with account number + password before
	// reaching the menu, instead of anyone being able to type any account
	
	private void customerMenu() {
		/*
		 * private static String generateAccountNumber() { long number = 1000000000L +
		 * (long)(Math.random() * 9000000000L); // return String.valueOf(number); }
		 */
		/*
		 * private static String generateAccountNumber() {
		 * 
		 * Random random = new Random();
		 * 
		 * StringBuilder accountNumber = new StringBuilder();
		 * 
		 * accountNumber.append(1000 + random.nextInt(9000));
		 * 
		 * accountNumber.append(10000000 + random.nextInt(90000000));
		 * 
		 * return accountNumber.toString(); }
		 */
		System.out.print("Enter your account number : ");
		String accountNumber = sc.next();
		System.out.print("Enter your password: ");
		String password = sc.next();

		Accounts loggedIn;
		try {
			loggedIn = findAccountOrThrow(accountNumber);
		} catch (InvalidAccountException e) {
			System.out.println("Error: " + e.getMessage());
			logFailedLogin(accountNumber);
			return;
		}
		if (!loggedIn.getCustomer().checkPassword(password)) {
		    System.out.println("Error: incorrect password.");
		    logFailedLogin(accountNumber);
		    return;
		}
		if (loggedIn.isLocked()) {
			System.out.println("Error: this account is locked. Contact Security Officer.");
			return;
		}

		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== CUSTOMER MENU (" + accountNumber + ") =====");
			System.out.println("1. Check Balance");
			System.out.println("2. Mini Statement");
			System.out.println("3. Transfer Funds");
			System.out.println("4. Apply for Loan");
			System.out.println("5. Check Loan Status");
			System.out.println("6. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				checkBalance(accountNumber);
				break;
			case 2:
				viewStatement(accountNumber);
				break;
			case 3:
				System.out.print("Enter receiver's account number: ");
				String to = sc.next();
				System.out.print("Enter amount: ");
				double amt = readDouble();
				transferFunds(accountNumber, to, amt);
				break;
			case 4:
				System.out.print("Enter loan amount needed: ");
				double loanAmt = readDouble();
				applyForLoan(accountNumber, loanAmt);
				break;
			case 5:
				System.out.print("Enter your loan ID: ");
				checkLoanStatus(readInt());
				break;
			case 6:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Check Balance --- shows the current balance of the logged-in account
	public void checkBalance(String accountNumber) {
		try {
			Accounts acc = findAccountOrThrow(accountNumber);
			System.out.println("Balance for " + acc.getAccountNumber() + " : Rs. " + acc.getBalance());
		} catch (InvalidAccountException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Mini Statement --- shows the last few transactions of the account
	public void viewStatement(String accountNumber) {
		try {
			Accounts acc = findAccountOrThrow(accountNumber);
			System.out.println("===== MINI STATEMENT =====");
			System.out.println("Account Number: " + acc.getAccountNumber());
			System.out.println("Customer Name : " + acc.getCustomer().getName());
			System.out.println("Balance : Rs. " + acc.getBalance());
			System.out.println("Transactions :");
			for (String t : acc.getTransactions()) {
				System.out.println(" " + t);
			}
		} catch (InvalidAccountException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Transfer Funds --- sends money from one account to another
	// FIX: lookups now go through acc.matches(...) (via findInList), the
	// single shared matching rule, instead of a raw .equals() that only
	// worked if the stored format exactly matched what the user typed.
	public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		Accounts fromAcc = null;
		Accounts toAcc = null;
		for (Accounts acc : allAccounts) {
			if (acc.matches(fromAccountNumber)) {
				fromAcc = acc;
			}
			if (acc.matches(toAccountNumber)) {
				toAcc = acc;
			}
		}
		if (fromAcc == null || toAcc == null) {
			System.out.println("Error: One or both account numbers not found.");
			return;
		}
		try {
			if (fromAcc.isLocked() || toAcc.isLocked()) {
				throw new AccountLockedException("One of these accounts is locked. Contact Security Officer.");
			}
			withdrawChecked(fromAcc, amount);
			toAcc.deposit(amount);
			writeAllAccounts(ACCOUNT_FILE, allAccounts);
			logTransaction(fromAcc.getAccountNumber(), "TRANSFER OUT to " + toAcc.getAccountNumber(), amount);
			logTransaction(toAcc.getAccountNumber(), "TRANSFER IN from " + fromAcc.getAccountNumber(), amount);
			System.out.println("Rs. " + amount + " transferred from " + fromAcc.getAccountNumber() + " to "
					+ toAcc.getAccountNumber());
		} catch (InsufficientBalanceException | AccountLockedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Apply for Loan --- customer requests a loan by entering the amount needed
	public void applyForLoan(String accountNumber, double amount) {
		try {
			Accounts acc = findAccountOrThrow(accountNumber);
			ArrayList<Loan> allLoans = readAllLoans(LOAN_FILE);
			int newLoanId = nextLoanId(allLoans);
			Loan loan = new Loan(newLoanId, acc.getAccountNumber(), amount);
			allLoans.add(loan);
			writeAllLoans(LOAN_FILE, allLoans);
			System.out.println("Loan application submitted. Your Loan ID is " + newLoanId + " (Status: Pending)");
			if (amount >= HIGH_VALUE_LOAN_LIMIT) {
				System.out.println("Note: this is a high-value loan and will need Bank Manager approval.");
			}
		} catch (InvalidAccountException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Check Loan Status --- customer checks if their loan is approved, rejected, or
	// pending
	public void checkLoanStatus(int loanId) {
		ArrayList<Loan> allLoans = readAllLoans(LOAN_FILE);
		for (Loan loan : allLoans) {
			if (loan.getLoanId() == loanId) {
				System.out.println("Loan ID " + loanId + " Status: " + loan.getLoanStatus());
				return;
			}
		}
		System.out.println("Loan ID not found: " + loanId);
	}

	// ==================================================
	// ================= CASHIER SECTION ===============
	// ==================================================
	// Section 4 planned menu: Deposit Cash, Withdraw Cash
	// Section 9: Key methods --- depositCash(), withdrawCash()
	private void cashierMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== CASHIER MENU =====");
			System.out.println("1. Deposit Cash");
			System.out.println("2. Withdraw Cash");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				System.out.print("Enter account number: ");
				String depAcc = sc.next();
				System.out.print("Enter amount to deposit: ");
				double depAmt = readDouble();
				depositCash(depAcc, depAmt);
				break;
			case 2:
				System.out.print("Enter account number: ");
				String wAcc = sc.next();
				System.out.print("Enter amount to withdraw: ");
				double wAmt = readDouble();
				withdrawCash(wAcc, wAmt);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Deposit Cash --- add cash to any customer's account and update balance
	public void depositCash(String accountNumber, double amount) {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		try {
			Accounts acc = findInList(allAccounts, accountNumber);
			if (acc.isLocked()) {
				throw new AccountLockedException("Account " + acc.getAccountNumber() + " is locked. Contact Security Officer.");
			}
			acc.deposit(amount);
			writeAllAccounts(ACCOUNT_FILE, allAccounts);
			logTransaction(acc.getAccountNumber(), "DEPOSIT", amount);
			System.out.println("Rs. " + amount + " deposited to account " + acc.getAccountNumber());
		} catch (InvalidAccountException | AccountLockedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Withdraw Cash --- take cash out, but only if there is enough balance
	public void withdrawCash(String accountNumber, double amount) {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		try {
			Accounts acc = findInList(allAccounts, accountNumber);
			if (acc.isLocked()) {
				throw new AccountLockedException("Account " + acc.getAccountNumber() + " is locked. Contact Security Officer.");
			}
			withdrawChecked(acc, amount);
			writeAllAccounts(ACCOUNT_FILE, allAccounts);
			logTransaction(acc.getAccountNumber(), "WITHDRAWAL", amount);
			System.out.println("Rs. " + amount + " withdrawn from account " + acc.getAccountNumber());
		} catch (InvalidAccountException | InsufficientBalanceException | AccountLockedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Logs every cashier / transfer transaction to a text file in append mode
	private void logTransaction(String accountNumber, String type, double amount) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTION_LOG_FILE, true))) {
			bw.write(type + " | Account: " + accountNumber + " | Amount: " + amount);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Error logging transaction: " + e.getMessage());
		}
	}

	private void logFailedLogin(String accountNumber) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FAILED_LOGIN_FILE, true))) {
			bw.write("FAILED LOGIN | Account: " + accountNumber + " | " + new java.util.Date());
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Error logging failed login: " + e.getMessage());
		}
	}

	// ==================================================
	// ============= BRANCH MANAGER SECTION ============
	// ==================================================
	// Section 4 planned menu: Create Account, View All Accounts,
	// Search/Delete Account, Approve/Reject Loan
	// Section 9: Key methods --- createAccount(), viewAllAccounts(),
	// searchAccount(), deleteAccount(), approveLoan()
	private void branchManagerMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== BRANCH MANAGER MENU =====");
			System.out.println("1. Create New Account");
			System.out.println("2. View All Accounts");
			System.out.println("3. Search Account");
			System.out.println("4. Delete Account");
			System.out.println("5. Approve / Reject Loan");
			System.out.println("6. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				System.out.print("Enter customer name: ");
				String name = sc.next();
				String mobile = readValidMobile();
				System.out.print("Enter address: ");
				String address = sc.next();
				String password = readValidPassword();
				System.out.print("Enter account type (Savings/Current): ");
				String type = sc.next();
				System.out.print("Enter opening deposit: ");
				double openingBalance = readDouble();
				createAccount(name, mobile, address, password, type, openingBalance);
				break;
			case 2:
				viewAllAccounts();
				break;
			case 3:
				System.out.print("Enter account number to search: ");
				searchAccount(sc.next());
				break;
			case 4:
				System.out.print("Enter account number to delete: ");
				deleteAccount(sc.next());
				break;
			case 5:
				System.out.print("Enter loan ID: ");
				int loanId = readInt();
				System.out.print("Approve this loan? (yes/no): ");
				boolean approve = sc.next().equalsIgnoreCase("yes");
				approveLoan(loanId, approve);
				break;
			case 6:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}


public void createAccount(String name, String mobile, String address, String password, String accountType,
			double openingBalance) {
		ArrayList<Customer_> allCustomers = readAllCustomers(CUSTOMER_FILE);
		String newCustomerId = nextCustomerId(allCustomers);
		Customer_ customer = new Customer_(newCustomerId, name, mobile, address, password);
		allCustomers.add(customer);
		writeAllCustomers(CUSTOMER_FILE, allCustomers);

		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		// make sure every account number already on file is registered so
		// the new sequential number can never collide with an existing one
		for (Accounts a : allAccounts) {
			Accounts.registerExistingAccountNumber(a.getAccountNumber());
		}

		// Section 8: Inheritance --- decide which subclass to build based on
		// the type the Branch Manager typed in
		Accounts account;
		if (accountType.equalsIgnoreCase("Current")) {
			account = new CurrentAccount(customer, openingBalance);
		} else {
			if (!accountType.equalsIgnoreCase("Savings")) {
				System.out.println("Unrecognized account type '" + accountType + "' --- defaulting to Savings.");
			}
			account = new SavingsAccount(customer, openingBalance);
		}
		allAccounts.add(account);
		writeAllAccounts(ACCOUNT_FILE, allAccounts);
		System.out.println("Account created successfully.");
		System.out.println("Customer ID : " + newCustomerId);
		System.out.println("Account Number : " + account.getAccountNumber());
	}

	// View All Accounts --- show the list of every customer account in the bank
	public void viewAllAccounts() {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		System.out.println("===== ALL ACCOUNTS =====");
		if (allAccounts.isEmpty()) {
			System.out.println("No accounts found.");
			return;
		}
		for (Accounts acc : allAccounts) {
			System.out.println(acc);
		}
	}

	// Search Account --- look up and display one account by number
	public void searchAccount(String accountNumber) {
		try {
			Accounts acc = findAccountOrThrow(accountNumber);
			System.out.println(acc);
			System.out.println(acc.getCustomer());
		} catch (InvalidAccountException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Delete Account --- remove an account from accounts.src
	public void deleteAccount(String accountNumber) {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		Accounts toRemove = null;
		for (Accounts acc : allAccounts) {
			if (acc.matches(accountNumber)) {
				toRemove = acc;
				break;
			}
		}
		if (toRemove == null) {
			System.out.println("Error: account number not found: " + accountNumber);
			return;
		}
		allAccounts.remove(toRemove);
		writeAllAccounts(ACCOUNT_FILE, allAccounts);
		System.out.println("Account " + toRemove.getAccountNumber() + " deleted.");
	}

	// Approve / Reject Loan
	public void approveLoan(int loanId, boolean approve) {
		ArrayList<Loan> allLoans = readAllLoans(LOAN_FILE);
		for (Loan loan : allLoans) {
			if (loan.getLoanId() == loanId) {
				loan.setLoanStatus(approve ? "Approved" : "Rejected");
				writeAllLoans(LOAN_FILE, allLoans);
				if (approve) {
					try {
						Accounts acc = findAccountOrThrow(loan.getAccountNumber());
						ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
						for (Accounts a : allAccounts) {

						    if (a.matches(acc.getAccountNumber())) {

						        a.deposit(loan.getLoanAmount());

						        logTransaction(
						                a.getAccountNumber(),
						                "LOAN DISBURSEMENT",
						                loan.getLoanAmount()
						        );
						    }
						}
						writeAllAccounts(ACCOUNT_FILE, allAccounts);
					} catch (InvalidAccountException e) {
						System.out.println("Warning: loan approved but account not found for disbursement: "
								+ e.getMessage());
					}
				}
				System.out.println("Loan ID " + loanId + " is now " + loan.getLoanStatus() + ".");
				return;
			}
		}
		System.out.println("Loan ID not found: " + loanId);
	}

	// ==================================================
	// ============== BANK MANAGER SECTION ==============
	// ==================================================
	private void bankManagerMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== BANK MANAGER MENU =====");
			System.out.println("1. View All Accounts");
			System.out.println("2. View All Loans");
			System.out.println("3. Approve / Reject High-Value Loan");
			System.out.println("4. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				viewAllAccounts();
				break;
			case 2:
				viewAllLoans();
				break;
			case 3:
				System.out.print("Enter loan ID: ");
				int loanId = readInt();
				System.out.print("Approve this loan? (yes/no): ");
				boolean approve = sc.next().equalsIgnoreCase("yes");
				approveLoan(loanId, approve);
				break;
			case 4:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	public void viewAllLoans() {
		ArrayList<Loan> allLoans = readAllLoans(LOAN_FILE);
		System.out.println("===== ALL LOANS =====");
		if (allLoans.isEmpty()) {
			System.out.println("No loans found.");
			return;
		}
		for (Loan loan : allLoans) {
			System.out.println(loan);
		}
	}

	// ==================================================
	// ============== LOAN OFFICER SECTION ==============
	// ==================================================
	private void loanOfficerMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== LOAN OFFICER MENU =====");
			System.out.println("1. View All Loans");
			System.out.println("2. Approve / Reject Loan");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				viewAllLoans();
				break;
			case 2:
				System.out.print("Enter loan ID: ");
				int loanId = readInt();
				System.out.print("Approve this loan? (yes/no): ");
				boolean approve = sc.next().equalsIgnoreCase("yes");
				approveLoan(loanId, approve);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// ==================================================
	// ========= CUSTOMER SERVICE EXECUTIVE SECTION =====
	// ==================================================
	private void customerServiceMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== CUSTOMER SERVICE MENU =====");
			System.out.println("1. Search Account");
			System.out.println("2. Log a Complaint");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				System.out.print("Enter account number to search: ");
				searchAccount(sc.next());
				break;
			case 2:
				System.out.print("Enter account number: ");
				String accNum = sc.next();
				System.out.print("Enter complaint details: ");
				sc.nextLine();
				String details = sc.nextLine();
				logComplaint(accNum, details);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void logComplaint(String accountNumber, String details) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMPLAINT_FILE, true))) {
			bw.write("Account: " + accountNumber + " | " + details + " | " + new java.util.Date());
			bw.newLine();
			System.out.println("Complaint logged.");
		} catch (IOException e) {
			System.out.println("Error logging complaint: " + e.getMessage());
		}
	}

	// ==================================================
	// ============ RELATIONSHIP MANAGER SECTION ========
	// ==================================================
	private void relationshipManagerMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== RELATIONSHIP MANAGER MENU =====");
			System.out.println("1. View High-Value Customers");
			System.out.println("2. Search Account");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				viewHighValueCustomers();
				break;
			case 2:
				System.out.print("Enter account number to search: ");
				searchAccount(sc.next());
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void viewHighValueCustomers() {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		System.out.println("===== HIGH-VALUE CUSTOMERS (>= Rs. " + HIGH_VALUE_CUSTOMER_LIMIT + ") =====");
		boolean found = false;
		for (Accounts acc : allAccounts) {
			if (acc.getBalance() >= HIGH_VALUE_CUSTOMER_LIMIT) {
				System.out.println(acc);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No high-value customers found.");
		}
	}

	// ==================================================
	// ================ ACCOUNTANT SECTION ==============
	// ==================================================
	private void accountantMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== ACCOUNTANT MENU =====");
			System.out.println("1. View All Accounts");
			System.out.println("2. View Transaction Log");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				viewAllAccounts();
				break;
			case 2:
				printFile(TRANSACTION_LOG_FILE);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// ==================================================
	// ================= AUDITOR SECTION ================
	// ==================================================
	private void auditorMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== AUDITOR MENU (Read-Only) =====");
			System.out.println("1. View All Accounts");
			System.out.println("2. View All Loans");
			System.out.println("3. View Transaction Log");
			System.out.println("4. View Failed Login Attempts");
			System.out.println("5. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				viewAllAccounts();
				break;
			case 2:
				viewAllLoans();
				break;
			case 3:
				printFile(TRANSACTION_LOG_FILE);
				break;
			case 4:
				printFile(FAILED_LOGIN_FILE);
				break;
			case 5:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void printFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("(" + path + " has no entries yet.)");
			return;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			boolean any = false;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				any = true;
			}
			if (!any) {
				System.out.println("(" + path + " has no entries yet.)");
			}
		} catch (IOException e) {
			System.out.println("Error reading " + path + ": " + e.getMessage());
		}
	}

	// ==================================================
	// ============= SECURITY OFFICER SECTION ===========
	// ==================================================
	private void securityOfficerMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== SECURITY OFFICER MENU =====");
			System.out.println("1. Lock Account");
			System.out.println("2. Unlock Account");
			System.out.println("3. View Failed Login Attempts");
			System.out.println("4. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				System.out.print("Enter account number to lock: ");
				setAccountLock(sc.next(), true);
				break;
			case 2:
				System.out.print("Enter account number to unlock: ");
				setAccountLock(sc.next(), false);
				break;
			case 3:
				printFile(FAILED_LOGIN_FILE);
				break;
			case 4:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void setAccountLock(String accountNumber, boolean lock) {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		for (Accounts acc : allAccounts) {
			if (acc.matches(accountNumber)) {
				acc.setLocked(lock);
				writeAllAccounts(ACCOUNT_FILE, allAccounts);
				System.out.println("Account " + acc.getAccountNumber() + (lock ? " locked." : " unlocked."));
				return;
			}
		}
		System.out.println("Error: account number not found: " + accountNumber);
	}

	// ==================================================
	// ============ IT ADMINISTRATOR SECTION =============
	// ==================================================
	private void itAdministratorMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== IT ADMINISTRATOR MENU =====");
			System.out.println("1. Backup Data Files");
			System.out.println("2. View Console Log");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				backupDataFiles();
				break;
			case 2:
				printFile(CONSOLE_LOG_FILE);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void backupDataFiles() {
		File dir = new File(BACKUP_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String[] files = { CUSTOMER_FILE, ACCOUNT_FILE, LOAN_FILE };
		for (String fileName : files) {
			File source = new File(fileName);
			if (!source.exists()) {
				continue;
			}
			File dest = new File(dir, fileName + "." + System.currentTimeMillis() + ".bak");
			try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(dest)) {
				byte[] buffer = new byte[4096];
				int len;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				System.out.println("Error backing up " + fileName + ": " + e.getMessage());
			}
		}
		System.out.println("Backup complete. Files saved to " + BACKUP_DIR + "/");
	}

	// ==================================================
	// ================ HELP DESK SECTION ================
	// ==================================================
	private void helpDeskMenu() {
		boolean logout = false;
		while (!logout) {
			System.out.println();
			System.out.println("===== HELP DESK MENU =====");
			System.out.println("1. Raise a Support Ticket");
			System.out.println("2. View All Tickets");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice = readInt();
			switch (choice) {
			case 1:
				System.out.print("Enter account number: ");
				String accNum = sc.next();
				System.out.print("Enter issue details: ");
				sc.nextLine();
				String details = sc.nextLine();
				raiseTicket(accNum, details);
				break;
			case 2:
				printFile(TICKET_FILE);
				break;
			case 3:
				logout = true;
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void raiseTicket(String accountNumber, String details) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE, true))) {
			bw.write("Account: " + accountNumber + " | " + details + " | " + new java.util.Date());
			bw.newLine();
			System.out.println("Ticket raised.");
		} catch (IOException e) {
			System.out.println("Error raising ticket: " + e.getMessage());
		}
	}

	// ==================================================
	// ================== SHARED HELPERS ================
	// ==================================================

	// Every lookup-by-account-number in the whole app (customer login,
	// checkBalance, viewStatement, applyForLoan, cashier deposit/withdraw,
	// searchAccount, approveLoan's disbursement step...) goes through this
	// ONE method, which itself goes through acc.matches(). That's the core
	// of the account-number fix: one consistent rule everywhere, instead of
	// several slightly different .equals() comparisons scattered around the
	// file that could disagree with each other.
	private Accounts findAccountOrThrow(String accountNumber) throws InvalidAccountException {
		ArrayList<Accounts> allAccounts = readAllAccounts(ACCOUNT_FILE);
		return findInList(allAccounts, accountNumber);
	}
			

	private Accounts findInList(ArrayList<Accounts> allAccounts, String accountNumber) throws InvalidAccountException {
		for (Accounts acc : allAccounts) {
			if (acc.matches(accountNumber)) {
		}
				return acc;
			}
		
		throw new InvalidAccountException("Account number not found: " + accountNumber);
	}
	
	private void withdrawChecked(Accounts acc, double amount) throws InsufficientBalanceException {
		acc.withdraw(amount);
	}

	private String nextCustomerId(ArrayList<Customer_> allCustomers) {
		int max = 0;
		for (Customer_ c : allCustomers) {
			try {
				int n = Integer.parseInt(c.getCustomerId().replaceAll("[^0-9]", ""));
				if (n > max) {
					max = n;
				}
			} catch (NumberFormatException ignored) {
			}
		}
		return "C" + (max + 1);
	}

	private int nextLoanId(ArrayList<Loan> allLoans) {
		int max = 0;
		for (Loan l : allLoans) {
			if (l.getLoanId() > max) {
				max = l.getLoanId();
			}
		}
		return max + 1;
	}

	// ---------- Generic object-file read/write ----------

	@SuppressWarnings("unchecked")
	private ArrayList<Accounts> readAllAccounts(String fileName) {
		Object obj = readObjectFile(fileName);
		return obj != null ? (ArrayList<Accounts>) obj : new ArrayList<>();
	}

	private void writeAllAccounts(String fileName, ArrayList<Accounts> list) {
		writeObjectFile(fileName, list);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Customer_> readAllCustomers(String fileName) {
		Object obj = readObjectFile(fileName);
		return obj != null ? (ArrayList<Customer_>) obj : new ArrayList<>();
	}

	private void writeAllCustomers(String fileName, ArrayList<Customer_> list) {
		writeObjectFile(fileName, list);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Loan> readAllLoans(String fileName) {
		Object obj = readObjectFile(fileName);
		return obj != null ? (ArrayList<Loan>) obj : new ArrayList<>();
	}

	private void writeAllLoans(String fileName, ArrayList<Loan> list) {
		writeObjectFile(fileName, list);
	}

	private Object readObjectFile(String fileName) {
		File f = new File(fileName);
		if (!f.exists() || f.length() == 0) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error reading " + fileName + ": " + e.getMessage());
			return null;
		}
	}

	private void writeObjectFile(String fileName, Object data) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(data);
		} catch (IOException e) {
			System.out.println("Error writing " + fileName + ": " + e.getMessage());
		}
	}

	// ---------- Console input helpers ----------

	private int readInt() {
		while (!sc.hasNextInt()) {
			System.out.print("Please enter a valid number: ");
			sc.next();
		}
		return sc.nextInt();
	}

	private double readDouble() {
		while (!sc.hasNextDouble()) {
			System.out.print("Please enter a valid amount: ");
			sc.next();
		}
		return sc.nextDouble();
	}
}