package client.ui.node;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;

import client.User;
import client.ui.UserPanel;

public class UserNode extends ComponentNode{
	
	User u;
	UserPanel comp;
	
	public UserNode(User u){
		this.u = u;
		comp = new UserPanel(u);
		u.setPanel(comp);
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	public String toString(){
		return u.getName();
	}
}