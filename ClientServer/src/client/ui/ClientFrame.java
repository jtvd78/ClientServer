package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;
import com.hoosteen.tree.NodeEvent;
import com.hoosteen.tree.NodeEventListener;
import com.hoosteen.tree.TreeComp;

import client.ClientStart;
import client.ui.node.BaseNode;
import client.ui.node.Server;
import client.ui.node.User;

public class ClientFrame extends JFrame{
	
	BaseNode data;	
	
	JSplitPane split;
	JPanel rightPanel;
	
	Server currentServer;
	
	TreeComp tc;

	public ClientFrame(){
		
		init();
		
		setTitle(client.settings.ClientSettings.programName);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}	
	
	private void init(){
		//Split Pane
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		split.setDividerLocation(150);
		split.setResizeWeight(0);
		
		
		data = new BaseNode();
		tc = new TreeComp(this,data);
		tc.allowNodeRemoval(false);
		
		tc.addNodeEventListner(new NodeListener());
		
		//Split panels
		rightPanel = new JPanel(new GridLayout(0,1));
		rightPanel.setBackground(Color.white);
		
		split.setLeftComponent(tc);
		split.setRightComponent(rightPanel);
		
		add(split);
		setJMenuBar(new MenuBar(this));
	}
	
	class NodeListener implements NodeEventListener{
		
		@Override
		public void nodeRightClicked(String text, NodeEvent event) {
			//Do nothing
		}

		@Override
		public void nodeLeftClicked(NodeEvent nodeEvent) {
			
			Node n = nodeEvent.getNode();
			
			JComponent pan = ((ComponentNode)n).getComponent();
			 
			if(pan == null){
				pan = new JPanel(new BorderLayout());
				 
				JLabel lb = new JLabel(n.toString());
				lb.setHorizontalAlignment(SwingConstants.CENTER);
				 
				pan.add(lb,BorderLayout.CENTER);
			}
			
			ClientStart.getClient().getFrame().setRightPanel(pan);	
			
			//Set current server
			//Find server at root of selected node
			Node current = n;
			
			do{
				if(current instanceof Server){
					currentServer = (Server)current;
					break;
				}
				
				current = current.getParent();
				
				if(current == null){
					currentServer = null;
					break;
				}	
				
			}while(current != null);
		}

		@Override
		public void nodeDoubleClicked(NodeEvent nodeEvent) {
			nodeEvent.getNode().toggleExpanded();
		}
	}
	
	public Server getCurrentServer(){
		return currentServer;
	}
	
	public TreeComp getTreeComp(){
		return tc;
	}
	
	public void setRightPanel(JComponent p){
		
		rightPanel.removeAll();
		rightPanel.add(p,0);
		split.validate();
		split.repaint();
		
	}
	
	public void PM(String message, Server s, int fromUID){
		data.PM(message, s, fromUID);
	}

	public BaseNode getTree() {
		return data;
	}

	public void removeServer(Server s) {
		data.removeServer(s);
	}

	public void addServer(Server s) {
		data.addServer(s);
	}

	public Set<Server> getServerList() {
		return data.getServerList();
	}
}