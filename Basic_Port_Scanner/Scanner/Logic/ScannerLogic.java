package Scanner.Logic;

import java.io.IOException;
import java.net.Socket;

public class ScannerLogic {

    //1. Instance variables//
    private String Hostname;

    //2. create the constructor//
    public ScannerLogic(String Hostname) {
        this.Hostname = Hostname;
    }

    //3. Getter for the hostname//
    public String getHostname() {
        return Hostname;
    }

    //4. Create a run method containing the port scanning logic//
    public void RunScanner(){

        for( int i=1; i<=65535; i++){

            try(Socket NetSocket = new Socket(Hostname, i)){

                System.out.println("Successfully Connected to host: " + Hostname);
                System.out.println("Localhost Port Number: "+NetSocket.getLocalPort());
                System.out.println("Remotehost Port Number: "+NetSocket.getPort());

            }
            catch(IOException e) {

                System.out.println("Error connecting to " + Hostname + " at port: "+i);
                //e.printStackTrace();
            }

        }
    }

    //5. Create a function that will print the IP address of the Localhost//
    public void ShowLocalIP() {

        int PreviousConnectedtoPort = 59996;

        try(Socket NetSocket = new Socket(Hostname,PreviousConnectedtoPort)){

            System.out.println("______________________________________________________________________");
            System.out.println("Successfully connected to the port via previously connected port: "+PreviousConnectedtoPort);
            System.out.println(" Local Computer's IP: "+ NetSocket.getInetAddress());

        }
        catch(IOException e) {

            //e.printStackTrace();
            System.out.println("Failed to connect to the socket!");

        }


    }

}
