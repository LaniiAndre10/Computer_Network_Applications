/*
 * This is a class which will facilitate the functionality of the SMTP mail class.
 * @author: Lanii10.
 * @version: 1.0.
 */

import smtp.client.Client_SMTP;

public class Main {

	public static void main(String[] args) {
		
		//instantiate the client |  We will use Papercut SMTP to simulate the Server on the other end of the communication//
		Client_SMTP SMTP_MiddleMan = new Client_SMTP();
		
		//Gather the requirements//
		SMTP_MiddleMan.TakeInputs(); // Remember to use netstat -ano to find the open ports first, and confirm with Papercut, on which port we will use, the default = 25 on PaperCut//
		
		//then send the mail//
		SMTP_MiddleMan.run();
		
	}

}
