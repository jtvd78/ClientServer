package client.ui.node;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.ui.ChatPanel;

import client.ClientStart;
import client.ServerSettings;
import client.net.Connection;
import shared.net.MessageRequest;
import shared.net.request.ChatRequest;
import shared.net.request.LoginRequest;
import shared.net.request.PMRequest;
import shared.net.response.LoginResponse;


public class Server extends ComponentNode{
	
	Connection c; 
	ServerPanel panel;
	ServerSettings ss;
	
	UserManager users;
	FileBrowserNode file;
	
	public Server(ServerSettings ss){
		super(true);
		
		this.ss = ss;
		
		panel = new ServerPanel();
		
		addNode(users = new UserManager());
		addNode(file = new FileBrowserNode(this));
		
		addRightClickOption(new JMenuItem(new DisconnectAction()));
	}
	
	public class ServerPanel extends ChatPanel{
		
		@Override
		public void send(String str){
			sendChat(str);
		}

		public void receiveChat(String fromName, String message) {
			updateChat("<" + fromName + "> " + message);
		}
	}
	
	class DisconnectAction extends AbstractAction{
		
		public DisconnectAction(){
			super("Disconnect");
		}
		
		public void actionPerformed(ActionEvent e) {
			ClientStart.getClient().removeServer(Server.this);
		}
	}
	
	public void connect() throws UnknownHostException, IOException{
		
		Socket socket = new Socket(InetAddress.getByName(ss.address),ss.port);
		
		c = new Connection(socket,this);	
		c.start();
		
		LoginRequest request = new LoginRequest(ClientStart.getClient().getSettings().name, ss.login, ss.password, shared.Settings.version);
		c.sendMessage(request);		
		
		
		LoginResponse re = (LoginResponse) request.waitForResponse();
		
		
		//Update the contents of the File Browser
		file.dataChanged();
		
		
		if(re.getType() == LoginResponse.Type.SUCCESS){
			System.out.println("Correct LP");
		}else if(re.getType() == LoginResponse.Type.INCORRECT_LP){
			System.out.println("Incorrent LP");
		}else if(re.getType() == LoginResponse.Type.INCORRECT_VERSION){
			System.out.println("Incorrect Version");
		}
	}
	
	public void sendChat(String message){
		
		ChatRequest chat = new ChatRequest(message);
		c.sendMessage(chat);
	}
	
	public void sendPM(String str, int uID) {
		
		MessageRequest pmRequest = new PMRequest(uID, str);
		c.sendMessage(pmRequest);	
	}
	
	public void receivePM(String message, int fromUID) {
		users.receivePM(message, fromUID);
	}
	
	public void disconnect(){
		c.close();
	}
	
	public String getName(){
		return ss.name;
	}
	
	public String toString(){
		return ss.name;
	}
	
	public void receiveChat(String message, int fromUID) {
		
		String fromName = users.getUser(fromUID).getName();
		
		panel.receiveChat(fromName, message);
	}
	
	public void addUser(String s, int uID){		
		User u = new User(s, this, uID);
		users.addUser(u);
	}
	
	
	public User getUser(int uID){
		return users.getUser(uID);
	}	
	
	public void removeUser(int uID){
		
		User remove = getUser(uID);
		
		if(remove != null){
			users.removeUser(remove);
		}
	}

	public void sendMessage(MessageRequest m) {
		c.sendMessage(m);
	}

	@Override
	public JComponent getComponent() {
		return panel;
	}
}