
public class InvestmentAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.05;
    private static final double MIN_BALANCE = 500.0;
    
    public InvestmentAccount(String accountNumber, double balance) {
        super(accountNumber, balance, "Investment Account");
    }
    
    @Override
    public BankingController.TransactionResult withdraw(double amount) {
        BankingController.TransactionResult validation = validateWithdrawal(amount);
        if (validation != null) return validation;
        
        if ((balance - amount) < MIN_BALANCE) {
            return new BankingController.TransactionResult(false,
                "Withdrawal failed! Investment account must maintain minimum balance of BWP " + MIN_BALANCE);
        }
        
        balance -= amount;
        return new BankingController.TransactionResult(true, "Withdrawal successful. New balance: BWP " + balance, balance);
    }
    
    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }
}