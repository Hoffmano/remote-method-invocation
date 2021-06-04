import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;

public class Implementation extends UnicastRemoteObject implements Hello {
    
    public Implementation() throws RemoteException{
        super();
    }

    public String sayHello() {
        return "Hello.";
    }

    public String sayThanks() {
        return "Thanks!";
    }

    public String sayGoodbye() {
        return "Goodbye.";
    }
}
