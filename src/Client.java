import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Client {
    public static void main(String args[ ]) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String[] input = null;
        // Map<String,Integer> subParts= new HashMap<String,Integer>();
        String currentPartCode = null;
        PartRepositoryInterface partRepository = null;

        try {
            while (!input[0].equalsIgnoreCase("quit")) {
                System.out.println("Client is ready");
                input = bufferedReader.readLine().split(" ");

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
                        System.out.println("Server connected to " + serverName);
                    } catch (Exception e) {
                        System.out.println("Error in connect to " + serverName);
                    }
                }
                else if(input[0].equals("listp")) {
                    Map<String, Part> parts = partRepository.listParts();

                    for (String key : parts.keySet()) {
                        String partCode = key.toString();
                        System.out.println(key);
                    }
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
                // else if(input[0].equals("addsubpart")) {
                //     String quantity = input[1];
                //     subPartesAtual.put(currentPart, Integer.valueOf(quantity));
                //     //partRepository.addSubPart(quantity);
                //     System.out.println("Subpart added with success");
                // }
                // else if(input[0].equals("addp")) {
                //     int code = Integer.parseInt(input[1]);
                //     String nome =  input[2];
                //     String desc = input[3];
                //     partRepository.addPart(code, nome, desc, subPartesAtual);
                //     System.out.println("Part added with success");
                // }
                else {
                    System.out.println("command is undefined");
                }
            }
        } catch(Exception e) {
            System.out.println("Error starting client");
        }
        System.exit(0);
    }
}
