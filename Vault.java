
import java.io.*;

public class Vault {

    private final String FILE = "vault.txt";
    private final String masterPassword;
    public Vault(String masterPassword) {
        this.masterPassword = masterPassword;
    }
    // Add credential
    public void addCredential(String user, String pass) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            String encrypted = CryptoUtils.encrypt(user + " : " + pass, masterPassword);
            fw.write(encrypted + "\n");
            System.out.println("Credential stored.");
        } catch (IOException e) {
            System.out.println("Error writing vault.");
        }
    }

    // List credentials
    public void listCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            System.out.println("\nVault Contents:");
            while ((line = br.readLine()) != null) {
                String decrypted = CryptoUtils.decrypt(line, masterPassword);
                System.out.println(decrypted);
            }
        } catch (IOException e) {
            System.out.println("Vault empty.");
        }
    }

    // Delete credential
public void deleteCredential(String username) {

        File input = new File(FILE);
        File temp = new File("temp.txt");
        boolean found = false;
        try (
                BufferedReader br = new BufferedReader(new FileReader(input)); FileWriter fw = new FileWriter(temp)) {
            String line;

            while ((line = br.readLine()) != null) {

                // decrypt first
                String decrypted = CryptoUtils.decrypt(line, masterPassword);

                if (decrypted.startsWith(username + " :")) {
                    found = true;
                    continue; // skip writing this line
                }

                fw.write(line + "\n"); // keep encrypted line
            }

        } catch (Exception e) {
            System.out.println("Error processing vault.");
            return;
        }

        input.delete();
        temp.renameTo(input);

        if (found) {
            System.out.println("Credential deleted.");
        } else {
            System.out.println("Username not found.");
        }
    }
    public String getAllCredentials() {
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("vault.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String decrypted = CryptoUtils.decrypt(line, masterPassword);
                result.append(decrypted).append("\n");
            }

        } catch (IOException e) {
            return "Vault empty.";
        }

        return result.toString();
    }
    public String[][] getCredentialTable() {
        java.util.List<String[]> rows = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("vault.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String decrypted = CryptoUtils.decrypt(line, masterPassword);
                String[] parts = decrypted.split(" : ");
                if (parts.length == 2) {
                    rows.add(parts);
                }
            }
        } catch (Exception e) {
            return new String[0][0];
        }

        return rows.toArray(new String[0][]);
    }
    public void resetVault() {
        File vaultFile = new File("vault.txt");
        if (vaultFile.exists()) {
            vaultFile.delete();
        }

        File masterFile = new File("master.hash");
        if (masterFile.exists()) {
            masterFile.delete();
        }
    }
}