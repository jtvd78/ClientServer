package client;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.ui.ChatPanel;

public class User extends ComponentNode{
	
	int uID;
	private String name;	
	private Server server;
	
	public UserPanel comp;
	
	public User(String name, Server server, int uID){
		this.name = name;
		this.server = server;
		this.uID = uID;
		comp = new UserPanel();
	}	
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
		treeChanged();
	}
	
	public int getID(){
		return uID;
	}
	
	public Server getServer(){
		return server;
	}
	
	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	public String toString(){
		return getName();
	}
	
	public void sendPM(String message){
		server.sendPM(message, uID);
	}

	public void receivePM(String fromName, String message) {
		comp.receivePM(fromName, message);
	}
	
	public class UserPanel extends ChatPanel{
		
		@Override
		public void send(String str){
			sendPM(str);
			updateChat("<" + ClientStart.getClient().getSettings().name + "> " + str);
		}
		
		public void receivePM(String fromName, String message) {
			updateChat("<" + fromName + "> " + message);
		}
	}
}