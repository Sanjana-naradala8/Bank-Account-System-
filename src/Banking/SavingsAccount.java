package Banking;

public class SavingsAccount extends Accounts {
	private static final long serialVersionUID = 1L;

	public static final double MIN_BALANCE = 500;
	public static final double INTEREST_RATE = 0.04; // 4% per year, simple interest

	public SavingsAccount(Customer_ customer, double openingBalance) {
		super(customer, openingBalance);
	}

	public void withdraw(double amount) throws InsufficientBalanceException {
		if (balance - amount < MIN_BALANCE) {
			throw new InsufficientBalanceException("Withdrawal denied for Savings account " + accountNumber
					+ " --- balance cannot go below the minimum Rs. " + MIN_BALANCE + " (Available to withdraw: Rs. "
					+ (balance - MIN_BALANCE) + ")");
		}
		balance -= amount;
		transactions.add("WITHDRAWAL: Rs. " + amount);
		pushRecent("WITHDRAWAL");
	}

	@Override
	public double calculateInterest() {
		return balance * INTEREST_RATE;
	}

	@Override
	public String getAccountType() {
		return "Savings";
	}
}