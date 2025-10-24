
public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String accountType;
    
    public Account(String accountNumber, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }
    
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }
    
    public abstract BankingController.TransactionResult withdraw(double amount);
    
    protected BankingController.TransactionResult validateWithdrawal(double amount) {
        if (amount <= 0) {
            return new BankingController.TransactionResult(false, "Please enter a positive amount");
        }
        if (amount > balance) {
            return new BankingController.TransactionResult(false, "Insufficient funds! Available balance: BWP " + balance);
        }
        return null;
    }
}