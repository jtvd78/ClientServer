package client.ui.node;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import client.Client;
import client.Server;
import client.User;
import client.ui.ServerPanel;

public class ServerNode extends com.hoosteen.tree.ComponentNode{
	
	Server server;
	JComponent comp;
	
	HashMap<User, UserNode> userNodes = new HashMap<User, UserNode>();
	
	
	public ServerNode(Server server){
		this.server = server;
		comp = new ServerPanel(server);
		
		FileBrowserNode file = new FileBrowserNode(server);
		addNode(file);
	
		this.addRightClickOption(new JMenuItem(new DisconnectAction()));
	}	
	
	class DisconnectAction extends AbstractAction{
		
		public DisconnectAction(){
			super("Disconnect");
		}
		
		public void actionPerformed(ActionEvent e) {
			Server currentServer = Client.getClient().getServerManager().getCurrentServer();
			
			if(currentServer != null){
				Client.getClient().getServerManager().removeServer(currentServer);
			}
		}
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}

	public void addUser(User u) {
		UserNode node = new UserNode(u);
		userNodes.put(u, node);
		addNode(node);
	}

	public void removeUser(User u) {
		UserNode toRemove = userNodes.get(u);
		if(toRemove != null){
			removeNode(toRemove);
		}
	}
	
	public String toString(){
		return server.toString();
	}

	public Server getServer() {
		return server;
	}
}