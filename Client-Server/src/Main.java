/*
 * This is the main class that will serve as the facilitation class for our Server class.
 * @Author: LaniiAndre10.
 * @Version: 1.0.
 */

import model.server.Server_Handler;

public class Main {

	public static void main(String[] args) {
		
		//ensure that Your Putty client (Rover) is connected to Localhost as hostname., and select other to connect ( Not SSH or Serial )//

		Server_Handler Mission_Control = new Server_Handler();
		
		Mission_Control.GetInfo(); //get the port number that Rover will connect to / the port number that Mission control will listen on for connections//
		Mission_Control.run();
		
	}

}
