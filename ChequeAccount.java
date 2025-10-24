
public class ChequeAccount extends Account {
    private String employer;
    private String companyAddress;
    
    public ChequeAccount(String accountNumber, double balance) {
        super(accountNumber, balance, "Cheque Account");
    }
    
    @Override
    public BankingController.TransactionResult withdraw(double amount) {
        BankingController.TransactionResult validation = validateWithdrawal(amount);
        if (validation != null) return validation;
        
        balance -= amount;
        return new BankingController.TransactionResult(true, "Withdrawal successful. New balance: BWP " + balance, balance);
    }
    
    public double calculateInterest() {
        return 0;
    }
    
    public void setEmploymentInfo(String employer, String companyAddress) {
        this.employer = employer;
        this.companyAddress = companyAddress;
    }
    
    public String getEmployer() { return employer; }
    public String getCompanyAddress() { return companyAddress; }
}