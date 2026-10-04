package Banking;

import java.io.Serializable;

public class Loan implements Serializable {

	private static final long serialVersionUID = 1L;

	// =========================
	// DATA MEMBERS
	// =========================
	private int loanId;
	private String accountNumber;
	private double loanAmount;
	private String loanStatus; // Pending / Approved / Rejected
	private boolean verified; // Set by Loan Officer
	private String recommendation; // Set by Loan Officer

	// =========================
	// PARAMETERIZED CONSTRUCTOR
	// =========================
	public Loan(int loanId, String accountNumber, double loanAmount) {
		this.loanId = loanId;
		this.accountNumber = accountNumber;
		this.loanAmount = loanAmount;

		// Default values
		this.loanStatus = "Pending";
		this.verified = false;
		this.recommendation = "Not Reviewed";
	}

	// =========================
	// GETTERS
	// =========================

	public int getLoanId() {
		return loanId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	// Added for code like: loan.getAmount()
	public double getAmount() {
		return loanAmount;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getRecommendation() {
		return recommendation;
	}

	// =========================
	// SETTERS
	// =========================

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	// =========================
	// TO STRING
	// =========================

	@Override
	public String toString() {
		return "Loan#" + loanId + " | Acc:" + accountNumber + " | Rs." + loanAmount + " | Status:" + loanStatus
				+ " | Verified:" + verified + " | Recommendation:" + recommendation;
	}
}