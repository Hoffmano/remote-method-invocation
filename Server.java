/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

    Esta classe e responsavel por implementar o servidor, instanciando um objeto do repositorio de pecas.
------------------------------------------------------------------------------------------------------------ */
import java.rmi.*;

public class Server {
    //Inicia o repositorio de partes
    public static void main (String args [ ]) {
        try {
            PartRepository partRepository = new PartRepository(args[0]);
            Naming.rebind(args[0], partRepository);
            partRepository.populate();

            System.out.println(args[0] + " is ready");
        } catch(Exception e) {
            System.out.println("Error "+ e.getMessage());
        }
    }
}
