package client;

import java.util.Set;

import com.hoosteen.settings.Settings;

import client.settings.ClientSettings;
import client.ui.ClientFrame;
import client.ui.node.Server;


public class ClientStart {

	ClientFrame frame;
	ClientSettings settings;
	private static ClientStart mainClient;
	
	public static void main(String[] args){
		
		//Make it look nice
		com.hoosteen.Tools.setNativeUI();
		
		//Start up the program
		
		mainClient = new ClientStart();
		mainClient.start();
	}
	
	private void start(){	
		
		settings = Settings.loadSettings(ClientSettings.class, ClientSettings.programName, shared.Settings.version);
		
		//UI
		frame = new ClientFrame();
	}
	
	public ClientSettings getSettings(){
		return settings;
	}

	public ClientFrame getFrame(){
		return frame;
	}
	
	public void PM(String message, Server s, int fromUID){
		frame.PM(message, s, fromUID);
	}
	
	public static ClientStart getClient() {
		return mainClient;
	}

	public void requestExit(){
		System.exit(0);
	}

	public void addServer(Server s) {
		frame.addServer(s);
	}

	public Set<Server> getServerList() {
		return frame.getServerList();
	}

	public void removeServer(Server server) {
		frame.removeServer(server);
	}
}