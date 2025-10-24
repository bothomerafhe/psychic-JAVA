
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel {
    private JPanel panel;
    private BankingSystem bankingSystem;
    private JTextField customerIdField;
    private JPasswordField pinField;
    
    public LoginPanel(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(240, 245, 249));
        
        // Title
        JLabel titleLabel = new JLabel("BANKING SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 82, 155));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Login panel
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        JLabel loginTitle = new JLabel("Login to Your Account", JLabel.CENTER);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 18));
        loginTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setBackground(Color.WHITE);
        
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setFont(new Font("Arial", Font.BOLD, 12));
        customerIdField = new JTextField();
        customerIdField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pinField = new JPasswordField();
        pinField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(46, 139, 87));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(customerIdLabel);
        formPanel.add(customerIdField);
        formPanel.add(pinLabel);
        formPanel.add(pinField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        
        // Demo credentials label
        JLabel demoLabel = new JLabel("Demo Credentials: Customer ID = '123', PIN = '1234'", JLabel.CENTER);
        demoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        demoLabel.setForeground(Color.GRAY);
        demoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        loginPanel.add(loginTitle, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(demoLabel, BorderLayout.SOUTH);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(loginPanel, BorderLayout.CENTER);
        
        setupEventHandlers(loginButton);
    }
    
    private void setupEventHandlers(JButton loginButton) {
        loginButton.addActionListener(e -> {
            String customerId = customerIdField.getText();
            String pin = new String(pinField.getPassword());
            
            if (customerId.isEmpty() || pin.isEmpty()) {
                bankingSystem.showAlert("Please enter both Customer ID and PIN");
                return;
            }
            
            if (customerId.equals("123") && pin.equals("1234")) {
                bankingSystem.showDashboardView();
            } else {
                bankingSystem.showAlert("Invalid Customer ID or PIN");
            }
        });
        
        // Enter key support
        Action loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        };
        customerIdField.addActionListener(loginAction);
        pinField.addActionListener(loginAction);
    }
    
    public JPanel getPanel() {
        return panel;
    }
}