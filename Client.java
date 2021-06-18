/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

    Esta classe e responsavel por implementar toda a interface do cliente no console, permitindo que o usuario
manipule os repositorios de partes.
------------------------------------------------------------------------------------------------------------ */

import java.rmi.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Client {
    //Variaveis utilizadas para ler a entrada do usuario
    public static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    public static BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    public static String[] input = {""};

    //Conjunto de subpartes
    public static HashMap<String,String> subParts = new HashMap<String,String>();

    //Variaveis utilizadas para contectar aos repositorios
    public static PartRepositoryInterface partRepository = null;
    public static String serverName = "";

    //Parte selecionada pelo usuario
    private static Part currentPart;

    //Metodo main captura os comandos inseridos pelo usuario no console
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
                    else if(input.length == 3 && input[1].equals("remove")) {
                        subpartsRemove(input[2]);
                    }
                    else{
                        subparts();
                    }
                }

                else {
                    System.out.println("Error: This command was not recognized");
                }
            }
        } catch(Exception e) {
            System.out.println("Client Error:");
            System.out.println(e);
        }

        System.exit(0);
    }

    //Metodo responsavel por conectar ao servidor do repositorio
    public static void repoBind(String[] input, String rawInput) {
        //Verificacao se o input e maior que o tamanho limite
        if (input.length != 3) {
            System.out.println("Error: Expected command: repo bind <server-name>\nreceived command: " + rawInput);
        }
        else {
            //Obtendo o nome do servidor
            serverName = input[2];
            try {
                //Conectando ao servidor
                partRepository = (PartRepositoryInterface)Naming.lookup("rmi://localhost/" + serverName);
                if( partRepository == null){
                    throw  new RuntimeException("Server not found");
                }
            } catch (Exception e) {    
                System.out.println("Error: Can't connect to " + serverName);
                serverName = "";
            }
        }
    }

    //Metodo responsavel por solicitar ao servidor a lista de partes
    public static void repoParts() {
        try {
            System.out.println(partRepository.listParts());
        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    //Metodo responsavel por solicitar ao servidor a quantidade de pecas presente
    public static void repo() {
        try {
            System.out.println(partRepository.repo());
        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    //Metodo responsavel por obter uma copia de uma peca no servidor, para ser usado posteriormente no comando subparts
    public static void partGet(String partCode) {
        try {
            currentPart = partRepository.getPart(partCode);

        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
            
        }
    }

    //Metodo responsavel por adicionar uma nova parte ao repositorio atual
    public static void partAdd(String rawInput) {
        String[] attributes = rawInput.split(", ");
        String code = attributes[0].split(" ")[2];
        String nome =  attributes[1];
        String desc = attributes[2];

        //Verificacao se o cliente esta conectado ao servidor
        if(serverName.equals("")){
            System.out.println("Error: You should bind to one repository");
            return;
        }

        try {
            partRepository.addPart(code, nome, desc, subParts);
        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    //Comando responsavel por imprimir a parte selecionada
    public static void part() {
        try {
            if (currentPart == null) {
                System.out.println("Error: No part is selected");
                return;
            }
            System.out.println(currentPart);
        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    //Comando responsável por adicionar subpartes a lista de subpartes
    public static void subpartsAdd(String quantity) {
        Integer quantity_integer = Integer.parseInt(quantity);
        String infos = quantity + " " + currentPart.getServerName();

        if (currentPart != null && quantity_integer > 0) {
            subParts.put(currentPart.getCode(), infos);
        }
        else if(currentPart == null) {
            System.out.println("Error: You should selected one part");
        }
        else if(quantity_integer <= 0) {
            System.out.println("Error: Quantity should be at least one");
        }
    }

    //Metodo responsavel por limpar a lista de subpartes
    public static void subpartsClean() {
        subParts.clear();
    }

    //Metodo responsavel por remover uma parte da lista de subpartes
    public static void subpartsRemove(String partCode) {
        subParts.remove(partCode);
    }

    //Metodo responsavel por exibir a lista de subpartes
    public static void subparts() {
        if(subParts.isEmpty()){
            System.out.println("Error: Subparts list is empty");
        }
        for (String partCode: subParts.keySet()) {
            String[] infos = subParts.get(partCode).split(" ");
            System.out.println("- " + infos[0] + "x " + partCode + " from " + infos[1]);
        }
    }
}
