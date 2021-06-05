import java.net.*;
import java.rmi.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public interface PartRepositoryInterface extends Remote {
    String listParts() throws RemoteException;
    // String getPart() throws RemoteException;
    // String showPartAttributes() throws RemoteException;
    // String clearSubPartsList() throws RemoteException;
    String addPart(String code, String name, String description) throws RemoteException;
    
}
