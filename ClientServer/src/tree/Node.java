package tree;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Node {
	
	public enum Type{
		SERVER,USER
	}
	
	Node parent;
	String name;
	boolean expanded = false;
	
	ArrayList<Node> nodeList = new ArrayList<Node>();
	NodeObject object;
	Type type;
	
	public Node(String name){
		this.name = name;
		object = new BlankNodeObject();
	}
	
	public Node(String name, NodeObject o){
		this.name = name;
		object = o;
	}
	
	class BlankNodeObject implements NodeObject{

		@Override
		public boolean hasNotification() {
			return false;
		}

		@Override
		public JPanel getPanel() {
			return null;
		}
	}
	
	public Type getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public void setParent(Node n){
		this.parent = n;
	}
	
	public synchronized void addNode(Node n){
		n.setParent(this);
		nodeList.add(n);
	}
	
	public synchronized void removeNode(Node n){
		nodeList.remove(n);
	}
	
	public int size(){
		return nodeList.size();
	}
	
	public Node getNode(int index){
		return nodeList.get(index);
	}
	
	
	public NodeObject getNodeObject(){
		return object;
	}
	
	public void setNodeObject(NodeObject o){
		object = o;
	}
	
	
	public void setExpanded(boolean e){
		expanded = e;
	}
	
	public boolean isExpanded(){
		return expanded;
	}
	
	public String getPath(){
		if(parent == null){
			return File.separator;
		}else{
			return (parent.getPath() + File.separator + name);
		}		
	}
	
	
	public Node getNodeByObject(NodeObject o){
		for(Node node : nodeList){
			if(node.getNodeObject().equals(o)){
				return node;
			}
		}
		return null;
	}
	
	
	public int getLevel(){
		return 1+parent.getLevel();
	}
	
	public void toggleExpanded(){
		expanded = !expanded;
	}
	
	public Node getVisibleNode(int nu){		
		for(Node n : nodeList){
			if(nu - 1 == 0){
				return n;
			}
			
			//to account for current node
			nu -= 1; 
			
			int count = n.getExpandedNodeCount();
			
			if(n.isExpanded() && n.size() != 0){
				if(nu - count > 0){
					nu-= count;		//next n
				}else if(nu - count <= 0){
					return n.getVisibleNode(nu);
				}
			}			
		}
		return null;
	}
	
	public int getExpandedNodeCount(){
		int ctr = 0;
		
		if(expanded && size() > 0){
			for(Node n : nodeList){
				ctr += n.getExpandedNodeCount()+1;
			}			
		}
		return ctr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JComponent getPanel() {
		return object.getPanel();
	}
}