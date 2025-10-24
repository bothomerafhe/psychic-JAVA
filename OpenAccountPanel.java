
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenAccountPanel {
    private JPanel panel;
    private BankingSystem bankingSystem;
    private JComboBox<String> typeComboBox;
    private JTextField depositField;
    private JTextField employerField;
    private JTextField companyAddressField;
    private JPanel employmentPanel;
    
    public OpenAccountPanel(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(240, 245, 249));
        
        // Header
        JLabel titleLabel = new JLabel("OPEN NEW ACCOUNT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 82, 155));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Account type selection
        JLabel typeLabel = new JLabel("Account Type:*");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        String[] accountTypes = {"Savings Account", "Investment Account", "Cheque Account"};
        typeComboBox = new JComboBox<>(accountTypes);
        
        // Initial deposit
        JLabel depositLabel = new JLabel("Initial Deposit (BWP):*");
        depositLabel.setFont(new Font("Arial", Font.BOLD, 12));
        depositField = new JTextField();
        
        // Employment details panel (initially hidden)
        employmentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        employmentPanel.setBackground(Color.WHITE);
        employmentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 193, 7)),
            "Employment Information (Required for Cheque Accounts)"
        ));
        
        JLabel employerLabel = new JLabel("Employer Name:*");
        employerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        employerField = new JTextField();
        
        JLabel companyAddressLabel = new JLabel("Company Address:*");
        companyAddressLabel.setFont(new Font("Arial", Font.BOLD, 12));
        companyAddressField = new JTextField();
        
        employmentPanel.add(employerLabel);
        employmentPanel.add(employerField);
        employmentPanel.add(companyAddressLabel);
        employmentPanel.add(companyAddressField);
        employmentPanel.setVisible(false); // Initially hidden
        
        // Requirements information
        JLabel requirementsLabel = new JLabel("<html><b>Account Requirements:</b><br>"
            + "• <b>Savings:</b> No withdrawals allowed, 0.05% interest<br>"
            + "• <b>Investment:</b> Min BWP 500, 5% interest, withdrawals allowed<br>"
            + "• <b>Cheque:</b> For salary deposits, employment info required</html>");
        requirementsLabel.setForeground(Color.DARK_GRAY);
        requirementsLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton openButton = createStyledButton("Open Account", new Color(40, 167, 69));
        JButton cancelButton = createStyledButton("Cancel", new Color(108, 117, 125));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(openButton);
        
        // Add components to form panel
        formPanel.add(typeLabel);
        formPanel.add(typeComboBox);
        formPanel.add(depositLabel);
        formPanel.add(depositField);
        formPanel.add(new JLabel()); // Empty cell
        formPanel.add(new JLabel()); // Empty cell
        formPanel.add(requirementsLabel);
        formPanel.add(new JLabel()); // Empty cell
        
        // Main layout
        JPanel mainFormPanel = new JPanel(new BorderLayout(10, 10));
        mainFormPanel.setBackground(Color.WHITE);
        mainFormPanel.add(formPanel, BorderLayout.NORTH);
        mainFormPanel.add(employmentPanel, BorderLayout.CENTER);
        mainFormPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(mainFormPanel, BorderLayout.CENTER);
        
        setupEventHandlers(openButton, cancelButton);
    }
    
    private void setupEventHandlers(JButton openButton, JButton cancelButton) {
        // Account type change listener to show/hide employment details
        typeComboBox.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            if (selectedType.equals("Cheque Account")) {
                employmentPanel.setVisible(true);
                panel.revalidate();
                panel.repaint();
            } else {
                employmentPanel.setVisible(false);
                panel.revalidate();
                panel.repaint();
            }
        });
        
        openButton.addActionListener(e -> {
            String accountType = (String) typeComboBox.getSelectedItem();
            String depositText = depositField.getText().trim();
            String employer = employerField.getText().trim();
            String companyAddress = companyAddressField.getText().trim();
            
            // Validate deposit amount
            if (depositText.isEmpty()) {
                bankingSystem.showAlert("Please enter initial deposit amount");
                return;
            }
            
            try {
                double deposit = Double.parseDouble(depositText);
                if (deposit <= 0) {
                    bankingSystem.showAlert("Please enter a positive amount");
                    return;
                }
                
                // Validate account-specific requirements
                if (accountType.equals("Investment Account")) {
                    if (deposit < 500) {
                        bankingSystem.showAlert("Investment account requires minimum deposit of BWP 500");
                        return;
                    }
                }
                
                // Validate employment details for cheque account
                if (accountType.equals("Cheque Account")) {
                    if (employer.isEmpty() || companyAddress.isEmpty()) {
                        bankingSystem.showAlert("Please provide both employer name and company address for cheque account");
                        return;
                    }
                    if (employer.length() < 2) {
                        bankingSystem.showAlert("Please enter a valid employer name");
                        return;
                    }
                    if (companyAddress.length() < 5) {
                        bankingSystem.showAlert("Please enter a valid company address");
                        return;
                    }
                }
                
                // Success message with details
                String successMessage = "Successfully opened new " + accountType + 
                    " with BWP " + String.format("%,.2f", deposit);
                
                if (accountType.equals("Cheque Account")) {
                    successMessage += "\nEmployer: " + employer + 
                                    "\nCompany Address: " + companyAddress;
                }
                
                if (accountType.equals("Investment Account")) {
                    successMessage += "\nNote: 5% monthly interest will be applied";
                } else if (accountType.equals("Savings Account")) {
                    successMessage += "\nNote: 0.05% monthly interest will be applied";
                }
                
                bankingSystem.showAlert(successMessage);
                bankingSystem.showDashboardView();
                
                // Reset form
                resetForm();
                
            } catch (NumberFormatException ex) {
                bankingSystem.showAlert("Please enter a valid number for deposit");
            }
        });
        
        cancelButton.addActionListener(e -> {
            resetForm();
            bankingSystem.showDashboardView();
        });
    }
    
    private void resetForm() {
        depositField.setText("");
        employerField.setText("");
        companyAddressField.setText("");
        typeComboBox.setSelectedIndex(0);
        employmentPanel.setVisible(false);
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
    
    public JPanel getPanel() {
        return panel;
    }
}