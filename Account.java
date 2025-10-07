public abstract class Account {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount); // Only ChequeAccount allows this

    public abstract double calculateInterest(); // Only Savings and Investment accounts

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
}
