package client.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import client.Server;
import client.User;
import tree.TreeComp;

public class Frame extends JFrame{
	
	//Panels
	TreeComp tree;
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
		
		//Server Tree
		tree = new TreeComp(); 
		
		//Split panels
		rightPanel = new JPanel(new GridLayout(0,1));
		rightPanel.setBackground(Color.white);
		
		split.setLeftComponent(tree);
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
		tree.addUser(u, s);
	}

	public void removeUser(User u, Server s) {
		tree.removeUser(u, s);
	}

	public TreeComp getTree() {
		return tree;
	}
}