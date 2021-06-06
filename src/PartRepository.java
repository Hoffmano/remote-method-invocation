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

        for (String partCode : this.parts.keySet()) {
            result += partCode + "\n";
        }

        return result;
    }

    public String getPart(String partCode) {
        Part part = null;
        String result = "";

        try {
            System.out.println(this.parts);
            System.out.println(partCode);
            part = this.parts.get(partCode);

            result += "code: " + partCode + "\n";
            result += "name: " + part.name + "\n";
            result += "description: " + part.description + "\n";
            result += "sub parts:\n";
            
            for (String subPartCode : part.subParts.keySet()) {
                String quantity = part.subParts.get(subPartCode).toString();
                result += "  - " + quantity + "x " + subPartCode + "\n";
            }

            if (part.subParts.isEmpty()) {
                result += "  - This is a primitive part";
            }

            return "Current part set as " + partCode;
        } catch (Exception e) {
            return "This part was not found in this part repository\n" + e.getMessage();
        }
    }

    public String showPartAttributes(String partCode) {
        Part part = this.parts.get(partCode);
        String result = "";

        result += "code: " + partCode + "\n";
        result += "name: " + part.name + "\n";
        result += "description: " + part.description + "\n";
        result += "sub parts:\n";
        
        for (String subPartCode: part.subParts.keySet()) {
            String quantity = part.subParts.get(subPartCode).toString();
            result += "  - " + quantity + "x " + subPartCode + "\n";
        }

        if (part.subParts.isEmpty()) {
            result += "  - This is a primitive part";
        }

        return result;
    }

    public String addPart(String code, String name, String description, HashMap<String, Integer> subParts) {
        this.parts.put(code, new Part(code, name, description, subParts));
        return "";
    }
}
