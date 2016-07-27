package client.ui.node;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.ui.ChatPanel;

import client.ClientStart;
import client.ServerSettings;
import client.net.Connection;
import shared.net.Message;


public class Server extends ComponentNode{
	
	Connection c; 
	ServerPanel panel;
	ServerSettings ss;
	
	UserManager users;
	FileBrowserNode file;
	
	public Server(ServerSettings ss){
		super(true);
		
		this.ss = ss;
		
		panel = new ServerPanel();
		
		addNode(users = new UserManager());
		addNode(file = new FileBrowserNode(this));
		
		addRightClickOption(new JMenuItem(new DisconnectAction()));
	}
	
	public class ServerPanel extends ChatPanel{
		
		@Override
		public void send(String str){
			sendChat(str);
		}

		public void receiveChat(String fromName, String message) {
			updateChat("<" + fromName + "> " + message);
		}
	}
	
	class DisconnectAction extends AbstractAction{
		
		public DisconnectAction(){
			super("Disconnect");
		}
		
		public void actionPerformed(ActionEvent e) {
			ClientStart.getClient().removeServer(Server.this);
		}
	}
	
	public void connect() throws UnknownHostException, IOException{
		
		Socket socket = new Socket(InetAddress.getByName(ss.address),ss.port);
		
		c = new Connection(socket,this);	
		c.start();
		
		
		System.out.println("Connecting to " + ss.name + " at " + ss.address + " on port " + ss.port + " with the username " + ss.login + " and the password " + ss.password);
		
		
		//Login		
		Message m = new Message(Message.Type.LOGIN);
		m.put(ClientStart.getClient().getSettings().name);
		m.put(ss.login);
		m.put(ss.password);
		m.put(shared.Settings.version);
		
		c.sendMessage(m);
		m.waitForResponse();
		
		Message re = m.getResponse();
		System.out.println(re);
		
		
		//Update the contents of the File Browser
		file.dataChanged();
		
		
		if(re.type == Message.Type.CORRECT_LP){
			System.out.println("Correct LP");
		}else if(re.type == Message.Type.INCORRECT_LP){
			System.out.println("Incorrent LP");
		}else if(re.type == Message.Type.INCORRECT_VERSION){
			System.out.println("Incorrect Version");
		}
	}
	
	public void sendChat(String s){
		Message m = new Message(Message.Type.CHAT);
		m.put(s);
		m.put(ClientStart.getClient().getSettings().name);
		c.sendMessage(m);
	}
	
	public void sendPM(String str, int uID) {
		Message m = new Message(Message.Type.PM);
		m.put(str);
		m.put(uID);
		c.sendMessage(m);
	}
	
	public void receivePM(String message, int fromUID) {
		users.receivePM(message, fromUID);
	}
	
	public void disconnect(){
		c.close();
	}
	
	public String getName(){
		return ss.name;
	}
	
	public String toString(){
		return ss.name;
	}
	
	public void receiveChat(String fromName, String message) {
		panel.receiveChat(fromName, message);
	}
	
	public void addUser(String s, int uID){		
		User u = new User(s, this, uID);
		users.addUser(u);
	}
	
	
	public User getUser(int uID){
		return users.getUser(uID);
	}	
	
	public void removeUser(int uID){
		
		User remove = getUser(uID);
		
		if(remove != null){
			users.removeUser(remove);
		}
	}

	public void sendMessage(Message m) {
		c.sendMessage(m);
	}

	@Override
	public JComponent getComponent() {
		return panel;
	}
}