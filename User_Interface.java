import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User_Interface extends Remote {
    void receiveMessage(String sender, String message) throws RemoteException;
    void addUser(String userName, User_Interface user) throws RemoteException;
}
