import java.rmi.*;
import java.rmi.server.*;
import java.util.Map;
import java.util.HashMap;

public class PartRepository extends UnicastRemoteObject implements PartRepositoryInterface {
    private Map<String, Part> parts;
    public String serverName;

    public PartRepository(String serverName) throws RemoteException{
        super();
        this.parts = new HashMap<String, Part>();
        this.serverName = serverName;

        Part part1 = new Part("p1", "part 1", "description part 1", new HashMap<String, Integer>(), serverName);
        Part part2 = new Part("p2", "part 2", "description part 2", new HashMap<String, Integer>(), serverName);
        Part part3 = new Part("p3", "part 3", "description part 3", new HashMap<String, Integer>(), serverName);

        this.parts.put("p1", part1);
        this.parts.put("p2", part2);
        this.parts.put("p3", part3);
    }

    public String listParts() {
        String result = "";

        for (String partCode : this.parts.keySet()) {
            result += partCode + "\n";
        }

        return result.trim();
    }

    public String getPart(String partCode) {
        try {
            Part part = this.parts.get(partCode);
            if (part != null) {
                return "success";
            }
            return "this part was not found in this part repository";
        } catch (Exception e) {
            return "this part was not found in this part repository";
        }
    }

    public String showPartAttributes(String partCode) {
        Part part = this.parts.get(partCode);
        String result = "";

        result += "code: " + partCode + "\n";
        result += "name: " + part.name + "\n";
        result += "server: " + part.serverName + "\n";
        result += "description: " + part.description + "\n";
        result += "sub parts:\n";
        
        for (String subPartCode: part.subParts.keySet()) {
            String quantity = part.subParts.get(subPartCode).toString();
            result += "  - " + quantity + "x " + subPartCode + "\n";
        }

        if (part.subParts.isEmpty()) {
            result += "  - this is a primitive part";
        }

        return result.trim();
    }

    public String addPart(String code, String name, String description, HashMap<String, Integer> subParts) {
        this.parts.put(code, new Part(code, name, description, subParts, serverName));
        return "";
    }

    public String repo() {
        String result = "";
        Integer quantityOfParts = this.parts.size();

        result += "repository name: " + serverName + "\n";
        result += "quantity of parts: " + quantityOfParts;

        return result;
    }
}
