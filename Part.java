import java.util.Map;
import java.util.HashMap;

public class Part {
  public String code;
  public String name;
  public String description;
  public Map<String, String> subParts;
  public String serverName;

  public Part(String code, String name, String description, HashMap<String,String> subParts, String serverName) {
    this.code = code;
    this.name = name;
    this.description = description;
    this.serverName = serverName;
    this.subParts = subParts;
  }
}
