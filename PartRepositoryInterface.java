/* ------------------------------------------------------------------------------------------------------------
                                        Exercicio Programa 1 - DSID

Feito Por:
- Caio Rodrigues Gomes              - 11208012
- Eric Batista da Silva             - 10783114
- Gabriel Hoffman Silva             - 10783250
- Julia Cristina de Brito Passos    - 10723840

    Interface do repositorio de partes.
------------------------------------------------------------------------------------------------------------ */
import java.rmi.*;
import java.util.HashMap;
import java.util.LinkedList;

public interface PartRepositoryInterface extends Remote {
    void populate() throws RemoteException;
    String listParts() throws RemoteException;
    String repo() throws RemoteException;
    Part getPart(String partCode) throws RemoteException;
    String addPart(String code, String name, String description, LinkedList<Part> subParts) throws RemoteException;
    
}
