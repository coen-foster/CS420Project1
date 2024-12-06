import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your User name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your RMI registry port (e.g., 1099): ");
            int port = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            LocateRegistry.createRegistry(port);
            User user = new User(name);
            Naming.rebind("rmi://localhost:" + port + "/" + name, user);
            System.out.println("User " + name + " is ready!");

            System.out.println("Do you want to connect to an existing user? (yes/no)");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
            	try {
	            	System.out.print("Enter the other user's port number:  ");
	            	String otherport = scanner.nextLine();
	            	System.out.print("Enter the other user's name: ");
	                String otherName = scanner.nextLine();
	                
	                String url = "rmi://localhost:" + otherport + "/" + otherName;
	                User_Interface otheruser = (User_Interface) Naming.lookup(url);
	
	                otheruser.addUser(name, user);
	                user.addUser(otherName, otheruser);
            	} catch (Exception e) {
            		System.out.println("Error: Invalid User");
            	}
            }

            System.out.println("Chat session started. Type 'add' to add more users, 'users' to view known users, or 'exit' to quit.");

            while (true) {
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                } else if (input.equalsIgnoreCase("add")) {
                	try {
    	            	System.out.print("Enter the other user's port number:  ");
    	            	String otherport = scanner.nextLine();
    	            	System.out.print("Enter the other user's name: ");
    	                String otherName = scanner.nextLine();
    	                
    	                String url = "rmi://localhost:" + otherport + "/" + otherName;
    	                User_Interface otheruser = (User_Interface) Naming.lookup(url);
    	
    	                otheruser.addUser(name, user);
    	                user.addUser(otherName, otheruser);
                	} catch (Exception e) {
                		System.out.println("Error: Invalid User");
                	}
                } else if (input.equalsIgnoreCase("users")) {
                    user.displayKnownUsers();
                } else {
                    user.broadcastMessage(input);
                }
            }
            
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
