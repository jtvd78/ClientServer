package shared.net.request;

import server.ServerStart;
import server.net.ServerMessageHandler;
import shared.net.response.MessageResponse;
import shared.net.response.PMResponse;

public class PMRequest extends MessageRequest{
	
	int toUID;
	String message;
	
	public PMRequest(int toUID, String message){
		this.toUID = toUID;
		this.message = message;
	}	

	@Override
	public MessageResponse handle(ServerMessageHandler handler) {
		
		PMResponse response = new PMResponse(message, handler.getUser().uID);
		
		ServerStart.mainServer.sendPMToClient(response, toUID);
		return null;		
		
	}
}