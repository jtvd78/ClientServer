package client.ui.node;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;

import client.User;
import client.ui.UserPanel;

public class UsersNode extends ComponentNode{
	
	UsersComp comp;
	
	public UsersNode(){
		
		comp = new UsersComp();
		
	}
	
	public void addUser(User u){
		
		UserNode node = new UserNode(u);
		
		addNode(node);
		
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
}