package client;

import client.ui.UserPanel;

public class User{
	
	int uID;
	private String name;	
	private Server server;
	private UserPanel panel;
	
	public User(String name, Server server, int uID){
		this.name = name;
		this.server = server;
		this.uID = uID;
	}	
	
	public String getName(){
		return name;
	}
	
	public void PM(String s){
		panel.updateChat(s);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getID(){
		return uID;
	}
	
	public Server getServer(){
		return server;
	}
	
	public void setPanel(UserPanel up){
		panel = up;
	}
}