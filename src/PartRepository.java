import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;

public class PartRepository extends UnicastRemoteObject implements PartRepositoryInterface {
    private Map<String, Part> parts;

    public PartRepository() throws RemoteException{
        super();
        parts = new HashMap<String, Part>();
    }

    public Map listParts() {
        return this.parts;
    }

    // public String getPart() {
    //     return true;
    // }

    // public String showPart() {
    //     return "asdsadsaad";
    // }

    // public String clearList() {
    //     return "clearList";
    // }

    // public String addPart() {
    //     return "addPart";
    // }
}
