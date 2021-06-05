import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;

public class PartRepository extends UnicastRemoteObject implements PartRepositoryInterface {
    private Map<String, Part> parts;

    public PartRepository() throws RemoteException{
        super();
        this.parts = new HashMap<String, Part>();

        Part part1 = new Part("p1", "part 1", "description part 1", new HashMap<String, Part>());
        Part part2 = new Part("p2", "part 2", "description part 2", new HashMap<String, Part>());
        Part part3 = new Part("p3", "part 3", "description part 3", new HashMap<String, Part>());

        this.parts.put("p1", part1);
        this.parts.put("p2", part2);
        this.parts.put("p3", part3);
    }

    public String listParts() {
        String result = "";

        for (String partCode: this.parts.keySet()) {
            String key = partCode.toString();
            result += key + "\n";
        }

        return result;
    }

    // public String getPart() {
    //     return "true";
    // }

    // public String showPart() {
    //     return "asdsadsaad";
    // }

    // public String clearList() {
    //     return "clearList";
    // }

    public String addPart(String code, String name, String description) {
        this.parts.put(code, new Part(code, name, description, new HashMap<String, Part>()));

        return "Part added successfully";
    }
}
