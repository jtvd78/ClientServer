package client;

import java.util.ArrayList;

public class ServerManager {
	
	private Server currentServer;
	private ArrayList<Server> serverList = new ArrayList<Server>();
	
	public void addServer(Server s){
		serverList.add(s);
		Client.getClient().getFrame().getTree().addServer(s);
	}
	
	public void removeServer(Server s){
		
		if(contains(s)){
			if(currentServer == s){
				
				int index = serverList.indexOf(s);
				
				if(index == (serverList.size())-1){
					currentServer = serverList.get(index-1);
				}else if(index < (serverList.size()-1)){
					currentServer = serverList.get(index);
				}else if(index == 0){
					currentServer = serverList.get(1);
				}
			}
			
			serverList.remove(s);
			Client.getClient().getFrame().getTree().removeServer(s);
			s.disconnect();
		}else{
			System.out.println("That Server Doesn't exist");
		}
	}
	
	public boolean contains(Server s){
		if(serverList.contains(s)){
			return true;
		}
		return false;
	}
	
	public void setCurrentServer(Server s){
		if(contains(s)){
			currentServer = s;
		}	
	}
	
	public Server getCurrentServer(){
		return currentServer;
	}

	public ArrayList<Server> getServerList() {
		return serverList;
	}
}