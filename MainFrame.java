
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Vault vault;
    private JTextField siteField;
    private JPasswordField passField;

    public MainFrame(String masterPassword) {

        vault = new Vault(masterPassword);

        setTitle("Password Manager");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Vault Manager", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(title);
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // Form panel
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));

        siteField = new JTextField(15);
        passField = new JPasswordField(15);

        form.add(new JLabel("Website/App:"));
        form.add(siteField);
        form.add(new JLabel("Password:"));
        form.add(passField);

        main.add(form);
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // Button panel
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton addBtn = new JButton("Add");
        addBtn.setPreferredSize(new Dimension(90, 30));
        addBtn.addActionListener(e -> addCredential());

        JButton listBtn = new JButton("Show Vault");
        listBtn.setPreferredSize(new Dimension(120, 30));
        listBtn.addActionListener(e -> showVault());

        buttons.add(addBtn);
        buttons.add(listBtn);

        main.add(buttons);

        add(main);
        setVisible(true);
    }
    private void showVault() {

        String[][] data = vault.getCredentialTable();
        String[] cols = {"Website/App", "Password"};

        JTable table = new JTable(data, cols);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(360, 200));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Vault Contents",
                JOptionPane.PLAIN_MESSAGE
        );
    }
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
}
