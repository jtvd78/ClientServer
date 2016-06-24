package client.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.Server;
import client.net.Connection;
import shared.net.Message;

public class FileBrowser extends JPanel{	
	
	Path path;
	
	Connection c;
	Server server;	
	
	String[] strings;	
	JList<String> fileList;	

	
	public FileBrowser(Server server){
		super(new BorderLayout());
		
		this.server = server;
		this.path = new Path();
		
		
		ListListener ll = new ListListener();
		fileList = new JList<String>();
		fileList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.addMouseListener(ll);		
		
		//All the data is in here
		add(new JScrollPane(fileList));		
	}
	
	private class ListListener implements MouseListener{
		public void mousePressed(MouseEvent e) {
			
			
			
			if(e.getButton() == 3){
				path.goUp();
				updateBrowser();
				return;
			}
			
			path.add(strings[fileList.locationToIndex(new Point(e.getX(),e.getY()))]);
			updateBrowser();
		}
		
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {
			
			//Try to load once if mouse is entered and there's not connection yet
			if(c == null){
				updateBrowser();
			}			
		}
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
		
		fileList.setListData(strings);
		fileList.repaint();
		repaint();
	}
	
	public class Path {

		ArrayList<String> pathList = new ArrayList<String>();
		
		@Override
		public String toString(){
			String s = File.separator;
			for(String ss : pathList){
				s += ss;
				s += File.separator;
			}
			return s;
		}
		
		public void goUp(){		
			try{
				pathList.remove(pathList.size()-1);
			}catch(ArrayIndexOutOfBoundsException e){
				//Do nothing. Reached the root of the path.
			}
		}
		
		public void add(String s){
			pathList.add(s);
		}
	}
}