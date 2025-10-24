
import java.util.List;

public class AccountController {
    
    public static String getAccountDetails(String accountNumber) {
        Account account = BankingController.getAccount(accountNumber);
        if (account == null) return "Account not found";
        
        StringBuilder details = new StringBuilder();
        details.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        details.append("Account Type: ").append(account.getAccountType()).append("\n");
        details.append("Current Balance: BWP ").append(String.format("%,.2f", account.getBalance())).append("\n");
        
        if (account instanceof SavingsAccount) {
            details.append("Interest Rate: 0.05% monthly\n");
            details.append("Withdrawals: Not Allowed\n");
        } else if (account instanceof InvestmentAccount) {
            details.append("Interest Rate: 5% monthly\n");
            details.append("Minimum Balance: BWP 500.00\n");
        } else if (account instanceof ChequeAccount) {
            ChequeAccount chequeAccount = (ChequeAccount) account;
            details.append("Interest Rate: No interest\n");
            details.append("Employer: ").append(chequeAccount.getEmployer()).append("\n");
            details.append("Company Address: ").append(chequeAccount.getCompanyAddress()).append("\n");
        }
        
        return details.toString();
    }
    
    public static boolean canWithdrawFromAccount(String accountNumber) {
        Account account = BankingController.getAccount(accountNumber);
        return account != null && !(account instanceof SavingsAccount);
    }
    
    public static String getAccountType(String accountNumber) {
        Account account = BankingController.getAccount(accountNumber);
        return account != null ? account.getAccountType() : "Unknown";
    }
    
    public static double getAccountBalance(String accountNumber) {
        Account account = BankingController.getAccount(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }
}