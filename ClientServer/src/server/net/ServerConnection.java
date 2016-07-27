package server.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ssl.SSLSocket;

import client.ui.node.Server;
import client.ui.node.User;
import server.ServerStart;
import server.ServerUser;
import server.ui.Console;
import shared.net.Message;

public class ServerConnection{
	
	Socket socket;
	ObjectOutputStream oos;
	boolean connected = true;
	ServerMessageHandler messageHandler;
		
	ServerUser u; //Client
	int connectionNumber;

	public ServerConnection(Socket sock, int connectionNumber) throws IOException{
		this.messageHandler = new ServerMessageHandler(this);
		this.connectionNumber = connectionNumber;
		this.socket = sock;
		oos = new ObjectOutputStream(sock.getOutputStream());
	}
	
	public void start() {
		new Thread(new RequestListener()).start();
	}
	
	public void sendMessage(Message m){
		
		try {
			oos.writeObject(m);
			oos.flush();
		}catch(SocketException e){ 
			//Client Closed
			close();
			Console.out.println("Server Disconnected");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		connected = false;
		ServerStart.mainServer.removeUser(u);
		try {
			oos.close();
			socket.close();
		} catch (IOException e) {
			//Do Nothing. There is nothing you can do. 
		}
	}	
	
	private void handleMessage(Message m){
		messageHandler.handleMessage(m);
	}
	
	class RequestListener implements Runnable{

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
				close();
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

	public ServerUser createUser(String name) {
		return (u = new ServerUser(name, this,connectionNumber));
	}	
}