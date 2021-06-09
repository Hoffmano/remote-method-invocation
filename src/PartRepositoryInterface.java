import java.rmi.*;
import java.util.HashMap;

public interface PartRepositoryInterface extends Remote {
    String listParts() throws RemoteException;
    String repo() throws RemoteException;
    String getPart(String partCode) throws RemoteException;
    String showPartAttributes(String partCode) throws RemoteException;
    String addPart(String code, String name, String description, HashMap<String, Integer> subParts) throws RemoteException;
    
}
