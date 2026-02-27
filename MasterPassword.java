
import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class MasterPassword {

    private static String enteredPassword;
    private static final String HASH_FILE = "master.hash";
    private static String username = "User";
    public static String getUsername() {
        return username;
    }
    // Generate salt
    private static String generateSalt() {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    // Hash password + salt
    private static String hash(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String combined = password + salt;
        byte[] hashBytes = md.digest(combined.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    // Check if first run
    public static boolean isFirstRun() {
        return !(new File(HASH_FILE).exists());
    }

    public static String getEnteredPassword() {
        return enteredPassword;
    }

    // Create new master password (GUI first run)
    public static void createNewPassword(String pass, String user) {
        try {
            String salt = generateSalt();
            String hashed = hash(pass, salt);
            username = (user == null || user.isEmpty()) ? "User" : user;
            FileWriter fw = new FileWriter(HASH_FILE);
            fw.write(username + "|" + salt + ":" + hashed);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Verify password for GUI or CLI
    public static boolean verifyGUI(String input) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(HASH_FILE));
            String line = br.readLine();
            br.close();

            String[] userSplit = line.split("\\|");
            username = userSplit[0];

            String[] parts = userSplit[1].split(":");
            String salt = parts[0];
            String storedHash = parts[1];

            String inputHash = hash(input, salt);

            if (storedHash.equals(inputHash)) {
                enteredPassword = input;
                return true;
            }

        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
        return false;
    }

    // CLI login (optional)
    public static boolean login(Scanner sc) {
        try {
            System.out.print("Enter master password: ");
            String input = sc.nextLine();
            return verifyGUI(input);
        } catch (Exception e) {
            System.out.println("Authentication error.");
            return false;
        }
    }
}