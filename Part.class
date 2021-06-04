import java.util.*;

public class Part{

    private int codigo = -1;
    private String nome = NULL;
    private String desc = NULL;
    private ArrayList<Part> subP = new List<>();
    private char prim = 'n';

    public static Part(int codigo, String nome, String desc, ArrayList<Part> subP){

        this.codigo = codigo;
        this.nome = nome;
        this.desc = desc;
        this.subP = subP;

        //this.objUnicos = subP.stream().distinct().collect(Collectors.toList());
        
        if (subP == null){
            prim = 'p';
        }
        else{
            prim = 'a';
        }
    }

    public int getCodigo(){
        return this.codigo;        
    }

    public String getNome(){
        return this.nome;
    }

    public String getDesc(){
        return this.descl;
    }

    public void getPartes(){
        for(Part name:this.subP) {
            System.out.println(name.getNome());
        }
    }


        
}