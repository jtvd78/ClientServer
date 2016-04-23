package server;

import server.net.ServerConnection;
import shared.net.Message;

public class ServerUser {
	
	private String name;
	private ServerConnection c;
	public int uID;
	
	public ServerUser(String name, ServerConnection c, int uID){
		this.name = name;
		this.c = c;
		this.uID = uID;
	}
	
	public String getName(){
		return name;
	}
	
	public void addUser(ServerUser u){
		Message m = new Message(Message.Type.ADD_USER);
		m.put(u.getName());
		m.put(u.uID);
		c.sendMessage(m);
	}
	
	public void removeUser(ServerUser u){
		Message m = new Message(Message.Type.REMOVE_USER);
		m.put(u.uID);
		c.sendMessage(m);
	}

	public void sendMessage(Message m){
		c.sendMessage(m);
	}

	public void setName(String name) {
		this.name = name;
	}
}