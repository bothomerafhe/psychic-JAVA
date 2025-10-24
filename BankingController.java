
import java.util.*;

public class BankingController {
    private static Map<String, Customer> customers = new HashMap<>();
    private static Map<String, Account> accounts = new HashMap<>();
    private static int accountCounter = 3;
    
    static {
        initializeDemoData();
    }
    
    public static boolean login(String customerId, String pin) {
        Customer customer = customers.get(customerId);
        return customer != null && customer.getPin().equals(pin);
    }
    
    public static Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    public static String openAccount(String customerId, String accountType, double initialDeposit, 
                                   String employer, String companyAddress) {
        try {
            Customer customer = customers.get(customerId);
            if (customer == null) return "ERROR: Customer not found";
            
            String validationError = validateAccountCreation(accountType, initialDeposit, employer, companyAddress);
            if (validationError != null) return validationError;
            
            accountCounter++;
            String accountNumber = "ACC" + String.format("%03d", accountCounter);
            Account account = createAccount(accountNumber, accountType, initialDeposit, employer, companyAddress);
            
            accounts.put(accountNumber, account);
            customer.addAccount(account);
            
            return "SUCCESS:" + accountNumber;
            
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    public static TransactionResult deposit(String accountNumber, double amount) {
        try {
            Account account = accounts.get(accountNumber);
            if (account == null) return new TransactionResult(false, "Account not found");
            if (amount <= 0) return new TransactionResult(false, "Please enter a positive amount");
            
            account.deposit(amount);
            return new TransactionResult(true, "Deposit successful. New balance: BWP " + account.getBalance());
            
        } catch (Exception e) {
            return new TransactionResult(false, "Error processing deposit: " + e.getMessage());
        }
    }
    
    public static TransactionResult withdraw(String accountNumber, double amount) {
        try {
            Account account = accounts.get(accountNumber);
            if (account == null) return new TransactionResult(false, "Account not found");
            return account.withdraw(amount);
            
        } catch (Exception e) {
            return new TransactionResult(false, "Error processing withdrawal: " + e.getMessage());
        }
    }
    
    public static List<Account> getCustomerAccounts(String customerId) {
        Customer customer = customers.get(customerId);
        return customer != null ? customer.getAccounts() : new ArrayList<>();
    }
    
    public static double getTotalBalance(String customerId) {
        List<Account> customerAccounts = getCustomerAccounts(customerId);
        double total = 0;
        for (Account account : customerAccounts) total += account.getBalance();
        return total;
    }
    
    public static Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    private static String validateAccountCreation(String accountType, double initialDeposit, 
                                                String employer, String companyAddress) {
        if (initialDeposit <= 0) return "ERROR: Please enter a positive deposit amount";
        
        switch (accountType.toUpperCase()) {
            case "INVESTMENT ACCOUNT":
                if (initialDeposit < 500) return "ERROR: Investment account requires minimum deposit of BWP 500";
                break;
            case "CHEQUE ACCOUNT":
                if (employer == null || employer.trim().isEmpty()) return "ERROR: Employer name is required";
                if (companyAddress == null || companyAddress.trim().isEmpty()) return "ERROR: Company address is required";
                if (employer.trim().length() < 2) return "ERROR: Please enter a valid employer name";
                break;
            case "SAVINGS ACCOUNT": break;
            default: return "ERROR: Invalid account type";
        }
        return null;
    }
    
    private static Account createAccount(String accountNumber, String accountType, double initialDeposit,
                                       String employer, String companyAddress) {
        switch (accountType.toUpperCase()) {
            case "SAVINGS ACCOUNT": return new SavingsAccount(accountNumber, initialDeposit);
            case "INVESTMENT ACCOUNT": return new InvestmentAccount(accountNumber, initialDeposit);
            case "CHEQUE ACCOUNT":
                ChequeAccount chequeAccount = new ChequeAccount(accountNumber, initialDeposit);
                chequeAccount.setEmploymentInfo(employer, companyAddress);
                return chequeAccount;
            default: throw new IllegalArgumentException("Unknown account type: " + accountType);
        }
    }
    
    private static void initializeDemoData() {
        Customer demoCustomer = new Customer("123", "1234", "John", "Doe", "123 Main St, Gaborone");
        customers.put("123", demoCustomer);
        
        SavingsAccount savings = new SavingsAccount("ACC001", 1500.0);
        InvestmentAccount investment = new InvestmentAccount("ACC002", 5000.0);
        ChequeAccount cheque = new ChequeAccount("ACC003", 2500.0);
        cheque.setEmploymentInfo("Botswana Government", "Government Enclave, Gaborone");
        
        accounts.put("ACC001", savings);
        accounts.put("ACC002", investment);
        accounts.put("ACC003", cheque);
        
        demoCustomer.addAccount(savings);
        demoCustomer.addAccount(investment);
        demoCustomer.addAccount(cheque);
    }
    
    public static class TransactionResult {
        private boolean success;
        private String message;
        private double newBalance;
        
        public TransactionResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public TransactionResult(boolean success, String message, double newBalance) {
            this.success = success;
            this.message = message;
            this.newBalance = newBalance;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public double getNewBalance() { return newBalance; }
    }
}