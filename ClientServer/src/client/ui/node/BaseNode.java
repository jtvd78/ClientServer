package client.ui.node;

import java.util.List;
import java.util.Set;

import client.Server;
import client.User;

public class BaseNode extends com.hoosteen.tree.Node{	
	
	ServerManager servers;
	TransfersNode transfers;	
	
	public BaseNode(){
		
		servers = new ServerManager();
		transfers = new TransfersNode();		
		
		addNode(servers);
		addNode(transfers);
		setExpanded(true);
	}
	
	public void addServer(Server s){
		servers.addServer(s);
	}
	
	public void removeServer(Server s){
		servers.removeServer(s);
	}

	public void PM(String message, Server s, int fromUID) {
		servers.PM(message, s, fromUID);
	}

	public Set<Server> getServerList() {
		return servers.getServerList();
	}
}