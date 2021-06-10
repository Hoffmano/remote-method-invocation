import java.rmi.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Client {
    public static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    public static BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    public static String[] input = {""};
    public static HashMap<String,String> subParts = new HashMap<String,String>();
    public static String currentPartCode = null;
    public static PartRepositoryInterface partRepository = null;
    public static String serverName = "";

    public static void main(String args[ ]) throws Exception {
        try {
            while (true) {
                System.out.println();
                System.out.print("> ");
                String rawInput = bufferedReader.readLine();
                input = rawInput.split(" ");

                if(input[0].equals("quit")) {
                    break;
                }

                else if(input[0].equals("repo")) {
                    if(input.length == 3 && input[1].equals("bind")) {
                        repoBind(input, rawInput);
                    }
                    else if(input.length == 2 && input[1].equals("parts")) {
                        repoParts();
                    }
                    else if(input.length == 1) {
                        repo();
                    }
                }

                else if(input[0].equals("part")) {
                    if(input.length == 3 && input[1].equals("get")) {
                        partGet(input[2]);
                    }
                    else if(input.length > 2 && input[1].equals("add")) {
                        partAdd(rawInput);
                    }
                    else if(input.length == 2 && input[1].equals("subparts")) {
                        partSubparts();
                    }
                    else if (input.length == 1) {
                        part();
                    }
                }

                else if(input[0].equals("subparts")) {
                    if(input.length == 3 && input[1].equals("add")) {
                        subpartsAdd(input[2]);
                    }
                    else if(input.length == 2 &&input[1].equals("clean")) {
                        subpartsClean();
                    }
                    else if(input.length == 2 && input[1].equals("remove")) {
                        subpartsRemove(input[2]);
                    }
                    else{
                        subparts();
                    }
                }

                else {
                    System.out.println("this command was not recognized");
                }
            }
        } catch(Exception e) {
            System.out.println("client error");
            System.out.println(e);
        }

        System.exit(0);
    }

    public static void repoBind(String[] input, String rawInput) {
        if (input.length != 3) {
            System.out.println("expected command: repo bind <server-name>\nreceived command: " + rawInput);
        }
        else {
            serverName = input[2];
            try {
                partRepository = (PartRepositoryInterface)Naming.lookup("rmi://localhost/" + serverName);
            } catch (Exception e) {
                serverName = "";
                System.out.println("can't connect to " + serverName);
            }
        }
    }

    public static void repoParts() {
        try {
            System.out.println(partRepository.listParts());
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void repo() {
        try {
            System.out.println(partRepository.repo());
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void partGet(String partCode) {
        try {
            String output = partRepository.getPart(partCode);

            if (output.equals("success") ) {
                currentPartCode = partCode;
            }
            else{
                System.out.println(output);
            }
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void partAdd(String rawInput) {
        String[] attributes = rawInput.split(", ");
        String code = attributes[0].split(" ")[2];
        String nome =  attributes[1];
        String desc = attributes[2];

        if(serverName.equals("")){
            System.out.println("you should bind to one repository");
            return;
        }

        try {
            partRepository.addPart(code, nome, desc, subParts);
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void partSubparts() {
        if (currentPartCode.equals("")) {
            System.out.println("you should select one part");
        }

        String part = "";

        try {
            part = partRepository.showPartAttributes(currentPartCode);
        } catch (Exception e) {
            //TODO: handle exception
        }

        String[] partDetails = part.split(System.getProperty("line.separator"));
        String output = "";

        for (int i = 5; i < partDetails.length; i++) {
            String[] subpart = partDetails[i].split(" ");
            String subpartCode = subpart[4];
            String server = subpart[6];

            PartRepositoryInterface subpartRepository = null;

            try {
                subpartRepository = (PartRepositoryInterface)Naming.lookup("rmi://localhost/" + server);
            } catch (Exception e) {
                //TODO: handle exception
            }

            try {
                output += "- code: " + subpartCode + "\n";
                String[] subpartAttributes = subpartRepository.showPartAttributes(subpartCode).split(System.getProperty("line.separator"));

                for (int j = 1; j < subpartAttributes.length; j++) {
                    output += "  " + subpartAttributes[j]  + "\n";
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }

        System.out.println(output.trim());
    }

    public static void part() {
        try {
            if (currentPartCode.equals("")) {
                System.out.println("no part is selected");
                return;
            }
            System.out.println(partRepository.showPartAttributes(currentPartCode));
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void subpartsAdd(String quantity) {
        Integer quantity_integer = Integer.parseInt(quantity);
        String infos = quantity + " " + serverName;

        if (!currentPartCode.equals("") && quantity_integer > 0) {
            subParts.put(currentPartCode, infos);
        }
        else if(currentPartCode == null) {
            System.out.println("you should selected one part");
        }
        else if(quantity_integer == 0) {
            System.out.println("quantity should be at least one");
        }
    }

    public static void subpartsClean() {
        subParts.clear();
    }

    public static void subpartsRemove(String partCode) {
        subParts.remove(partCode);
    }

    public static void subparts() {
        if(subParts.isEmpty()){
            System.out.println("subparts list is empty");
        }
        for (String partCode: subParts.keySet()) {
            String[] infos = subParts.get(partCode).split(" ");
            System.out.println("- " + infos[0] + "x " + partCode + " from " + infos[1]);
        }
    }
}
