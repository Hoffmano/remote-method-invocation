import java.rmi.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Client {
    public static void main(String args[ ]) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String[] input = {""};
        HashMap<String,Integer> subParts = new HashMap<String,Integer>();
        String currentPartCode = null;
        PartRepositoryInterface partRepository = null;
        String serverName = "";

        try {
            while (true) {
                System.out.println();
                System.out.print("> ");
                String rawInput = bufferedReader.readLine();
                input = rawInput.split(" ");

                if(input[0].equals("quit")) {
                    break;
                }

                if(input[0].equals("bind")) {
                    try{
                        serverName = input[1];
                    }
                    catch(Exception e){
                        serverName = "";
                        System.out.println("Server name not found");
                    }

                    try {
                        partRepository = (PartRepositoryInterface)Naming.lookup("rmi://localhost/" + serverName);
                    } catch (Exception e) {
                        serverName = "";
                        System.out.println("Can't connect to " + serverName);
                    }
                }

                else if(input[0].equals("listp")) {
                    try {
                        System.out.println(partRepository.listParts());
                    } catch (Exception e) {
                        System.out.println("Client error");
                    }
                }

                else if(input[0].equals("getp")) {
                    try {
                        String partCode = input[1];
                        String result = partRepository.getPart(partCode);

                        if (result.equals("Current part set as " + partCode) ) {
                            currentPartCode = partCode;
                        }
                        else{
                            System.out.println("Part not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Client error");
                    }
                }

                else if(input[0].equals("showp")) {
                    try {
                        System.out.println(partRepository.showPartAttributes(currentPartCode));
                    } catch (Exception e) {
                        System.out.println("Client error");
                    }
                }

                else if(input[0].equals("clearlist")) {
                    subParts.clear();
                }

                else if(input[0].equals("addsubpart")) {
                    Integer quantity = Integer.valueOf(input[1]);

                    subParts.put(currentPartCode, quantity);
                }

                else if(input[0].equals("addp")) {
                    // addp p4, Tabua de Ype, Tabua de Ype de 60cm x 100cm
                    String[] attributes = rawInput.split(", ");
                    String code = attributes[0].split(" ")[1];
                    String nome =  attributes[1];
                    String desc = attributes[2];

                    String response = partRepository.addPart(code, nome, desc, subParts, serverName);

                    System.out.println(response);
                }
                else {
                    System.out.println("This command was not recognized");
                }
            }
        } catch(Exception e) {
            System.out.println("Client error");
            System.out.println(e);
        }
        System.exit(0);
    }
}
