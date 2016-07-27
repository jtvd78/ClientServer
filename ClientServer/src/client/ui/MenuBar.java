package client.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import client.ClientStart;
import client.SavedServers;
import client.ServerSettings;
import client.ui.dialog.ConnectToServerDialog;
import client.ui.node.Server;

class MenuBar extends JMenuBar{
	
	ClientFrame frame;
	
	JMenu file, saved, edit, server, help;
	
	JMenuItem connect, exit;
	JMenuItem newServer;
	JMenuItem prefs;
	JMenuItem disconnect;
	JMenuItem about;
	
	public MenuBar(ClientFrame frame){
		this.frame = frame;
		
		
		
		//FILE
		file = new JMenu("File");
			connect = new JMenuItem(new ConnectAction());
			exit = new JMenuItem(new ExitAction());
		file.add(connect);
		file.addSeparator();
		file.add(exit);
		add(file);
		
		//SAVED SERVERS
		saved = new JMenu("Saved Servers");
		
			//Get saved Servers, and add them to the JMenu
			SavedServers savedServers = new SavedServers();
			ServerSettings[] servers = savedServers.getSavedServers();
			
			for(ServerSettings ss : servers){
				JMenuItem jmi = new JMenuItem(new SavedServerAction(ss));
				saved.add(jmi);
			}
		
			saved.addSeparator();
			
			newServer = new JMenuItem(new NewServerAction());
			saved.add(newServer);
		
		add(saved);
		
		
		//EDIT
		edit = new JMenu("Edit");
			prefs = new JMenuItem(new PreferencesAction());
		edit.add(prefs);	
		add(edit);
		
		//SERVER
		server = new JMenu("Server");
			disconnect = new JMenuItem(new DisconnectAction());
		server.add(disconnect);
		add(server);
		
		//HELP
		help = new JMenu("Help");
			about = new JMenuItem(new AboutAction());
		help.add(about);
//		help.add(new JMenuItem("Your nickname is " + Client.getClient().getSettings().name));
		add(help);
	}
	
	class PreferencesAction extends AbstractAction{
		
		public PreferencesAction(){
			super("Preferences");
		}

		public void actionPerformed(ActionEvent arg0) {
			client.settings.Settings.showSettingsWindow();
		}
	}
	
	class SavedServerAction extends AbstractAction{
		
		ServerSettings server;
		
		public SavedServerAction(ServerSettings server){
			super(server.name);
			this.server = server;
		}

		public void actionPerformed(ActionEvent e) {
			new ConnectToServerDialog(ClientStart.getClient().getFrame(),server).setVisible(true);
		}
	}
	
	class NewServerAction extends AbstractAction{
		
		public NewServerAction(){
			super("New Saved Server");
		}
		
		public void actionPerformed(ActionEvent e){
			
		}
		
	}
	
	class DisconnectAction extends AbstractAction{
		
		public DisconnectAction(){
			super("Disconnect");
		}
		
		public void actionPerformed(ActionEvent e) {
			
			Server currentServer = ClientStart.getClient().getFrame().getCurrentServer();
			
			if(currentServer != null){
				ClientStart.getClient().getFrame().removeServer(currentServer);
			}
		}
	}
	
	class ConnectAction extends AbstractAction{
		
		public ConnectAction(){
			super("Connect");
		}
		
		public void actionPerformed(ActionEvent e) {
			new ConnectToServerDialog(ClientStart.getClient().getFrame()).setVisible(true);
		}
	}
	
	class ExitAction extends AbstractAction{
		
		public ExitAction(){
			super("Exit");
		}
		
		public void actionPerformed(ActionEvent arg0) {
			ClientStart.getClient().requestExit();
		}
	}	
	
	class AboutAction extends AbstractAction{
		
		public AboutAction(){
			super("About");
		}

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(frame,"Made by Hoosteen\nVersion " + shared.Settings.version);
		}
		
	}
}