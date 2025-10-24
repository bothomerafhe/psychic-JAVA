
import java.util.List;

public class CustomerController {
    
    public static String getCustomerInfo(String customerId) {
        Customer customer = BankingController.getCustomer(customerId);
        if (customer == null) return "Customer not found";
        
        return "Name: " + customer.getFullName() + "\n" +
               "Customer ID: " + customer.getCustomerId() + "\n" +
               "Address: " + customer.getAddress() + "\n" +
               "Total Balance: BWP " + String.format("%,.2f", BankingController.getTotalBalance(customerId)) + "\n" +
               "Number of Accounts: " + customer.getAccounts().size();
    }
}