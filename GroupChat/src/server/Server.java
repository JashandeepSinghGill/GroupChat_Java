/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author JashanGill
 *
 */
public class Server{
	
	static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	

	public static void main(String[] args) {

		try {
			
		ServerSocket server = new ServerSocket(5555);
		System.out.println("Server Up and Running :)");
		
		while(true) {
			
				ClientHandler client = new ClientHandler(server.accept(),clients);
				clients.add(client);
				Thread t = new Thread(client);
				t.start();
								
		}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
