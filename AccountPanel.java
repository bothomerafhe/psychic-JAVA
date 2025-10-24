
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccountPanel {
    private JPanel panel;
    private BankingSystem bankingSystem;
    private JLabel balanceLabel;
    
    public AccountPanel(BankingSystem bankingSystem) {
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
        
        JLabel titleLabel = new JLabel("ACCOUNT DETAILS - SAVINGS ACCOUNT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 82, 155));
        
        JButton backButton = createStyledButton("Back to Dashboard", new Color(108, 117, 125));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Account info
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 8, 8));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        infoPanel.setBackground(new Color(227, 242, 253));
        
        infoPanel.add(createInfoLabel("Account Number: ACC001"));
        infoPanel.add(createInfoLabel("Account Type: Savings Account"));
        balanceLabel = new JLabel("Current Balance: BWP " + String.format("%,.2f", bankingSystem.getSavingsBalance()));
        infoPanel.add(balanceLabel);
        infoPanel.add(createInfoLabel("Interest Rate: 0.05% monthly"));
        infoPanel.add(createInfoLabel("Withdrawals: Not Allowed"));
        
        // Quick actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(new Color(240, 245, 249));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        
        JButton quickDepositBtn = createStyledButton("Quick Deposit", new Color(40, 167, 69));
        JButton quickWithdrawBtn = createStyledButton("Quick Withdrawal", new Color(255, 193, 7));
        JButton transferBtn = createStyledButton("Transfer Funds", new Color(253, 126, 20));
        
        actionPanel.add(quickDepositBtn);
        actionPanel.add(quickWithdrawBtn);
        actionPanel.add(transferBtn);
        
        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 245, 249));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Button actions
        backButton.addActionListener(e -> bankingSystem.showDashboardView());
        quickDepositBtn.addActionListener(e -> bankingSystem.performTransaction("ACC001", "Deposit"));
        quickWithdrawBtn.addActionListener(e -> bankingSystem.performTransaction("ACC001", "Withdraw"));
        transferBtn.addActionListener(e -> bankingSystem.showAlert("Transfer feature coming soon!"));
    }
    
    public void refreshAccountInfo() {
        balanceLabel.setText("Current Balance: BWP " + String.format("%,.2f", bankingSystem.getSavingsBalance()));
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