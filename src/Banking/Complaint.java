package Banking;

import java.io.Serializable;

// Used by Customer Service Executive: logComplaint(), resolveComplaint()
public class Complaint implements Serializable {
	private static final long serialVersionUID = 1L;

	private int complaintId;
	private String accountNumber;
	private String issue;
	private String status; // Open / Resolved

	public Complaint(int complaintId, String accountNumber, String issue) {
		this.complaintId = complaintId;
		this.accountNumber = accountNumber;
		this.issue = issue;
		this.status = "Open";
	}

	public int getComplaintId() {
		return complaintId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getIssue() {
		return issue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Complaint#" + complaintId + " | Acc:" + accountNumber + " | Issue: " + issue + " | Status:" + status;
	}
}