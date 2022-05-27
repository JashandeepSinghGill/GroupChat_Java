/**
 * 
 */
package server;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import utilities.InputListener;
import utilities.Message;


/**
 * @author JashanGill
 * @param <clientHandler>
 *
 */
public class ClientHandler extends Thread implements PropertyChangeListener{
	
	private ObjectInputStream  cRead;
	private ObjectOutputStream cWrite;
	private Socket client;
	private int id;
	private ArrayList<ClientHandler> clients;
	private InputListener			listner;

	public ClientHandler(Socket client, ArrayList<ClientHandler> clients) throws IOException{
		this.client = client;
		this.clients = clients;
		this.id = (int) (Math.random()*100);
		
		this.cWrite = new ObjectOutputStream(client.getOutputStream());

	}

	public int getI() {
		return (int)this.id;
	}
	
	
	private void msg(Message message) throws IOException {
		cWrite.writeObject(message);
	}


	@Override
	public void run() {
		listner = new InputListener(id,client,this);
		Thread t = new Thread(listner);
		t.start();

			try {
				
				Message m = new Message("*","You have joined");
				cWrite.writeObject(m);
				
				while(client.isConnected());
				client.close();
				cWrite.close();
				
			} catch (IOException e) {

			}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String lis = evt.getPropertyName();
		Message m = (Message)evt.getNewValue();
		
		
			for (ClientHandler clientHandler : this.clients) {
				
				try {
					if((clientHandler.getI() +"").equals(lis) || m.getUser().equals("*")) {
						continue;
					}else {
						clientHandler.msg(new Message("",(m.getUser()+":-"+m.getMsg())));
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	}
}
