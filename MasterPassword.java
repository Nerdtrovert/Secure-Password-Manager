
import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import java.security.SecureRandom;
import java.io.Console;

public class MasterPassword {

    private static String enteredPassword;
    private static final String HASH_FILE = "master.hash";

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
    private static boolean firstRun() {
        return !(new File(HASH_FILE).exists());
    }

    public static String getEnteredPassword() {
        return enteredPassword;
    }

    // CLI Login
    public static boolean login(Scanner sc) {
        try {
            String input;

            // FIRST RUN → SET PASSWORD
            if (firstRun()) {
                Console console = System.console();
                if (console != null) {
                    char[] passChars = console.readPassword("Create master password: ");
                    input = new String(passChars);
                } else {
                    System.out.print("Create master password: ");
                    input = sc.nextLine();
                }

                String salt = generateSalt();
                String hashed = hash(input, salt);

                try (FileWriter fw = new FileWriter(HASH_FILE)) {
                    fw.write(salt + ":" + hashed);
                }

                enteredPassword = input;
                System.out.println("Master password saved.");
                return true;
            }

            // NORMAL LOGIN
            Console console = System.console();
            if (console != null) {
                char[] passChars = console.readPassword("Enter master password: ");
                input = new String(passChars);
            } else {
                System.out.print("Enter master password: ");
                input = sc.nextLine();
            }

            return verifyGUI(input);

        } catch (Exception e) {
            System.out.println("Authentication error.");
            return false;
        }
    }

    // Used by GUI login AND CLI login
    public static boolean verifyGUI(String input) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(HASH_FILE));
            String line = br.readLine();
            br.close();

            String[] parts = line.split(":");
            String salt = parts[0];
            String storedHash = parts[1];

            String inputHash = hash(input, salt);

            if (storedHash.equals(inputHash)) {
                enteredPassword = input;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}