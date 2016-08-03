package shared.net.response;

import client.ClientStart;
import client.ui.node.Server;
import shared.net.AbstractMessage;
import shared.net.MessageResponse;

public class PMResponse extends MessageResponse{
	
	int fromUID;
	String message;
	
	public PMResponse(String message, int fromUID){
		this.message = message;
		this.fromUID = fromUID;
	}

	@Override
	public void handle(Server s) {
		
		ClientStart.getClient().PM(message, s, fromUID);
		
	}
	
}