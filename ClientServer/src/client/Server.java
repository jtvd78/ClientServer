package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JPanel;

import tree.NodeObject;
import client.net.Connection;
import client.ui.FileBrowser;
import shared.net.Message;


public class Server implements NodeObject{
	
	Connection c; 
	ServerPanel panel;
	ServerSettings ss;
	FileBrowser fileBrowser;
	
	ArrayList<User> userList = new ArrayList<User>();
	
	public Server(ServerSettings ss){
		this.ss = ss;
		
		panel = new ServerPanel(this);
		fileBrowser = new FileBrowser();
	}
	
	public void connect() throws UnknownHostException, IOException{
		
		Socket socket = new Socket(InetAddress.getByName(ss.address),ss.port);
		
		c = new Connection(socket,this);	
		c.start();
		fileBrowser.setConnection(c);
		
		
		System.out.println("Connecting to " + ss.name + " at " + ss.address + " on port " + ss.port + " with the username " + ss.login + " and the password " + ss.password);
		
		
		//Login		
		Message m = new Message(Message.Type.LOGIN);
		m.put(Client.getClient().getSettings().name);
		m.put(ss.login);
		m.put(ss.password);
		m.put(client.res.Strings.version);
		
		c.sendMessage(m);
		m.waitForResponse();
		
		Message re = m.getResponse();
		System.out.println(re);
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
		m.put(Client.getClient().getSettings().name);
		c.sendMessage(m);
	}
	
	public void sendPM(String str, int uID) {
		Message m = new Message(Message.Type.PM);
		m.put(str);
		m.put(uID);
		c.sendMessage(m);
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
	

	public void addText(String s) {
		panel.updateChat(s);
	}
	
	public void addUser(String s, int uID){
		User u = new User(s, this, uID);
		userList.add(u);
		Client.getClient().getFrame().addUser(u, this);
	}
	
	
	public User getUser(int uID){
		for(User u : userList){
			if(u.uID == uID){
				return u;
			}
		}
		return null;
	}
	
	
	
	public void removeUser(int uID){
		
		User remove = null;
		
		for(User u : userList){
			if(u.uID == uID){
				remove = u;
			}
		}
		
		if(remove != null){
			userList.remove(remove);
			Client.getClient().getFrame().removeUser(remove,this);
		}
	}
	

	public FileBrowser getFileBrowser() {
		return fileBrowser;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void sendMessage(Message m) {
		c.sendMessage(m);
	}

	@Override
	public boolean hasNotification() {
		// TODO Auto-generated method stub
		return false;
	}
}