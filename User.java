import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class User extends UnicastRemoteObject implements User_Interface {
    private static final long serialVersionUID = 1L;
	private final String name;
    private final ConcurrentHashMap<String, User_Interface> knownUsers = new ConcurrentHashMap<>();

    public User(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized void receiveMessage(String sender, String message) throws RemoteException {
        System.out.println(sender + ": " + message);
    }

    @Override
    public synchronized void addUser(String userName, User_Interface user) throws RemoteException {
        if (!knownUsers.containsKey(userName)) {
            knownUsers.put(userName, user);
            System.out.println(userName + " has been added to your user list.");
        }
    }

    public void broadcastMessage(String message) throws RemoteException {
        for (User_Interface user : knownUsers.values()) {
            user.receiveMessage(name, message);
        }
    }

    public void displayKnownUsers() {
        System.out.println("Known Users: " + knownUsers.keySet());
    }
}
