package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

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
import com.hoosteen.tree.Tree;

import client.Client;
import client.Server;
import client.User;
import client.ui.node.BaseNode;
import client.ui.node.MainTree;
import tree.TreeComp;

public class Frame extends JFrame{
	
	BaseNode data;
	
	
	JSplitPane split;
	JPanel rightPanel;

	public Frame(){
		
		init();
		
		setTitle(client.res.Strings.programName);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}	
	
	private void init(){
		//Split Pane
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setDividerLocation(150);
		split.setResizeWeight(0);
		
		
		data = new BaseNode();
		MainTree t = new MainTree(data);
		com.hoosteen.tree.TreeComp tc = new com.hoosteen.tree.TreeComp(this,t);
		
		tc.addNodeEventListner(new NodeEventListener(){

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
				
				Client.getClient().getFrame().setRightPanel(pan);		
				
			}
			
		});
		
		//Split panels
		rightPanel = new JPanel(new GridLayout(0,1));
		rightPanel.setBackground(Color.white);
		
		split.setLeftComponent(tc);
		split.setRightComponent(rightPanel);
		
		add(split);
		setJMenuBar(new MenuBar(this));
	}
	
	public void setRightPanel(JComponent p){
		
		rightPanel.removeAll();
		rightPanel.add(p,0);
		split.validate();
		split.repaint();
		
	}
	
	public void addUser(User u, Server s){
		data.addUser(u, s);
	}

	public void removeUser(User u, Server s) {
		data.removeUser(u, s);
	}

	public BaseNode getTree() {
		return data;
	}
}