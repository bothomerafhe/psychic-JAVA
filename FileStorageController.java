
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class FileStorageController {
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    
    public static void saveCustomers(Map<String, Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers.values()) {
                writer.println(customer.getCustomerId() + "|" +
                              customer.getPin() + "|" +
                              customer.getFirstName() + "|" +
                              customer.getLastName() + "|" +
                              customer.getAddress());
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
        public static Map<String, Customer> loadCustomers() {
        Map<String, Customer> customers = new HashMap<>();
        File file = new File(CUSTOMERS_FILE);
        
        if (!file.exists()) {
            return customers;
                    }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Customer customer = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    customers.put(parts[0], customer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }
    
   
    public static void saveAccounts(Map<String, Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts.values()) {
                String line = account.getAccountNumber() + "|" +
                             account.getAccountType() + "|" +
                             account.getBalance();
                
                
                if (account instanceof ChequeAccount) {
                    ChequeAccount cheque = (ChequeAccount) account;
                    line += "|" + cheque.getEmployer() + "|" + cheque.getCompanyAddress();
                } else {
                    line += "||"; 
                }
                
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    
    
    public static Map<String, Account> loadAccounts() {
        Map<String, Account> accounts = new HashMap<>();
        File file = new File(ACCOUNTS_FILE);
        
        if (!file.exists()) {
            return accounts;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String accountNumber = parts[0];
                    String accountType = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    
                    Account account = createAccountFromData(accountNumber, accountType, balance, parts);
                    if (account != null) {
                        accounts.put(accountNumber, account);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }
    
    
    public static void saveTransaction(String accountNumber, String type, double amount, double balanceAfter) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            String timestamp = dateFormat.format(new Date());
            writer.println(accountNumber + "|" + timestamp + "|" + type + "|" + amount + "|" + balanceAfter);
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
    
    
    public static List<Transaction> loadTransactions(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5 && parts[0].equals(accountNumber)) {
                    try {
                        Date date = dateFormat.parse(parts[1]);
                        String type = parts[2];
                        double amount = Double.parseDouble(parts[3]);
                        double balanceAfter = Double.parseDouble(parts[4]);
                        
                        Transaction transaction = new Transaction(accountNumber, date, type, amount, balanceAfter);
                        transactions.add(transaction);
                    } catch (Exception e) {
                        System.err.println("Error parsing transaction: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        
       
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        return transactions;
    }
    
   
    private static Account createAccountFromData(String accountNumber, String accountType, 
                                               double balance, String[] parts) {
        switch (accountType) {
            case "Savings Account":
                return new SavingsAccount(accountNumber, balance);
            case "Investment Account":
                return new InvestmentAccount(accountNumber, balance);
            case "Cheque Account":
                ChequeAccount cheque = new ChequeAccount(accountNumber, balance);
                if (parts.length >= 5 && !parts[3].isEmpty()) {
                    cheque.setEmploymentInfo(parts[3], parts[4]);
                }
                return cheque;
            default:
                return null;
        }
    }
    
    
    public static void initializeDemoDataIfNeeded() {
        File customersFile = new File(CUSTOMERS_FILE);
        File accountsFile = new File(ACCOUNTS_FILE);
        
        if (!customersFile.exists() || !accountsFile.exists()) {
            System.out.println("Initializing demo data...");
            BankingController.initializeDemoData();
            saveAllData();
        }
    }
    
   
    public static void saveAllData() {
        
        Map<String, Customer> customers = BankingController.getCustomersMap();
        Map<String, Account> accounts = BankingController.getAccountsMap();
        
        if (customers != null) saveCustomers(customers);
        if (accounts != null) saveAccounts(accounts);
    }
    
    // Load all data from files
    public static void loadAllData() {
        Map<String, Customer> customers = loadCustomers();
        Map<String, Account> accounts = loadAccounts();
        
        BankingController.setCustomersMap(customers);
        BankingController.setAccountsMap(accounts);
    }
}