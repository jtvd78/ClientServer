package client.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ssl.SSLSocket;

import client.Server;
import shared.net.Message;

public class Connection {
	
	private Socket socket;
	private ObjectOutputStream oos;
	private boolean connected = true;
	private MessageHandler messageHandler;
	
	public Connection(Socket socket, Server s) throws IOException {
		this.socket = socket;
		this.messageHandler = new MessageHandler(s);
		oos = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void start(){
		new Thread(new RequestListener()).start();
	}

	public void sendMessage(Message m){
		
		switch(m.type){
		case ADD_USER:
		case CORRECT_LP:
		case GET_FILELIST:
		case INCORRECT_LP:
		case INCORRECT_VERSION:
		case LOGIN:
		case PM:
		case REMOVE_USER:
		case SEND_FILELIST: m.setID(messageHandler.getNextID());
							messageHandler.addPendingMessage(m);
			break;
			
			
		case CHAT:
			break;
		default:
			break;
		}
		
		
		try {
			oos.writeObject(m);
			oos.flush();
		}catch(SocketException e){ 
			//Socket Closed
			close();
			System.out.println("Server Disconnected");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close(){
	//	Client.getClient().removeServer(s);
		connected = false;
		try {
			oos.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleMessage(Message m){
		messageHandler.handleMessage(m);
	}
	
	private class RequestListener implements Runnable{

		public void run() {
			
			ObjectInputStream ois = null;
			
			try {
				ois = new ObjectInputStream(socket.getInputStream()); 
				
				while(connected){
					Message m = (Message)ois.readObject();
					handleMessage(m);
				}
				
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SocketException e){	
				close();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					ois.close();
				} catch (IOException e) {
					//Do Nothing. There is nothing you can do. 
					e.printStackTrace();
				}
			}
		}
	}	
}