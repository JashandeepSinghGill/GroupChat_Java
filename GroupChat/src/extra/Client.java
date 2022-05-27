/**
 * 
 */
package extra;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author JashanGill
 *
 */
public class Client extends Thread {

	private DataInputStream  cRead;
	private DataOutputStream cWrite;
	
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}
	
	
	@Override
	public void run() {
		try {
			Socket client = new Socket("localhost",5555);
			
			System.out.print("Enter your name: ");
			Scanner sc  = new Scanner(System.in);
			String name = sc.next();
			
			cWrite = new DataOutputStream( client.getOutputStream() );
			cWrite.writeUTF(name);
			
			cRead = new DataInputStream( client.getInputStream() );			
				
				String inMsg = cRead.readUTF();
				System.out.print(inMsg);
				
			cWrite.close();
			cRead.close();
			} catch (IOException e) {
			System.out.println("Server not responding.");
			} 
		
	}

	
}
