package client.ui.node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JComponent;

import com.hoosteen.tree.Node;

import client.Server;
import client.User;

public class ServersNode extends com.hoosteen.tree.ComponentNode{
	
	ServersComp comp;	
	HashMap<Server, ServerNode> serverNodes = new HashMap<Server, ServerNode>();
	
	public ServersNode() {
		comp = new ServersComp();
	}
	
	public void addServer(Server s){		
		ServerNode node = new ServerNode(s);
		serverNodes.put(s, node);
		
		addNode(node);
	}
	
	public void removeServer(Server s){
		
		Node toRemove = serverNodes.get(s);
		if(toRemove != null){
			removeNode(toRemove);
		}
		
	}
	
	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	class ServersComp extends JComponent{	
		
		public void paintComponent(Graphics g){
			
			int ctr = 0;
			g.setColor(Color.RED);
			
			for(Node n : ServersNode.this){
				ctr++;
				g.drawString(n.toString(), 0, ctr*15);
			}
		}
	}

	public void addUser(User u, Server s) {
		ServerNode server = serverNodes.get(s);
		if(server != null){
			server.addUser(u);
		}
	}

	public void removeUser(User u, Server s) {
		ServerNode server = serverNodes.get(s);
		if(server != null){
			server.removeUser(u);
		}		
	}
	
	public String toString(){
		return "Servers";
	}
}