/*
@author: LaniiAndre.
@project: Just_A_Simple_Port_Scanner.
 */
import Scanner.Logic.ScannerLogic;

public static void main(String[] args) {

    //1. Hardcode the name of the host = Localhost //
    String Hostname = "Localhost";

    //2. Run the Scanner logic to initiate the port scanning //
    ScannerLogic PortScanner = new ScannerLogic(Hostname);

    PortScanner.RunScanner(); // Scan for the ports : 1 to 65 535//
    PortScanner.ShowLocalIP(); //Show the Ip address of the device //

}
