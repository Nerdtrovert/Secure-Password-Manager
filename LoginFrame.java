import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Password Manager Login");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // FIRST RUN CHECK BEFORE BUILDING UI
        if (MasterPassword.isFirstRun()) {
            createMasterPassword();
        }

        JLabel title = new JLabel("Enter Master Password", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this::handleLogin);
        getRootPane().setDefaultButton(loginBtn);

        JPanel center = new JPanel();
        center.add(new JLabel("Password: "));
        center.add(passwordField);

        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(loginBtn, BorderLayout.SOUTH);

        setVisible(true);

        // auto-focus password field
        passwordField.requestFocusInWindow();
    }
    private void createMasterPassword() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));

        JTextField usernameField = new JTextField();
        JPasswordField passField1 = new JPasswordField();
        JPasswordField passField2 = new JPasswordField();

        panel.add(new JLabel("New User Name (optional):"));
        panel.add(usernameField);
        panel.add(new JLabel("Create Master Password:"));
        panel.add(passField1);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(passField2);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "New User Setup",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            System.exit(0);
        }

        String pass1 = new String(passField1.getPassword());
        String pass2 = new String(passField2.getPassword());

        if (pass1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty");
            createMasterPassword();
            return;
        }

        if (!pass1.equals(pass2)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            createMasterPassword();
            return;
        }
        String username = usernameField.getText().trim();
        MasterPassword.createNewPassword(pass1,username);

        
        if (!username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Welcome " + username + "!\nYour vault is ready.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Master password created successfully.");
        }
    }
    private void handleLogin(ActionEvent e) {
        String input = new String(passwordField.getPassword());

        // Using existing authentication method from MasterPassword class
        boolean ok = MasterPassword.verifyGUI(input);

        if (ok) {
            dispose(); // close login window
            new MainFrame(input); // open main UI, pass password
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password");
        }
    }
}
