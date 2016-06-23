package client.ui.node;

import client.Server;
import client.User;

public class BaseNode extends com.hoosteen.tree.Node{	
	
	ServersNode servers;
	TransfersNode transfers;	
	
	public BaseNode(){
		
		servers = new ServersNode();
		transfers = new TransfersNode();		
		
		addNode(servers);
		addNode(transfers);
	}
	
	public void addServer(Server s){
		servers.addServer(s);
	}
	
	public void removeServer(Server s){
		servers.removeServer(s);
	}
	
	public void addUser(User u, Server s){
		servers.addUser(u,s);
	}
	
	public void removeUser(User u, Server s){
		servers.removeUser(u,s);
	}
}