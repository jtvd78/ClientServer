package client.ui.node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;

import client.User;
import client.ui.UserPanel;

public class UsersNode extends ComponentNode{
	
	UsersComp comp;
	
	

	HashMap<User, UserNode> userNodes = new HashMap<User, UserNode>();
	
	public UsersNode(){
		
		comp = new UsersComp();
		
	}
	
	public void addUser(User u){
		
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

	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	class UsersComp extends JComponent{	
		
		public void paintComponent(Graphics g){
			
			int ctr = 0;
			g.setColor(Color.RED);
			
			for(Node n : UsersNode.this){
				ctr++;
				g.drawString(n.toString(), 0, ctr*15);
			}
		}
	}
	
	public String toString(){
		return "Users";
	}

	public void receivePM(String message, int fromUID) {
		for(User u : userNodes.keySet()){
			if(u.getID() == fromUID){
				userNodes.get(u).comp.updateChat("<" + u.getName() + ">" + message);
			}
		}
	}

	public User getUser(int uID) {
		for(Node n : this){
			UserNode u = (UserNode)n;
			if(u.getUser().getID() == uID){
				return u.getUser();
			}
		}
		return null;
	}
}