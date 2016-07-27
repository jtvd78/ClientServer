package client;

import java.util.Set;

import client.settings.Settings;
import client.settings.SettingsLoader;
import client.ui.ClientFrame;
import client.ui.node.Server;


public class ClientStart {

	ClientFrame frame;
	Settings settings;
	private static ClientStart mainClient;
	
	public static void main(String[] args){
		
		//Make it look nice
		com.hoosteen.graphics.Tools.setNativeUI();
		
		//Start up the program
		
		mainClient = new ClientStart();
		mainClient.start();
	}
	
	private void start(){
		
		//Init Stuff
		settings = new SettingsLoader().loadSettings();
		
		//UI
		frame = new ClientFrame();
	}
	
	public Settings getSettings(){
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