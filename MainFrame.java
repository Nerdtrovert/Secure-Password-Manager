
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;

public class MainFrame extends JFrame {

    private Vault vault;
    private JTextField siteField;
    private JPasswordField passField;
    private JTable table;
    private JLabel userLabel;
    private char defaultEcho;

    public MainFrame(String masterPassword) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        vault = new Vault(masterPassword);

        setTitle("Password Manager");
        setSize(480, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JPanel userRow = new JPanel(new BorderLayout());
        userRow.setOpaque(false);

        userLabel = new JLabel("User: " + MasterPassword.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.GRAY);

        userRow.add(userLabel, BorderLayout.EAST);

        JLabel title = new JLabel("Password Manager", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(userRow);
        header.add(title);

        main.add(header);
        main.add(new JSeparator());
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // ===== FORM CARD =====
        JPanel form = new JPanel(new GridLayout(2, 3, 12, 12));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        siteField = new JTextField();
        passField = new JPasswordField();
        defaultEcho = passField.getEchoChar();

        JCheckBox showPassword = new JCheckBox("Show");

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar(defaultEcho);
            }
        });

        form.add(new JLabel("Website / App:"));
        form.add(siteField);
        form.add(new JLabel(""));

        form.add(new JLabel("Password:"));
        form.add(passField);
        form.add(showPassword);

        main.add(form);
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // ===== BUTTONS =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addBtn = new JButton("Add Credential");
        JButton listBtn = new JButton("Open Vault");
        JButton resetBtn = new JButton("Reset User");

        resetBtn.setForeground(Color.RED);

        addBtn.addActionListener(e -> addCredential());
        listBtn.addActionListener(e -> showVault());
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

        String site = siteField.getText().trim();
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

    // ===== VAULT VIEW =====
    private void showVault() {

        JDialog dialog = new JDialog(this, "Vault Contents", true);
        dialog.setSize(440, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel userInfo = new JLabel("User: " + MasterPassword.getUsername());
        userInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        String[][] data = vault.getCredentialTable();
        String[] cols = {"Website/App", "Password"};

        table = new JTable(new javax.swing.table.DefaultTableModel(data, cols));

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(table);

        JButton deleteBtn = new JButton("Delete Selected");
        JButton copyBtn = new JButton("Copy Password");

        deleteBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(dialog, "Select a row first");
                return;
            }

            String site = table.getValueAt(row, 0).toString();

            vault.deleteCredential(site);

            String[][] newData = vault.getCredentialTable();
            table.setModel(new javax.swing.table.DefaultTableModel(newData, cols));
        });

        copyBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(dialog, "Select a row first");
                return;
            }

            String password = table.getValueAt(row, 1).toString();

            Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .setContents(new StringSelection(password), null);

            JOptionPane.showMessageDialog(dialog, "Password copied to clipboard!");
        });

        JPanel bottom = new JPanel();
        bottom.add(copyBtn);
        bottom.add(deleteBtn);

        dialog.add(userInfo, BorderLayout.NORTH);
        dialog.add(scroll, BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
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

        JOptionPane.showMessageDialog(this,
                "Vault cleared. Application will now restart.");

        dispose();
        new LoginFrame();
    }
}