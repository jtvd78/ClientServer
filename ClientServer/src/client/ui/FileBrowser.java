package client.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.hoosteen.graphics.table.TableActionListener;
import com.hoosteen.graphics.table.TableComp;
import com.hoosteen.graphics.table.TableData;
import com.hoosteen.graphics.table.TableDataSource;

import client.Server;
import client.net.Connection;
import shared.file.FileData;
import shared.net.Message;

public class FileBrowser extends JPanel implements TableDataSource<FileData>{
	
	
	Path path;
	
	Connection c;
	Server server;
	

	FileData[] fileData;	

	public FileBrowser(Server server){
		super(new BorderLayout());
		this.server = server;
		this.path = new Path();
		
		
		TableComp<FileData> tc = new TableComp<FileData>(this);
		tc.addTableActionListener(new Listener());		
		add(tc, BorderLayout.CENTER);
	}

	@Override
	public FileData[] getData() {
		
		if(fileData == null){
			updateBrowser();
		}
		
		return fileData;		
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
		
		
		fileData = (FileData[])m.getResponse().get(1);	

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
	
	private class Listener implements TableActionListener{

		@Override
		public void rowSelected(int row, TableData data) {
			
		}

		@Override
		public void rowDoubleClicked(int row, TableData data) {
			path.add(fileData[row].getName());
			updateBrowser();
		}

		@Override
		public void rowRightClicked(int row, TableData data) {
			path.goUp();
			updateBrowser();
		}
	}

	@Override
	public int getColumnCount() {
		//bad hack
		return new FileData("",0,false).getColumnCount();
	}
}