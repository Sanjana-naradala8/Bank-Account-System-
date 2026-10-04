package Banking;

public class AccountLockedException extends Exception {

	private static final long serialVersionUID = 1L;

	// Constructor
	public AccountLockedException(String message) {
		super(message);
	}
}