package shared.net.request;

import server.ServerStart;
import server.ServerUser;
import server.net.ServerMessageHandler;
import shared.net.MessageRequest;
import shared.net.MessageResponse;
import shared.net.response.UpdateNameResponse;

public class UpdateNameRequest extends MessageRequest{
	
	String name;
	
	public UpdateNameRequest(String name){
		this.name = name;
	}
	

	@Override
	public MessageResponse handle(ServerMessageHandler serverMessageHandler) {
		
		ServerUser user = serverMessageHandler.getUser();
		
		user.setName(name);
		
		UpdateNameResponse response = new UpdateNameResponse(name, user.uID);
		
		ServerStart.mainServer.updateUserName(response);
		
		return null;		
		
	}
}
