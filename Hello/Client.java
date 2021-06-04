import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    public static void main(String args[ ]) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String input = "";

        Hello object = (Hello)Naming.lookup("rmi://localhost/Server");

        try {
            while (!input.equalsIgnoreCase("exit")) {
                input = bufferedReader.readLine();

                if(input.equals("hi")) {
                    System.out.println(object.sayHello());
                }
                else if(input.equals("thx")) {
                    System.out.println(object.sayThanks());
                }
                else if(input.equals("bye")) {
                    System.out.println(object.sayGoodbye());
                }
                else {
                    System.out.println("command is undefined");
                }
            }
        } catch(Exception e) {
            System.out.println("Client erro"+ e.getMessage());
        }
        System.exit(0);
    }
}
