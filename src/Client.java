import java.rmi.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Client {
    public static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    public static BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    public static String[] input = {""};
    public static HashMap<String,Integer> subParts = new HashMap<String,Integer>();
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
                    if(input[1].equals("add")) {
                        subpartsAdd(input[2]);
                    }
                    else if(input[1].equals("clean")) {
                        subpartsClean();
                    }
                    else if(input[1].equals("remove")) {
                        subpartsRemove();
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
            String output = partRepository.addPart(code, nome, desc, subParts);
        } catch (Exception e) {
            System.out.println("server error");
        }
    }

    public static void partSubparts() {
        // TODO Auto-generated method stub
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

    public static void subpartsAdd(String input) {
        Integer quantity = Integer.valueOf(input);

        if (!currentPartCode.equals("") && quantity > 0) {
            subParts.put(currentPartCode, quantity);
        }
        else if(currentPartCode == null) {
            System.out.println("you should selected one part");
        }
        else if(quantity == 0) {
            System.out.println("quantity should be at least one");
        }
    }

    public static void subpartsClean() {
        subParts.clear();
    }

    public static void subpartsRemove() {
        // TODO Auto-generated method stub
    }

    public static void subparts() {
        // TODO Auto-generated method stub
    }

}
