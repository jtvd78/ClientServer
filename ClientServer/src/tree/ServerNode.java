package tree;

import javax.swing.JPanel;

import client.Server;

public class ServerNode extends Node{
	
	Node userNode;
	Node fileBrowserNode;

	public ServerNode(Server s) {
		super(s.getName());
		
		object = s;
		
		fileBrowserNode = new Node("File Browser", s.getFileBrowser());
		userNode = new Node("Users");
		
		addNode(fileBrowserNode);
		addNode(userNode);
	}
	
	public Node getFileBrowserNode(){
		return fileBrowserNode;
	}
	
	public Node getUserNode(){
		return userNode;
	}
	
	public JPanel getPanel(){
		return object.getPanel();
	}
}