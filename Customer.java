
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String pin;
    private String firstName;
    private String lastName;
    private String address;
    private List<Account> accounts;
    
    public Customer(String customerId, String pin, String firstName, String lastName, String address) {
        this.customerId = customerId;
        this.pin = pin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accounts = new ArrayList<>();
    }
    
    public String getCustomerId() { return customerId; }
    public String getPin() { return pin; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public List<Account> getAccounts() { return accounts; }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}