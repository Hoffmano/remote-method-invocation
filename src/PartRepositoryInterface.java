import java.net.*;
import java.rmi.*;
import java.util.Map;
import java.util.HashMap;

public interface PartRepositoryInterface extends Remote {
    Map listParts() throws RemoteException;
    // String getPart() throws RemoteException;
    // String showPartAttributes() throws RemoteException;
    // String clearSubPartsList() throws RemoteException;
    // String addPart() throws RemoteException;
    
}
