package shared.net.response;

import client.ui.node.Server;

public class AddUserResponse extends MessageResponse{
	
	int uID;
	String name;
	
	
	public AddUserResponse(String name, int uID){
		this.uID = uID;
		this.name = name;
	}

	@Override
	public void handle(Server s) {
		s.addUser(name, uID);
	}

}
