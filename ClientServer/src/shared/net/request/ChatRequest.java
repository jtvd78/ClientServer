package shared.net.request;

import server.ServerStart;
import server.net.ServerMessageHandler;
import shared.net.MessageRequest;
import shared.net.MessageResponse;
import shared.net.response.ChatResponse;

public class ChatRequest extends MessageRequest{

	String message;
	
	public ChatRequest(String message){
		this.message = message;
	}
	
	@Override
	public MessageResponse handle(ServerMessageHandler serverMessageHandler) {
		
		ChatResponse response = new ChatResponse(message, serverMessageHandler.getUser().uID);		
		
		ServerStart.mainServer.sendChatToClients(response);
		
		return null;
	}

}
