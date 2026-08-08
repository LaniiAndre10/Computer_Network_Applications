package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * This is the Server Handler class, which will be responsible to containing the functionalitu for our server, which will communicate with our Putty Client.
 * @Author: LaniiAndre10.
 * @version: 1.0.
 */

public class Server_Handler implements Runnable{
	
	//1. define the instance variables//
	
	private int Portnumber;
	private ServerSocket ServerConnection = null;
	
	//2. use the default constructor//
	
	//3.Collect the information from the user//
	public void GetInfo() {
		
		System.out.println("Hi, Enter the port you want your server to listen to for connection!");
		Scanner Readin = new Scanner(System.in);
		this.Portnumber = Readin.nextInt();
		//use that newline command to take in the new line left after the consumption of the number//
		Readin.nextLine();
		Readin.close();
		
	}
	

	@Override
	public void run() {
		
		//4. run the serversocket to listen for connections//
		try {
			
			System.out.println("Mission Control Awaiting Connection!");
			ServerConnection = new ServerSocket(Portnumber);
			//4.1. Create a traditional socket out of the serversocket that you will use as a communication medium with the client.//
			Socket MiddlemanSocket = ServerConnection.accept();
			
			//4.2 then create the Input and output streams you will need to use for communication in between client and server.//
			BufferedReader PackageReader = new BufferedReader(new InputStreamReader(MiddlemanSocket.getInputStream()));
			PrintWriter PackageSender = new PrintWriter(MiddlemanSocket.getOutputStream(),true); //enable auto flush//
			
			//4.3: Server and Client Communications//
			
			//SCENARIO 1: Pre-Launch Checkout//
			
			PackageSender.println("[UTC 20:15:01][ACK][SYS]: LINK_ESTABLISHED. ID=ORION_01"); //meaning: Acknowledges client connection and assigns ID.//
			
			//The client must respond with: [MET 00:00:00][CMD][SYS]: INIT_SEQUENCE_ALPHA, Meaning: Requests connection and begins initial handshake. //
			System.out.println(PackageReader.readLine());
			//then the client sends the data again: [MET 00:00:02][TLM][ECLSS]: O2_PRES=101.3kPa, CABIN_TEMP=21.5C, Meaning: Sends cabin environment data.//
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 20:15:03][ACK][ECLSS]: ENV_NOMINAL. CONTINUE_CHECKOUT"); // Meaning: Confirms cabin environment is safe. //
			
			//the client then sends the data once more: [MET 00:00:05][TLM][PROP]: FUEL_STAGE_1=100%, OXID_STAGE_1=100%, Meaning: Reports propulsion tanks are completely full.//
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 20:15:06][CMD][PROP]: GO_FOR_PRESSURIZATION"); // Meaning: Commands the client to pressurize fuel tanks. //
			
			//SCENARIO 2: Standard Flight Operations//
			
			//The client Streams regular health telemetry, and the server issues trajectory adjustments//
			
			//The clients then sends data again: [MET 02:14:22][TLM][GNC]: POS_X=421.3, POS_Y=120.4, POS_Z=-89.1, VEL=7.66km/s, Meaning: Sends current orbital coordinates and speed. //
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 22:29:23][ACK][GNC]: TELEMETRY_LOGGED. TRAJECTORY_VALID"); // Meaning: Meaning: Confirms coordinates match flight plan tracking. //
			
			//the clients responds with more data: [MET 02:15:00][TLM][PWR]: SOLAR_ARRAY_EFF=94%, BATT_CHARGE=88% ,Meaning: Reports power and battery health. //
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 22:30:02][CMD][GNC]: BURN_TIG_02_15_30, DUR=4.2s, V_DELTA=+0.02km/s"); // Meaning: Commands a 4.2-second engine burn at a specific time. //
			
			//SCENARIO 3: Anomaly and Emergency Handling//
			
			//The client Sends data: [MET 14:05:12][TLM][NAV]: WARN_STAR_TRACKER_BLINDED , Meaning: Sensor is temporarily unable to navigate using stars. //
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 10:20:13][CMD][NAV]: SWITCH_TO_GYRO_BACKUP"); //Meaning: Tells spacecraft to ignore star tracker and use gyroscopes.//
			
			//Client Replies: [MET 14:05:15][TLM][PWR]: CRIT_BATTERY_CELL_3_THERMAL_RUNAWAY, TEMP=145C, Meaning: Dangerous overheating detected in a battery component. //
			System.out.println(PackageReader.readLine());
			
			PackageSender.println("[UTC 10:20:16][CMD][PWR]: ISOLATE_BATT_CELL_3, ENTER_SAFE_MODE"); //Meaning: Commands immediate shutdown of failing battery to prevent fire. //
			
			//4.4: Close the connection, Lose client connection//
			PackageReader.close();
			PackageSender.close();
			
			MiddlemanSocket.close();
			
			
			
		} catch (IOException e) {
			
			System.out.println("Failed to Form a connection with Rover. Binding Timeout!"); 
			e.printStackTrace();
			
		}
		
	}
	
	

}
