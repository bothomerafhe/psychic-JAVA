
public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005;
    
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance, "Savings Account");
    }
    
    @Override
    public BankingController.TransactionResult withdraw(double amount) {
        return new BankingController.TransactionResult(false, "Withdrawals are not allowed from Savings accounts");
    }
    
    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }
}