package client;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import client.settings.Settings;
import client.settings.SettingsLoader;
import client.ui.Frame;


public class Client {

	Frame f;
	Settings settings;
	private ServerManager serverManager;
	private static Client mainClient;
	
	public static void main(String[] args){
		new Client().start();
	}
	
	private void start(){
		
		//Init Stuff
		mainClient = this;
		settings = new SettingsLoader().loadSettings();
		serverManager = new ServerManager();
		
		//UI
		setNativeUI();
		f = new Frame();
	}
	


	public void setNativeUI(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public Settings getSettings(){
		return settings;
	}

	public Frame getFrame(){
		return f;
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