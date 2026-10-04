package Banking;

import java.io.Serializable;

// Used by Help Desk / Support Executive: logTicket(), escalateIssue()
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	private int ticketId;
	private String accountNumber;
	private String description;
	private String status; // Open / Escalated / Closed

	public Ticket(int ticketId, String accountNumber, String description) {
		this.ticketId = ticketId;
		this.accountNumber = accountNumber;
		this.description = description;
		this.status = "Open";
	}

	public int getTicketId() {
		return ticketId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Ticket#" + ticketId + " | Acc:" + accountNumber + " | " + description + " | Status:" + status;
	}
}