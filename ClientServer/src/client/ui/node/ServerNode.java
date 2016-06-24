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
	UsersNode users;
	FileBrowserNode file;
	
	public ServerNode(Server server){
		this.server = server;
		comp = new ServerPanel(server);
		
		
		addNode(users = new UsersNode());
		addNode(file = new FileBrowserNode(server));
	
		this.addRightClickOption(new JMenuItem(new DisconnectAction()));
	}	
	
	class DisconnectAction extends AbstractAction{
		
		public DisconnectAction(){
			super("Disconnect");
		}
		
		public void actionPerformed(ActionEvent e) {
			Client.getClient().getServerManager().removeServer(server);
		}
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}

	public void addUser(User u) {
		users.addUser(u);
	}

	public void removeUser(User u) {
		users.removeUser(u);
	}
	
	public String toString(){
		return server.toString();
	}

	public Server getServer() {
		return server;
	}
}