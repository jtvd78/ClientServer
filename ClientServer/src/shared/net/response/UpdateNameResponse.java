package shared.net.response;

import client.ui.node.Server;
import client.ui.node.User;

public class UpdateNameResponse extends MessageResponse{
	
	int uID;
	String name;
	
	public UpdateNameResponse(String name, int uID){
		this.name = name;
		this.uID = uID;
	}
	

	@Override
	public void handle(Server s) {
		User u = s.getUser(uID);
		u.setName(name);
	}

}