package shared.net.request;

import com.hoosteen.ui.Console;

import server.ServerStart;
import server.ServerUser;
import server.net.ServerMessageHandler;
import shared.net.MessageRequest;
import shared.net.MessageResponse;
import shared.net.response.LoginResponse;

public class LoginRequest extends MessageRequest{
	
	String name;
	String username;
	String password;
	String version;	
	
	public LoginRequest(String name, String username, String password, String version){
		this.name = name;
		this.username = username;
		this.password = password;
		this.version = version;
	}	

	@Override
	public MessageResponse handle(ServerMessageHandler handler) {
		
		ServerUser u = new ServerUser(name, handler.getConnection());
		handler.setUser(u);
		MessageResponse response;
		
		if(!version.equals(shared.Settings.version)){
			
			response = new LoginResponse(LoginResponse.Type.INCORRECT_VERSION);			
			
		}else{
			
			boolean result = ServerStart.mainServer.login(u, username, password);
			
			if(result){							
				
				Console.out.println(u.getName() + " connected to the server");
				
				response = new LoginResponse(LoginResponse.Type.SUCCESS);
				
				ServerStart.mainServer.userConnected(u, handler.getConnection());
				
			}else{
				
				response = new LoginResponse(LoginResponse.Type.INCORRECT_LP);
			}			
			
		}		
		
		return response;		
	}
}