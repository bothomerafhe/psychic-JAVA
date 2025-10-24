
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DashboardPanel {
    private JPanel panel;
    private BankingSystem bankingSystem;
    private JTable accountsTable;
    private DefaultTableModel tableModel;
    private JLabel totalBalanceLabel;
    
    public DashboardPanel(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 245, 249));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 245, 249));
        
        JLabel titleLabel = new JLabel("CUSTOMER DASHBOARD", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 82, 155));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JButton logoutButton = createStyledButton("Logout", new Color(220, 53, 69));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Customer info
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
        infoPanel.setBackground(new Color(248, 249, 250));
        
        infoPanel.add(createInfoLabel("Name: John Doe"));
        infoPanel.add(createInfoLabel("Customer ID: 123"));
        infoPanel.add(createInfoLabel("Address: 123 Main Street, Gaborone"));
        totalBalanceLabel = new JLabel("Total Balance: BWP " + String.format("%,.2f", bankingSystem.getTotalBalance()));
        infoPanel.add(totalBalanceLabel);
        
        // Accounts table
        String[] columnNames = {"Account Number", "Account Type", "Balance (BWP)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        accountsTable = new JTable(tableModel);
        accountsTable.setRowHeight(30);
        accountsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        accountsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        accountsTable.setSelectionBackground(new Color(220, 240, 255));
        
        JScrollPane tableScrollPane = new JScrollPane(accountsTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Your Accounts"));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 245, 249));
        
        JButton viewAccountBtn = createStyledButton("View Account", new Color(0, 123, 255));
        JButton depositBtn = createStyledButton("Make Deposit", new Color(40, 167, 69));
        JButton withdrawBtn = createStyledButton("Make Withdrawal", new Color(255, 193, 7));
        JButton openAccountBtn = createStyledButton("Open New Account", new Color(111, 66, 193));
        
        buttonPanel.add(viewAccountBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(openAccountBtn);
        
        // Layout organization
        JPanel northPanel = new JPanel(new BorderLayout(10, 10));
        northPanel.setBackground(new Color(240, 245, 249));
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(infoPanel, BorderLayout.CENTER);
        
        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        setupEventHandlers(viewAccountBtn, depositBtn, withdrawBtn, openAccountBtn, logoutButton);
        refreshBalances();
    }
    
    public void refreshBalances() {
        // Update total balance
        totalBalanceLabel.setText("Total Balance: BWP " + String.format("%,.2f", bankingSystem.getTotalBalance()));
        
        // Update accounts table
        tableModel.setRowCount(0); // Clear existing rows
        
        Object[] savingsRow = {"ACC001", "Savings Account", String.format("%,.2f", bankingSystem.getSavingsBalance())};
        Object[] investmentRow = {"ACC002", "Investment Account", String.format("%,.2f", bankingSystem.getInvestmentBalance())};
        Object[] chequeRow = {"ACC003", "Cheque Account", String.format("%,.2f", bankingSystem.getChequeBalance())};
        
        tableModel.addRow(savingsRow);
        tableModel.addRow(investmentRow);
        tableModel.addRow(chequeRow);
    }
    
    private void setupEventHandlers(JButton viewAccountBtn, JButton depositBtn, JButton withdrawBtn, 
                                   JButton openAccountBtn, JButton logoutButton) {
        viewAccountBtn.addActionListener(e -> {
            int selectedRow = accountsTable.getSelectedRow();
            if (selectedRow != -1) {
                bankingSystem.showAccountView();
            } else {
                bankingSystem.showAlert("Please select an account first");
            }
        });
        
        depositBtn.addActionListener(e -> {
            int selectedRow = accountsTable.getSelectedRow();
            if (selectedRow != -1) {
                String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
                bankingSystem.performTransaction(accountNumber, "Deposit");
            } else {
                bankingSystem.showAlert("Please select an account first");
            }
        });
        
        withdrawBtn.addActionListener(e -> {
            int selectedRow = accountsTable.getSelectedRow();
            if (selectedRow != -1) {
                String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
                bankingSystem.performTransaction(accountNumber, "Withdraw");
            } else {
                bankingSystem.showAlert("Please select an account first");
            }
        });
        
        openAccountBtn.addActionListener(e -> bankingSystem.showOpenAccountView());
        logoutButton.addActionListener(e -> bankingSystem.showLoginView());
    }
    
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
    
    public JPanel getPanel() {
        return panel;
    }
}