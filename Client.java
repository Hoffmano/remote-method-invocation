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
    public static LinkedList<Part> subParts = new LinkedList<Part>();

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
                    else if(input.length == 2 && input[1].equals("populate")) {
                        repoPopulate();
                    }
                    else if(input.length == 2 && input[1].equals("help")) {
                        repoHelp();
                    }
                    else if(input.length == 1) {
                        repo();
                    }
                    else System.out.println("Error: This command was not recognized");
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
                    else if(input.length == 2 && input[1].equals("name")) {
                        partName();
                    }
                    else if(input.length == 2 && input[1].equals("description")) {
                        partDescription();
                    }
                    else if(input.length == 2 && input[1].equals("help")) {
                        partHelp();
                    }
                    else if (input.length == 1) {
                        part();
                    }
                    else System.out.println("Error: This command was not recognized");
                }

                else if(input[0].equals("subparts")) {
                    if(input.length == 3 && input[1].equals("add")) {
                        subpartsAdd(input[2]);
                    }
                    else if(input.length == 2 &&input[1].equals("clean")) {
                        subpartsClean();
                    }
                    else if(input.length == 3 && input[1].equals("remove")) {
                        subpartsRemove(Integer.parseInt(input[2]));
                    }
                    else if(input.length == 2 && input[1].equals("help")) {
                        subpartsHelp();
                    }
                    else if (input.length == 1){
                        subparts();
                    }
                    else System.out.println("Error: This command was not recognized");
                }

                else {
                    System.out.println("Error: This command was not recognized");
                    generalHelp();
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

    //Metodo responsavel por solicitar ao servidor a lista de partes
    public static void repoPopulate() {
        try {
            partRepository.populate();
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

    public static void partName() {
        try {
            if (currentPart == null) {
                System.out.println("Error: No part is selected");
                return;
            }
            System.out.println("Part Name: " + currentPart.getName());
        } catch (Exception e) {
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    public static void partDescription() {
        try {
            if (currentPart == null) {
                System.out.println("Error: No part is selected");
                return;
            }
            System.out.println("Part Name: " + currentPart.getDescription());
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
            Part addedPart = currentPart;
            addedPart.setNumber(quantity_integer);
            subParts.add(addedPart);
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
    public static void subpartsRemove(int partCode) { subParts.remove(partCode); }

    //Metodo responsavel por exibir detalhes das subpartes presente na lista de subpartes
    public static void partSubparts() {
        try{
            LinkedList<Part> subPartsList = currentPart.getSubParts();
            for(int i = 0; i<subPartsList.size(); i++){
                System.out.println("Index: "+ i);
                System.out.println("Number of Parts: " + subPartsList.get(i).getNumber());
                System.out.println(subPartsList.get(i).toString());
                System.out.println("---------------------------------");
            }
        }
        catch (Exception e){
            System.out.println("Server error:");
            System.out.println(e);
        }
    }

    //Metodo responsavel por exibir a lista de subpartes
    public static void subparts() {
        if(subParts.isEmpty()){
            System.out.println("Error: Subparts list is empty");
        }
        else{
            String result = "";
            if(subParts.isEmpty()){
                result += "  - This is a primary Part";
            }
            else{
                for (int i = 0; i < subParts.size(); i++) {
                    Part currentPart = subParts.get(i);
                    result += "  ("+i+") - " + currentPart.getNumber() + "x " + currentPart.getCode() + " from " + currentPart.getNumber() + "\n";
                }
            }
            System.out.println(result);
        }
    }

    //HELP COMMANDS
    //Estes comandos ajudam a orientar o usuario, indicando quais sao os comandos presentes em cada categoria

    public static void repoHelp(){
        String help = "Comandos relacionados ao repositorio:\n" +
                "\n" +
                "- `repo bind \\<serverName>`\n" +
                "  - Conecta o cliente com um determinado repositorio\n" +
                "- `repo parts`\n" +
                "  - Lista os codigos das pecas presentes no repositorio corrente\n" +
                "- `repo populate`\n" +
                "  - Popula o repositorio corrente com 3 pecas simples\n" +
                "- `repo help`\n" +
                "  - Exibe os comandos disponiveis\n" +
                "- `repo`\n" +
                "  - Mostra dados e estatisticas do repositorio corrente";
        System.out.println(help);
    }

    public static void partHelp(){
        String help = "Comandos relacionados as pecas:\n" +
                "\n" +
                "- `part get \\<partCode>`\n" +
                "  - Busca uma determinada peca no repositorio corrente e caso encontre, a torna a peca corrente\n" +
                "- ``part add \\<partCode>, \\<partName>, \\<partDescription>``\n" +
                "  - Adiciona uma nova peca no repositorio corrente\n" +
                "- ``part subparts``\n" +
                "  - Lista as subpartes presentes na peca corrente\n" +
                "- ``part name``\n" +
                "  - Exibe o nome da peca corrente  \n" +
                "- ``part description``\n" +
                "  - Exibe a descricao da peca corrente  \n" +
                "- ``part help``\n" +
                "  - Exibe os comandos disponiveis    \n" +
                "- ``part``\n" +
                "  - Mostra os dados da peca corrente";
        System.out.println(help);
    }

    public static void subpartsHelp(){
        String help = "Comandos relacionados a lista de subpecas:\n" +
                "\n" +
                "- ``subparts add \\<quantity>``\n" +
                "  - Adiciona `n` quantidades da peca corrente numa lista de subpecas\n" +
                "- `subparts clean`\n" +
                "  - Limpa a lista de subpecas\n" +
                "- ``subparts remove \\<partCode>``\n" +
                "  - Remove uma determinada peca da lista de subpecas\n" +
                "- `subparts help`\n" +
                "  - Limpa a lista de subpecas\n" +
                "- ``subparts``\n" +
                "  - Lista todas as pecas presentes na lista de subpecas";
        System.out.println(help);
    }

    public static void generalHelp(){
        System.out.println("Avaliable Commands:");
        System.out.println("- part");
        System.out.println("- subparts");
        System.out.println("- repo");
        System.out.println("- quit");
    }
}
