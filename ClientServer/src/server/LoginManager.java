package server;

import server.ui.Console;

public class LoginManager {
	
	String[][] accounts = new String[][]{
			{"Justin","Password"},
			{"User","Password"}
	};

	public boolean login(ServerUser u, String username, String password) {
		
		for(int c = 0; c < accounts.length; c++){
			if(username.equals(accounts[c][0])){
				if(password.equals(accounts[c][1])){
					return true;
				}
			}
		}
		
		Console.out.println("Login Unsuccessful");
		
		return false;
	}
}