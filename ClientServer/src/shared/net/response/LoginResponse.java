package shared.net.response;

import client.ui.node.Server;

public class LoginResponse extends MessageResponse{
	
	int uID;
	
	public enum Type{
		SUCCESS, INCORRECT_LP, INCORRECT_VERSION
	}
	
	Type type;
	
	public LoginResponse(Type type){
		this.type = type;
	}

	@Override
	public void handle(Server s) {
		
	}
	
	public Type getType(){
		return type;
	}
	
}
