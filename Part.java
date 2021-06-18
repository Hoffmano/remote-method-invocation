/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

  Essa classe e responsavel por implementar o objeto da peca.
------------------------------------------------------------------------------------------------------------ */
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Part implements Serializable  {

  //Codigo da peca
  private String code;
  //Nome da peca
  private String name;
  //Descricao da peca
  private String description;
  //Conjunto de subpartes da parte
  private LinkedList<Part> subParts = new LinkedList<Part>();
  //Nome do servidor em que a peca esta localizada
  private String serverName;
  //Quantidade da peca, utilizado para representar o numero de pecas quando usada como subpeca
  private int number;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public LinkedList<Part> getSubParts() {
    return subParts;
  }

  public void setSubParts(LinkedList<Part> subParts) { this.subParts = subParts; }

  public String getServerName() {
    return serverName;
  }

  public int getNumber() { return number; }

  public void setNumber(int number) { this.number = number; }

  public Part(String code, String name, String description, String serverName){
    this.code = code;
    this.name = name;
    this.description = description;
    this.serverName = serverName;
    this.number = 1;
  }

  public String subpartsToString(){
    String result = "";
    if(subParts.isEmpty()){
      result += "  - This is a primary Part";
    }
    else{
      for (int i = 0; i < subParts.size(); i++) {
        Part currentPart = subParts.get(i);
        result += "  ("+i+") - " + currentPart.getNumber() + "x " + currentPart.getCode() + " from " + currentPart.getServerName() + "\n";
      }
    }
    return  result;
  }

  @Override
  public String toString(){
    String result = "";

    result += "Code: " + this.code + "\n";
    result += "Name: " + this.name + "\n";
    result += "Server: " + this.serverName + "\n";
    result += "Description: " + this.description + "\n";
    result += "Sub parts:\n";
    result += subpartsToString();

    return result;  
  }


}
