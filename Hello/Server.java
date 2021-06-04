import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class Server {
    public static void main (String args [ ]) {
        try {
            Implementation obj = new Implementation();
            Naming.rebind("Server", obj);
            System.out.println("Server is ready");
        } catch(Exception e) {
            System.out.println("Server erro"+ e.getMessage());
        }
    }
}
