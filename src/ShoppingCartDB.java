// ShoppingCartDB is meant to handle load & save shopping cart

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.List;
import java.io.File;

public class ShoppingCartDB {

    private String user;
    private String directory;
    private String userPath;

    public ShoppingCartDB() {
        this("", "db");
    }

    public ShoppingCartDB(String user) {
        this(user, "db");       // because program defaults to use a directory called db if db was not input into method call
    }

    public ShoppingCartDB(String user, String directory) {
        this.user = user;
        this.directory = directory;
    }

    // Getters
    public String getUser() { return user; }
    public void setUser(String user) { 
        this.user = user; 
        this.userPath = "%s/%s.db".formatted(directory, this.user); // every time set user --> need to update the userPath
    }

    public String getDirectory() { return directory; }
    public void setDirectory(String directory) { this.directory = directory; }

    public List<String> loadCart(List<String> cart) throws IOException {

        // Check if db file exist
        File file = new File(userPath);
        if (!file.exists()) {
            file.createNewFile(); // automatically checks if file exist, if not it creates new file with path name
            return cart;
        }

        BufferedReader br = new BufferedReader(new FileReader(userPath));
        String line;

        while ((line = br.readLine()) != null) {
            cart.add(line);
        }
        
        br.close();
        if (cart.isEmpty()) {
            System.out.printf("%s, your cart is empty\n", user);
        } else {
            System.out.printf("%s, your cart has %d items\n", user, cart.size());
        }
        return cart;
    }

    public void saveCart(List<String> cart) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(userPath));
        for (String line : cart) {
            bw.write(line);
            bw.newLine();
        }

        bw.flush();
        bw.close();
        System.out.println("your cart has been saved");
    }
    
    
}
