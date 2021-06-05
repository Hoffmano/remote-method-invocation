import java.net.*;
import java.rmi.*;
import java.util.Map;
import java.util.HashMap;

public class Part {
  private String code;
  private String name;
  private String description;
  private Map<String, Part> subPartsList;

  public Part(String code, String name, String description, HashMap subPartsList) {
    this.code = code;
    this.name = name;
    this.description = description;
    this.subPartsList = subPartsList;
  }
}
