/*
 * package Banking;
 * 
 * // Section 9.3: CurrentAccount --- built from (extends) the Accounts base //
 * class (Inheritance). Can go below zero balance up to an overdraft limit, //
 * and earns no interest. public class CurrentAccount extends Accounts { private
 * static final long serialVersionUID = 1L;
 * 
 * public static final double OVERDRAFT_LIMIT = 10000;
 * 
 * public CurrentAccount(Customer_ customer, double openingBalance) {
 * super(customer, openingBalance); }
 * 
 * // Section 8: Method Overriding --- a Current account can go negative, // but
 * only up to the overdraft limit
 * 
 * @Override public void withdraw(double amount) throws
 * InsufficientBalanceException { if (balance - amount < -OVERDRAFT_LIMIT) {
 * throw new
 * InsufficientBalanceException("Withdrawal denied for Current account " +
 * accountNumber + " --- overdraft limit of Rs. " + OVERDRAFT_LIMIT +
 * " exceeded" + " (Available to withdraw: Rs. " + (balance + OVERDRAFT_LIMIT) +
 * ")"); } balance -= amount; transactions.add("WITHDRAWAL: Rs. " + amount);
 * pushRecent("WITHDRAWAL"); }
 * 
 * // Section 8: Method Overriding --- Current account earns no interest
 * 
 * @Override public double calculateInterest() { return 0; }
 * 
 * @Override public String getAccountType() { return "Current"; } }
 */

package Banking;

public class CurrentAccount extends Accounts {
	private static final long serialVersionUID = 1L;

	public static final double OVERDRAFT_LIMIT = 10000;

	public CurrentAccount(Customer_ customer, double openingBalance) {
		super(customer, openingBalance);
	}

	@Override
	public void withdraw(double amount) throws InsufficientBalanceException {
		if (balance - amount < -OVERDRAFT_LIMIT) {
			throw new InsufficientBalanceException(
					"Withdrawal denied for Current account " + "--- overdraft limit of Rs. " + OVERDRAFT_LIMIT
							+ " exceeded " + "(Available limit: Rs. " + (balance + OVERDRAFT_LIMIT) + ")");
		}
		balance -= amount;
		transactions.add("WITHDRAWAL: Rs. " + amount);
		pushRecent("WITHDRAWAL");
	}

	// Section 8: Method Overriding --- Current account earns no interest
	@Override
	public double calculateInterest() {
		return 0;
	}

	@Override
	public String getAccountType() {
		return "Current";
	}
}