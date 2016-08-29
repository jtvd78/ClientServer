package server.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.hoosteen.ui.Console;

import server.ServerStart;
import shared.net.MessageRequest;
import shared.net.MessageResponse;

public class ServerConnection{
	
	Socket socket;
	int connectionNumber;
	ObjectOutputStream oos;
	boolean connected = true;
	ServerMessageHandler messageHandler;
	
	

	public ServerConnection(Socket sock, int connectionNumber) throws IOException{
		this.messageHandler = new ServerMessageHandler(this);
		this.connectionNumber = connectionNumber;
		this.socket = sock;
		oos = new ObjectOutputStream(sock.getOutputStream());
		new Thread(new RequestListener()).start();
	}
	
	public void sendMessage(MessageResponse response){
		
		try {
			oos.writeObject(response);
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
		ServerStart.mainServer.connectionClosed(this);
		try {
			oos.close();
			socket.close();
		} catch (IOException e) {
			//Do Nothing. There is nothing you can do. 
		}
	}	
	
	private void handleMessage(MessageRequest m){
		messageHandler.handleMessage(m);
	}
	
	class RequestListener implements Runnable{

		public void run() {
			
			ObjectInputStream ois = null;
			
			try {
				ois = new ObjectInputStream(socket.getInputStream()); 
				
				while(connected){
					MessageRequest m = (MessageRequest)ois.readObject();
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

	public int getConnectionNumber() {
		return connectionNumber;
	}
}