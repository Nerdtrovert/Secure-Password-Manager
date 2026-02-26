
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Password Manager Login");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Enter Master Password", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this::handleLogin);

        JPanel center = new JPanel();
        center.add(new JLabel("Password: "));
        center.add(passwordField);

        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(loginBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String input = new String(passwordField.getPassword());

        // Use your existing authentication logic
        boolean ok = MasterPassword.verifyGUI(input);

        if (ok) {
            dispose(); // close login window
            new MainFrame(input); // open main UI, pass password
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password");
        }
    }
}
