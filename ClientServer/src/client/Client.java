package client;

import client.settings.Settings;
import client.settings.SettingsLoader;
import client.ui.Frame;


public class Client {

	Frame frame;
	Settings settings;
	private ServerManager serverManager;
	private static Client mainClient;
	
	public static void main(String[] args){
		
		//Make it look nice
		com.hoosteen.graphics.Tools.setNativeUI();
		
		//Start up the program
		
		mainClient = new Client();
		mainClient.start();
	}
	
	private void start(){
		
		//Init Stuff
		settings = new SettingsLoader().loadSettings();
		serverManager = new ServerManager();
		
		//UI
		frame = new Frame();
	}
	
	public Settings getSettings(){
		return settings;
	}

	public Frame getFrame(){
		return frame;
	}
	
	public static Client getClient() {
		return mainClient;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public void requestExit(){
		System.exit(0);
	}
}