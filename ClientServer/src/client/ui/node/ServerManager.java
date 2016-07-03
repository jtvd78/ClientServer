package client.ui.node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import com.hoosteen.tree.Node;

import client.Server;

public class ServerManager extends com.hoosteen.tree.ComponentNode{
	
	ServersComp comp;
	
	public ServerManager() {
		comp = new ServersComp();
	}
	
	public void addServer(Server s){
		addNode(s);
	}
	
	public void removeServer(Server s){
		removeNode(s);
	}
	
	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	class ServersComp extends JComponent{	
		
		public void paintComponent(Graphics g){
			
			int ctr = 0;
			g.setColor(Color.RED);
			
			for(Node n : ServerManager.this){
				ctr++;
				g.drawString(n.toString(), 0, ctr*15);
			}
		}
	}
	
	public String toString(){
		return "Servers";
	}

	public void PM(String message, Server s, int fromUID) {
		
		s.receivePM(message, fromUID);
	}

	public Set<Server> getServerList() {
		
		ArrayList<Server> servers = new ArrayList<Server>();
		
		for(Node n : this){
			servers.add((Server)n);
		}
		
		return new HashSet<Server>(servers);
	}
}