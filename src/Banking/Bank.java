package Banking;

import java.util.ArrayList;

public class Bank {

	// Bank Name
	public static final String BANK_NAME = "State Bank of India";

	// Display Bank Name
	public static void displayBankName() {

		System.out.println("======================================");
		System.out.println(" State Bank of India");
		System.out.println("======================================");
	}

	// Calculate Total Deposits
	public static double totalDeposits(ArrayList<Accounts> accounts) {

		double total = 0.0;

		if (accounts == null) {
			return 0.0;
		}

		for (Accounts account : accounts) {

			if (account != null) {
				total = total + account.getBalance();
			}
		}

		return total;
	}

	// Calculate Total Loan Amount
	public static double totalLoanAmount(ArrayList<Loan> loans, String statusFilter) {

		double total = 0.0;

		if (loans == null) {
			return 0.0;
		}

		for (Loan loan : loans) {

			if (loan == null) {
				continue;
			}

			if (statusFilter == null) {

				total = total + loan.getLoanAmount();

			} else if (loan.getLoanStatus() != null && loan.getLoanStatus().equalsIgnoreCase(statusFilter)) {

				total = total + loan.getLoanAmount();
			}
		}

		return total;
	}

	// Display Total Deposits
	public static void displayTotalDeposits(ArrayList<Accounts> accounts) {

		System.out.println("Total Bank Deposits : Rs. " + totalDeposits(accounts));
	}

	// Display Total Loans
	public static void displayTotalLoans(ArrayList<Loan> loans) {

		System.out.println("Total Loan Amount : Rs. " + totalLoanAmount(loans, null));
	}

	// Display Complete Bank Summary
	public static void displayBankSummary(ArrayList<Accounts> accounts, ArrayList<Loan> loans) {

		System.out.println();
		System.out.println("======================================");
		System.out.println("        STATE BANK OF INDIA");
		System.out.println("======================================");

		System.out.println("Bank Name         : State Bank of India");

		System.out.println("Total Deposits    : Rs. " + totalDeposits(accounts));

		System.out.println("Total Loan Amount : Rs. " + totalLoanAmount(loans, null));

		System.out.println("======================================");
	}
}