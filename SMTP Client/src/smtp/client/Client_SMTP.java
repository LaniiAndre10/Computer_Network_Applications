package smtp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * This is a class which contains the logic needed for use to communicate with the SMTP server, and again communicate or transmit the data in between the nodes.
 * @author: Lanii10.
 * @version: 1.0.
 */

public class Client_SMTP implements Runnable{
	
	//1. Create the instance variables//
	
	private String ServerID;
	private int Portnum = 0;
	private String Sender_Name;
	private String Receipient_Name;
	private String Email_Contents;
	
	//create a reference variable of the traditional socket which will serve as the communication medium//
	private Socket ClientSocket = null;
	
	//2. Create a function to retrieve the data before we could instantiate the class//
	
	public void TakeInputs() {
		
		Scanner DataCollector = new Scanner(System.in);
		
		//2.1 Take in the socket data first//
		System.out.println("Enter the IP address of the server you want to communicate with: Use Localhost to communicate with your PC");
		this.ServerID = DataCollector.nextLine();
		
		System.out.println("Enter the port Number you want to use to communicate with the Server!");
		this.Portnum = DataCollector.nextInt();
		DataCollector.nextLine(); // to fix the ommited New Line by the NextInt function//
		//2.2 Take in the Email data//
		System.out.println("Enter Your Sender Name:");
		this.Sender_Name = DataCollector.nextLine();
		
		System.out.println("Enter the Reciepient Name:");
		this.Receipient_Name = DataCollector.nextLine();
		
		System.out.println("Enter the Message you want to send to "+this.Receipient_Name+" .");
		this.Email_Contents = DataCollector.nextLine();
		
		//2.3 Close the DataCollector//
		DataCollector.close();
		
	}
	
	//3. The default constructor will be used, since we cannot push the functionality inside the constructor//

	@Override
	public void run() {
		
		//Use Multithreading to allow tasks to run concurrently//
		
		//3.1 Create the socket instance using the gathered socket data//
		
			try {
				
				this.ClientSocket = new Socket(ServerID,Portnum);
				
				//3.2 - When the connection is formed, it is time to use the communication streams to send mail:  BufferedReader(In) and the Printwriter (out)//
				
				BufferedReader MailReceiver = new BufferedReader( new InputStreamReader(ClientSocket.getInputStream()));
				PrintWriter MailSender = new PrintWriter(ClientSocket.getOutputStream(),true); //With Auto flush//
				
				//Client is ready to send and receive mails//
				
				System.out.println(MailReceiver.readLine()); // Read what the Server is sending out to your Client Upon connection//

				MailSender.println("HELO localhost"); //The standard Requirements to say that You are initiating an SMTP session with the Server//
				System.out.println(MailReceiver.readLine()); 

				MailSender.println("MAIL FROM:<" + this.Sender_Name + "@example.com>"); //A standardized way to state the sender//
				System.out.println(MailReceiver.readLine());

				MailSender.println("RCPT TO:<" + this.Receipient_Name + "@example.com>"); //A standardized way to state the Receiver//
				System.out.println(MailReceiver.readLine());

				MailSender.println("DATA"); //A standardized way to tell the machine that you are now sending data//
				System.out.println(MailReceiver.readLine());

				MailSender.println("Subject: Test Mail"); //A standardized way to tell the server that this is the subject of the mail//
				MailSender.println();
				MailSender.println(this.Email_Contents); //the email contents//
				MailSender.println(".");
				System.out.println(MailReceiver.readLine());

				MailSender.println("QUIT"); //Email Send, now quit//
				System.out.println(MailReceiver.readLine());

				
			} catch (UnknownHostException e) {
				
				//print the failed to connect to Server error//
				System.out.println("Failed to Connect to SMTP Server!");
				e.printStackTrace();
				
			} catch (IOException e) {
				
				//Print the failed to connect to port error//
				System.out.println("Failed to Connect to port!");
				e.printStackTrace();
				
			}
		
	}
	

}
