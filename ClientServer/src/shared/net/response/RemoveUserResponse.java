package shared.net.response;

import client.ui.node.Server;

public class RemoveUserResponse extends MessageResponse{
	
	int uID;
	
	public RemoveUserResponse(int uID){
		this.uID = uID;
	}
	
	@Override
	public void handle(Server s) {
		s.removeUser(uID);
	}

}
