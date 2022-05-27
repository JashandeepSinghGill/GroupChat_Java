package utilities;
/**
 * Created on Sep 15, 2010
 *
 * Project: demo06-ThreadedChatServerExample 
 */

import java.util.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;

/**
 * @author dwatson, kitty
 * @version 1.0
 */
public class InputListener implements Runnable
{
	//Attributes
	private int 							listenerNumber;
	private Socket							socket;
	private ObjectInputStream				ois;
	private List<PropertyChangeListener> 	listeners = new ArrayList<>();
	
	// Constructors
	/**
	 * @param socket the socket being monitored
	 * @param obs class to be notified when something changes
	 */
	public InputListener(Socket socket, PropertyChangeListener obs)
	{
		this.listenerNumber = 0;
		this.socket = socket;
		this.addObserver(obs);
	}
	/**
	 * @param listenerNumber number assigned to this listener
	 * @param socket the socket being monitored
	 * @param obs class to be notified when something changes
	 */
	public InputListener(int listenerNumber, Socket socket, PropertyChangeListener obs)
	{
		this.listenerNumber = listenerNumber;
		this.socket = socket;
		this.addObserver(obs);
	}
	
	private void addObserver(PropertyChangeListener obs)
	{
		this.listeners.add((PropertyChangeListener) obs);
	}
	
	//Getter and Setter Methods
	/**
	 * @return the listenerNumber
	 */
	public int getListenerNumber()
	{
		return listenerNumber;
	}
	/**
	 * @param listenerNumber the listenerNumber to set
	 */
	public void setListenerNumber(int listenerNumber)
	{
		this.listenerNumber = listenerNumber;
	}
	
	//Operational Methods
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{		
		try
		{
			ois = new ObjectInputStream(socket.getInputStream());
			
			while(true)
			{

				Object o = ois.readObject();
				notifyListeners(o);
				
				Message m = (Message) o;
				if (m.getMsg().compareTo("has disconnected.") == 0)
				{
					ois.close();
					socket.close();
				}
					
			}
		}
		catch (SocketException e)
		{
			// not all exceptions are errors, 
			// just handle them gracefully!

		}
		catch (EOFException e)
		{
			// not all exceptions are errors, 
			// just handle them gracefully!

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private void notifyListeners(Object o)
	{
		for( PropertyChangeListener listener : listeners )
		{
			
			listener.propertyChange(new PropertyChangeEvent(this,new Integer(this.getListenerNumber()).toString(), null, o));
		}
	}
}

