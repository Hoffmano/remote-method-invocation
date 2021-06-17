/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

  Essa classe e responsavel por implementar o objeto da peca.
------------------------------------------------------------------------------------------------------------ */
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Part implements Serializable  {

  private String code;
  private String name;
  private String description;
  private Map<String, String> subParts;
  private String serverName;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Map<String, String> getSubParts() {
    return subParts;
  }

  public String getServerName() {
    return serverName;
  }

  public Part(String code, String name, String description, HashMap<String,String> subParts, String serverName){
    this.code = code;
    this.name = name;
    this.description = description;
    this.serverName = serverName;
    this.subParts = subParts;
  }

  @Override
  public String toString(){
    String result = "";

    result += "Code: " + this.code + "\n";
    result += "Name: " + this.name + "\n";
    result += "Server: " + this.serverName + "\n";
    result += "Description: " + this.description + "\n";
    result += "Sub parts:\n";
        
    for (String subPartCode: this.subParts.keySet()) {
      String[] infos = this.subParts.get(subPartCode).split(" ");
      result += "  - " + infos[0] + "x " + subPartCode + " from " + infos[1] + "\n";
    }

    return result;  
  }


}
