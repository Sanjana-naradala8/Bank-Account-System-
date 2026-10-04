package Banking;

public interface Transaction {

    void deposit(double amount);

    void withdraw(double amount)
            throws InsufficientBalanceException;
}