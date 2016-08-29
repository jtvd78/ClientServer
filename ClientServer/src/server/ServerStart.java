package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hoosteen.ui.Console;

import server.file.FileHandler;
import server.net.ConnectionController;
import server.net.ServerConnection;
import shared.net.response.ChatResponse;
import shared.net.response.PMResponse;
import shared.net.response.UpdateNameResponse;

public class ServerStart {
	
	FileHandler fileHandler;
	LoginManager loginManager;
	ConnectionController connectionController;
	
	public static ServerStart mainServer;
	
	private ArrayList<ServerUser> userList = new ArrayList<ServerUser>();
	
	private HashMap<ServerConnection, ServerUser> userConnectionMap = new HashMap<ServerConnection, ServerUser>();
	
	public static void main(String[] args){
		new ServerStart().start();
	}
	
	public void start(){
		//Setup Static Server
		mainServer = this;
		
		//Setup Console Window
		Console.init();
		com.hoosteen.Tools.setNativeUI();
		
		//Init login manager
		loginManager = new LoginManager();
		
		//Init FileHandler
		fileHandler = new FileHandler(server.Settings.fileRoot);
		
		//Start ConnectionController
		connectionController = new ConnectionController();
		connectionController.init();
	}
	
	public boolean login(ServerUser u, String username, String password){
		
		return loginManager.login(u, username, password);
	}

	public synchronized void removeUser(ServerUser u) {
		userList.remove(u);
		
		for(ServerUser uu : userList){
			uu.removeUser(u);
		}
		
		Console.out.println(u.getName() + " left the server");
	}
	
	public void sendChatToClients(ChatResponse response){		
		for(ServerUser u : userList){
			u.sendMessage(response);
		}
	}
	
	public void updateUserName(UpdateNameResponse response){		
		for(ServerUser uu : userList){
			uu.sendMessage(response);
		}
	}
	
	public void sendPMToClient(PMResponse response, int uID){
		for(ServerUser u : userList){
			if(u.uID == uID){
				u.sendMessage(response);
			}
		}
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}	

	public void connectionClosed(ServerConnection serverConnection) {
		connectionController.connectionClosed(serverConnection);
		ServerUser user = userConnectionMap.remove(serverConnection);
		removeUser(user);
	}

	public void userConnected(ServerUser u, ServerConnection c) {
		userConnectionMap.put(c, u);
		
		//Add Existing users
		for(ServerUser uu : userList){
			u.addUser(uu);
		}
		
		userList.add(u);
		
		for(ServerUser uu : userList){
			uu.addUser(u);
		}
	}	
}