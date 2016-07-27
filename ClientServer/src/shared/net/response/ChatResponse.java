package shared.net.response;

import client.ui.node.Server;

public class ChatResponse extends MessageResponse{
	
	int fromUID;
	String message;
	
	public ChatResponse(String message, int fromUID){
		this.message = message;
		this.fromUID = fromUID;
	}

	@Override
	public void handle(Server s) {
		s.receiveChat(message, fromUID);
	}
}
