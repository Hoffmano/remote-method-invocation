import java.net.*;
import java.rmi.*;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
    String sayThanks() throws RemoteException;
    String sayGoodbye() throws RemoteException;
}
