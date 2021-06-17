/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

    Esta classe e responsavel por implementar o repositorio de pecas, que por sua vez, e responsavel por
armazenar as pecas e retornar uma copia quando solicitado pelo cliente.
------------------------------------------------------------------------------------------------------------ */
import java.rmi.*;
import java.rmi.server.*;
import java.util.Map;
import java.util.HashMap;

public class PartRepository extends UnicastRemoteObject implements PartRepositoryInterface {
    private Map<String, Part> parts;
    public String serverName;

    //Construtor padrao do repositorio de pecas
    //OBS: O repositorio e sempre inicializado com 3 pecas exemplo
    public PartRepository(String serverName) throws RemoteException{
        super();
        this.parts = new HashMap<String, Part>();
        this.serverName = serverName;

        Part part1 = new Part("p1", "part 1", "description part 1", new HashMap<String, String>(), serverName);
        Part part2 = new Part("p2", "part 2", "description part 2", new HashMap<String, String>(), serverName);
        Part part3 = new Part("p3", "part 3", "description part 3", new HashMap<String, String>(), serverName);

        this.parts.put("p1", part1);
        this.parts.put("p2", part2);
        this.parts.put("p3", part3);
    }

    //Comando responsavel por listar as partes presentes no repositorio
    public String listParts() {
        System.out.println("Command: listParts");
        String result = "";

        for (String partCode : this.parts.keySet()) {
            result += "- " + partCode + "\n";
        }

        return result.trim();
    }

    //Comando responsavel por retornar um objeto da parte
    public Part getPart(String partCode) {
        System.out.println("Command: getPart");
        System.out.println("Param: " + partCode);
        Part part = this.parts.get(partCode);
        if (part == null) {
            throw new RuntimeException("Part not found");
        }
        return part;
        
    }

    //Metodo responsavel por adicionar novas partes ao repositorio
    public String addPart(String code, String name, String description, HashMap<String, String> subParts) {
        System.out.println("Command: addPart");
        System.out.println("Param: " + code + " , " + name + " , " + description);
        this.parts.put(code, new Part(code, name, description, subParts, serverName));
        return "";
    }

    //Metodo responsavel por listar todas as pecas presentes no repo
    public String repo() {
        System.out.println("Command: repo");
        String result = "";
        Integer quantityOfParts = this.parts.size();

        result += "repository name: " + serverName + "\n";
        result += "quantity of parts: " + quantityOfParts;

        return result;
    }
}
