package server;

import server.net.ServerConnection;
import shared.net.response.AddUserResponse;
import shared.net.response.MessageResponse;
import shared.net.response.RemoveUserResponse;

public class ServerUser {
	
	private String name;
	private ServerConnection c;
	public int uID;
	
	public ServerUser(String name, ServerConnection c){
		this.name = name;
		this.c = c;
		this.uID = c.getConnectionNumber();
	}
	
	public String getName(){
		return name;
	}
	
	public void addUser(ServerUser u){
		
		MessageResponse addUser = new AddUserResponse(u.getName(), u.uID);
		c.sendMessage(addUser);
	}
	
	public void removeUser(ServerUser u){
		MessageResponse removeUser = new RemoveUserResponse(u.uID);
		c.sendMessage(removeUser);
	}

	public void sendMessage(MessageResponse response){
		c.sendMessage(response);
	}

	public void setName(String name) {
		this.name = name;
	}
}