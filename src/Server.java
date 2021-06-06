//import java.net.*;
import java.rmi.*;
//import java.rmi.server.*;
//import java.rmi.registry.*;

public class Server {
    public static void main (String args [ ]) {
        try {
            PartRepository partRepository = new PartRepository();
            Naming.rebind(args[0], partRepository);

            System.out.println(args[0] + " is ready");
        } catch(Exception e) {
            System.out.println("Error "+ e.getMessage());
        }
    }
}
