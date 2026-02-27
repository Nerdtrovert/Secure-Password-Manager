
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private Vault vault;
    private JTextField siteField;
    private JPasswordField passField;
    private JTable table;
    private JLabel userLabel;

    public MainFrame(String masterPassword) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        vault = new Vault(masterPassword);

        setTitle("Password Manager");
        setSize(460, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== HEADER (Title center, User right) =====
        // ===== HEADER PANEL =====
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

// top row → username aligned right
        JPanel userRow = new JPanel(new BorderLayout());
        userRow.setOpaque(false);

        userLabel = new JLabel("User: " + MasterPassword.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.GRAY);

        userRow.add(userLabel, BorderLayout.EAST);

// second row → perfectly centered title
        JLabel title = new JLabel("Password Manager", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(userRow);
        header.add(title);

        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        main.add(header);

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        siteField = new JTextField();
        passField = new JPasswordField();

        form.add(new JLabel("Website/App:"));
        form.add(siteField);
        form.add(new JLabel("Password:"));
        form.add(passField);

        main.add(form);

        // ===== BUTTONS =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> addCredential());

        JButton listBtn = new JButton("Show Vault");
        listBtn.addActionListener(e -> showVault());

        JButton resetBtn = new JButton("Reset User");
        resetBtn.setForeground(Color.RED);
        resetBtn.addActionListener(e -> resetUser());

        buttons.add(addBtn);
        buttons.add(listBtn);
        buttons.add(resetBtn);

        main.add(buttons);

        add(main);
        setVisible(true);
    }

    // ===== ADD CREDENTIAL =====
    private void addCredential() {
        String site = siteField.getText();
        String pass = new String(passField.getPassword());

        if (site.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }

        vault.addCredential(site, pass);
        JOptionPane.showMessageDialog(this, "Credential saved!");

        siteField.setText("");
        passField.setText("");
    }

    // ===== SHOW VAULT POPUP =====
    private void showVault() {

        String[][] data = vault.getCredentialTable();
        String[] cols = {"Website/App", "Password"};

        table = new JTable(data, cols);
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(380, 200));

        JLabel userInfo = new JLabel("User: " + MasterPassword.getUsername());
        userInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton deleteBtn = new JButton("Delete Selected");

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            String site = table.getValueAt(row, 0).toString();
            vault.deleteCredential(site);

            JOptionPane.showMessageDialog(this, "Deleted!");
            showVault(); // reload popup
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(userInfo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(deleteBtn, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Vault Contents",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // ===== RESET USER =====
    private void resetUser() {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will delete ALL saved passwords and reset the master password.\nAre you sure?",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        vault.resetVault();

        JOptionPane.showMessageDialog(
                this,
                "Vault cleared. Application will now restart."
        );

        dispose();
        new LoginFrame();
    }
}