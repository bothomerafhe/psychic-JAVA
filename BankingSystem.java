
import javax.swing.*;
import java.awt.*;

public class BankingSystem {
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private AccountPanel accountPanel;
    private OpenAccountPanel openAccountPanel;
    
    // Account balances storage
    private double savingsBalance = 1500.0;
    private double investmentBalance = 5000.0;
    private double chequeBalance = 2500.0;
    
    public BankingSystem() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        mainFrame = new JFrame("Banking System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 550);
        mainFrame.setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Initialize all panels
        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);
        accountPanel = new AccountPanel(this);
        openAccountPanel = new OpenAccountPanel(this);
        
        // Add panels to card layout
        mainPanel.add(loginPanel.getPanel(), "LOGIN");
        mainPanel.add(dashboardPanel.getPanel(), "DASHBOARD");
        mainPanel.add(accountPanel.getPanel(), "ACCOUNT");
        mainPanel.add(openAccountPanel.getPanel(), "OPEN_ACCOUNT");
        
        mainFrame.add(mainPanel);
        showLoginView();
        mainFrame.setVisible(true);
    }
    
    public void showLoginView() {
        cardLayout.show(mainPanel, "LOGIN");
        mainFrame.setTitle("Banking System - Login");
    }
    
    public void showDashboardView() {
        cardLayout.show(mainPanel, "DASHBOARD");
        mainFrame.setTitle("Banking System - Dashboard");
        dashboardPanel.refreshBalances();
    }
    
    public void showAccountView() {
        cardLayout.show(mainPanel, "ACCOUNT");
        mainFrame.setTitle("Banking System - Account Details");
        accountPanel.refreshAccountInfo();
    }
    
    public void showOpenAccountView() {
        cardLayout.show(mainPanel, "OPEN_ACCOUNT");
        mainFrame.setTitle("Banking System - Open New Account");
    }
    
    public void showAlert(String message) {
        JOptionPane.showMessageDialog(mainFrame, message);
    }
    
    // Getters for balances
    public double getSavingsBalance() { return savingsBalance; }
    public double getInvestmentBalance() { return investmentBalance; }
    public double getChequeBalance() { return chequeBalance; }
    public double getTotalBalance() { return savingsBalance + investmentBalance + chequeBalance; }
    
    // Method to validate and process withdrawals
    public boolean processWithdrawal(String accountNumber, double amount) {
        switch (accountNumber) {
            case "ACC001": // Savings Account
                if (amount > savingsBalance) {
                    showAlert("Insufficient funds! Available balance: BWP " + savingsBalance);
                    return false;
                }
                if (amount <= 0) {
                    showAlert("Please enter a positive amount");
                    return false;
                }
                // Savings account doesn't allow withdrawals (as per requirements)
                showAlert("Withdrawals are not allowed from Savings accounts");
                return false;
                
            case "ACC002": // Investment Account
                if (amount > investmentBalance) {
                    showAlert("Insufficient funds! Available balance: BWP " + investmentBalance);
                    return false;
                }
                if (amount <= 0) {
                    showAlert("Please enter a positive amount");
                    return false;
                }
                // Check minimum balance requirement for investment account (BWP 500)
                if ((investmentBalance - amount) < 500) {
                    showAlert("Withdrawal failed! Investment account must maintain minimum balance of BWP 500");
                    return false;
                }
                investmentBalance -= amount;
                showAlert("Successfully withdrew BWP " + amount + " from Investment Account\nNew Balance: BWP " + investmentBalance);
                return true;
                
            case "ACC003": // Cheque Account
                if (amount > chequeBalance) {
                    showAlert("Insufficient funds! Available balance: BWP " + chequeBalance);
                    return false;
                }
                if (amount <= 0) {
                    showAlert("Please enter a positive amount");
                    return false;
                }
                chequeBalance -= amount;
                showAlert("Successfully withdrew BWP " + amount + " from Cheque Account\nNew Balance: BWP " + chequeBalance);
                return true;
                
            default:
                showAlert("Invalid account selected");
                return false;
        }
    }
    
    // Method to process deposits
    public boolean processDeposit(String accountNumber, double amount) {
        if (amount <= 0) {
            showAlert("Please enter a positive amount");
            return false;
        }
        
        switch (accountNumber) {
            case "ACC001": // Savings Account
                savingsBalance += amount;
                showAlert("Successfully deposited BWP " + amount + " to Savings Account\nNew Balance: BWP " + savingsBalance);
                return true;
                
            case "ACC002": // Investment Account
                investmentBalance += amount;
                showAlert("Successfully deposited BWP " + amount + " to Investment Account\nNew Balance: BWP " + investmentBalance);
                return true;
                
            case "ACC003": // Cheque Account
                chequeBalance += amount;
                showAlert("Successfully deposited BWP " + amount + " to Cheque Account\nNew Balance: BWP " + chequeBalance);
                return true;
                
            default:
                showAlert("Invalid account selected");
                return false;
        }
    }
    
    public void performTransaction(String accountNumber, String type) {
        String amountStr = JOptionPane.showInputDialog(mainFrame, 
            "Enter amount to " + type.toLowerCase() + " to " + accountNumber + ":", "100");
        
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                
                boolean success = false;
                if (type.equals("Deposit")) {
                    success = processDeposit(accountNumber, amount);
                } else if (type.equals("Withdraw")) {
                    success = processWithdrawal(accountNumber, amount);
                }
                
                if (success) {
                    // Refresh the panels to show updated balances
                    showDashboardView();
                }
                
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid number");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingSystem());
    }
}