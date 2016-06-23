package client.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.Server;
import client.net.Connection;
import shared.net.Message;
import tree.Node;
import tree.Path;
import tree.Tree;

public class FileBrowser extends JPanel{
	
	Node root;
	
	Path path;
	String[] strings;
	
//	JButton
	JList<String> fileList;
	JLabel loading;

	Server server;
	
	Connection c; 
	
	public FileBrowser(Server server){
		super(new BorderLayout());
		
		this.server = server;
		
		root = new Tree();
		
		ListListener ll = new ListListener();
		fileList = new JList<String>();
		fileList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.addMouseListener(ll);
		
		
		JScrollPane jsp = new JScrollPane(fileList);
		add(jsp);
		
		loading = new JLabel("Getting Files");
		path = new Path();
		
	}
	
	private class ListListener implements MouseListener{
		public void mousePressed(MouseEvent e) {
			
			if(c == null){
				updateBrowser();
			}
			
			
			if(e.getButton() == 3){
				path.goUp();
				updateBrowser();
				return;
			}
			
			path.add(strings[fileList.locationToIndex(new Point(e.getX(),e.getY()))]);
			updateBrowser();
		}
		
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	public void updateBrowser(){
		
		//Make sure there's a connection to the server first
		if(c == null){
			c = server.getConnection();
			if(c == null){
				return;
			}
		}
		
		
		Message m = new Message(Message.Type.GET_FILELIST);
		m.put(path.toString());
		c.sendMessage(m);
		m.waitForResponse();
		
		
		strings = (String[])m.getResponse().get(1);	
		
	//	if(strings == null){
	///		path.goUp();
	//		updateBrowser();
	//		return;
	//	}
		
		fileList.setListData(strings);
		fileList.repaint();
		repaint();
	}
}