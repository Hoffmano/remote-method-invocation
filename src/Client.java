import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.ArrayList;

public class Client {
    public static void main(String args[ ]) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String[] input = {""};
        // Map<String,Integer> subParts= new HashMap<String,Integer>();
        String currentPartCode = null;
        PartRepositoryInterface partRepository = null;

        System.out.println("Client is ready");

        try {
            while (true) {
                System.out.print("> ");
                String rawInput = bufferedReader.readLine();
                input = rawInput.split(" ");

                if(input[0].equals("quit")) {
                    System.out.println("Client was successfully quit");
                    break;
                }

                if(input[0].equals("bind")) {
                    String serverName = "";

                    try{
                    serverName = input[1];
                    }
                    catch(Exception e){
                        System.out.println("Server name not found");
                    }

                    try {
                        partRepository = (PartRepositoryInterface)Naming.lookup("rmi://localhost/" + serverName);
                        System.out.println("Connected to " + serverName);
                    } catch (Exception e) {
                        System.out.println("Error in connect to " + serverName);
                    }
                }
                else if(input[0].equals("listp")) {
                    String parts = partRepository.listParts();

                    System.out.println(parts);

                    // for (String key : parts.keySet()) {
                    //     String partCode = key.toString();
                    //     System.out.println(key);
                    // }
                }
                // else if(input[0].equals("getp")) {
                //     String partName = bufferedReader.readLine();
                //     boolean isPartFound = partRepository.getPart(partCode);                   

                //     if (isPartFound) {
                //         currentPart = partName;
                //         System.out.println("Part with code " + partCode + " is found");
                //     }
                // }
                // else if(input[0].equals("showp")) {
                //     System.out.println(partRepository.showPart());
                // }
                // else if(input[0].equals("clearlist")) {
                //     subPartesAtual.clear();
                //     //partRepository.clearList();
                //     //System.out.println("Subpart list cleaned");
                // }
                else if(input[0].equals("addsubpart")) {
                    String code = input[1];
                    String quantity = input[2];

                    subPartesAtual.put(currentPart, Integer.valueOf(quantity));
                    //partRepository.addSubPart(quantity);
                    System.out.println("Subpart added with success");
                }
                else if(input[0].equals("addp")) {
                    // addp p4, Tabua de Ype, Tabua de Ype de 60cm x 100cm
                    String[] attributes = rawInput.split(", ");
                    String code = attributes[0].split(" ")[1];
                    String nome =  attributes[1];
                    String desc = attributes[2];

                    String response = partRepository.addPart(code, nome, desc);

                    System.out.println(response);
                }
                else {
                    System.out.println("This command was not recognized");
                }
            }
        } catch(Exception e) {
            System.out.println("Error");
            System.out.println(e);
        }
        System.exit(0);
    }
}
