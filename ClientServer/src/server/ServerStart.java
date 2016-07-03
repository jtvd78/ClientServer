package server;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.file.FileHandler;
import server.net.ConnectionController;
import server.ui.Console;
import shared.net.Message;

public class ServerStart {
	
	FileHandler fileHandler;
	LoginManager loginManager;
	
	public static ServerStart mainServer;
	
	private ArrayList<ServerUser> userList = new ArrayList<ServerUser>();
	
	public static void main(String[] args){
		new ServerStart().start();
	}
	
	public void start(){
		//Setup Static Server
		mainServer = this;
		
		//Setup Console Window
		Console.init();
		com.hoosteen.graphics.Tools.setNativeUI();
		
		//Init login manager
		loginManager = new LoginManager();
		
		//Init FileHandler
		fileHandler = new FileHandler(client.Settings.fileRoot);
		
		//Start ConnectionController
		ConnectionController cc = new ConnectionController();
		cc.init();
	}
	
	public boolean login(ServerUser u, String username, String password, String version, int ID){
		
		if(!version.equals(client.Settings.version)){
			Hashtable<Integer, String> ht = new Hashtable<Integer, String>();
			ht.put(0, client.Settings.version);
			
			Message m = new Message(Message.Type.INCORRECT_VERSION);
			m.put(client.Settings.version);
			m.setID(ID);
			
			u.sendMessage(m);
			return false;
		}
		
		boolean result = loginManager.login(u, username, password);
		
		if(result){	
			
			//Add Existing users
			for(ServerUser uu : userList){
				u.addUser(uu);
			}
			
			userList.add(u);
			
			for(ServerUser uu : userList){
				uu.addUser(u);
			}
			
						
			
			Console.out.println(u.getName() + " connected to the server");
			
			Message m = new Message(Message.Type.CORRECT_LP);
			m.setID(ID);
			u.sendMessage(m);
		}else{
			Message m = new Message(Message.Type.INCORRECT_LP);
			m.setID(ID);
			u.sendMessage(m);
		}
		
		return result;
	}

	public synchronized void removeUser(ServerUser u) {
		userList.remove(u);
		
		for(ServerUser uu : userList){
			uu.removeUser(u);
		}
		
		Console.out.println(u.getName() + " left the server");
	}
	
	public void sendChatToClients(Message m){		
		for(ServerUser u : userList){
			u.sendMessage(m);
		}
	}
	
	public void updateUserName(String name, int uID){
		Message m = new Message(Message.Type.UPDATE_NAME);
		m.put(name);
		m.put(uID);
		
		for(ServerUser uu : userList){
			uu.sendMessage(m);
		}
	}
	
	public void sendPMToClient(Message m, int uID){
		for(ServerUser u : userList){
			if(u.uID == uID){
				u.sendMessage(m);
			}
		}
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}
}