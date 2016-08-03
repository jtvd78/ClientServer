package server.net;

import server.ServerUser;
import shared.net.MessageRequest;
import shared.net.MessageResponse;

public class ServerMessageHandler {
	
	ServerUser u;	
	ServerConnection c;
	
	public ServerMessageHandler(ServerConnection c){
		this.c = c;
	}	
	
	public void handleMessage(MessageRequest request){
		
		MessageResponse response = request.handle(this);
		
		if(response != null){
			
			response.setID(request.getID());
			u.sendMessage(response);
			
		}	
	}

	public ServerConnection getConnection() {
		return c;
	}

	public ServerUser getUser() {
		return u;
	}
	
	public void setUser(ServerUser u){
		this.u = u;
	}
}
