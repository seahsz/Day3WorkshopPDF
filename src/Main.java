// Instructions - Write a Java console program that allows a user to add, remove
//  and list the contents of a shopping cart

import java.io.Console;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // Initialize db path
        String dbPath = "db";

        if (args.length > 1) {
            System.out.println("Only have 1 input for Save Directory destination");
            System.exit(-1);
        } else if (args.length == 1) {
            dbPath = args[0];
        } 

        // Creates the directory to store the user's cart
        File db = new File(dbPath);

        if (!db.exists()) {
            db.mkdir();
            System.out.printf("%s directory created\n", dbPath);
        } else {
            System.out.printf("%s directory already exist\n", dbPath);
        }


        boolean stop = false;
        Console cons = System.console();

        System.out.println("Welcome to your shopping cart");

        // Initialize cart & file & isLogin
        List<String> cart = new ArrayList<>();
        boolean isLogin = false;
        ShoppingCartDB cartDB = new ShoppingCartDB("", dbPath);

        while (!stop) {
            // list; add apple, orange; delete 2, end
            String input = cons.readLine("CMD>");
            
            String[] inputs = input.replaceAll("\\p{Punct}", "").split(" ");

            switch (inputs[0].trim().toLowerCase()) {
                // list
                case "list":
                    if (cart.size() == 0)
                        System.out.println("Your cart is empty");
                    else {
                        for (int idx = 0; idx < cart.size(); idx++)
                            System.out.printf("%d. %s\n", idx + 1, cart.get(idx));
                    }
                    break;
                
                // add apple, pear
                case "add":
                    for (int idx = 1; idx < inputs.length; idx++) {
                        String toAdd = inputs[idx];
                        if (cart.contains(toAdd))
                            System.out.printf("you have %s in your cart\n", toAdd);
                        else {
                            cart.add(toAdd);
                            System.out.printf("%s added to cart\n", toAdd);
                        }
                    }
                    break;
                
                // delete 2
                case "delete":
                    if (inputs.length > 2) // because to remove by index, only advisable to so one by one to avoid
                                           // errors
                        System.out.println("can only delete 1 item at a time");
                    else {
                        int deleteIdx = Integer.parseInt(inputs[1]);
                        if (deleteIdx <= 0 || deleteIdx > cart.size())
                            System.out.println("Incorrect item index");
                        else {
                            String deletedItem = cart.get(deleteIdx - 1);
                            cart.remove(deleteIdx - 1);
                            System.out.printf("%s removed from cart\n", deletedItem);
                        }
                    }
                    break;

                // login fred
                case "login":
                    // end login operation since login user not provided
                    if (inputs.length != 2) {
                        System.out.println("login id missing");
                        break;
                    }
                    String user = inputs[1];
                    isLogin = true;

                    // Set user
                    cartDB.setUser(user);

                    // Load the db file
                    cart = cartDB.loadCart(cart);
                    break;
                   
                // save    
                case "save":
                    if (!isLogin) {
                        System.out.println("Please login first before saving");
                        break;
                    }          
                    cartDB.saveCart(cart);  
                    break;        

                case "users":
                    String[] temp = db.list();
                    String[] allUsers = new String[temp.length];

                    // remove the .db from the end of each file name
                    for (int idx = 0; idx < temp.length; idx++) {
                        allUsers[idx] = temp[idx].replaceAll(".db", "");
                    }

                    System.out.println("The following users are registered");
                    for (String u : allUsers)
                        System.out.println(u);
                    break;

                // to end the program
                case "end":
                    stop = true;
                    break;

                default:
                    System.out.println("this is not a valid input");
                    break;
            }
        }
    }

}
