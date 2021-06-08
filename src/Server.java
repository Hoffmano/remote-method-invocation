import java.rmi.*;

public class Server {
    public static void main (String args [ ]) {
        try {
            PartRepository partRepository = new PartRepository(args[0]);
            Naming.rebind(args[0], partRepository);

            System.out.println(args[0] + " is ready");
        } catch(Exception e) {
            System.out.println("Error "+ e.getMessage());
        }
    }
}
