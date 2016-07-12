package client.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.hoosteen.ui.table.TableActionListener;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.Server;
import client.net.Connection;
import shared.file.FileData;
import shared.file.FilePath;
import shared.net.Message;

public class FileBrowser extends JPanel implements TableDataSource<FileData>{
	
	
	FilePath path;
	
	
	Server server;
	

	FileData[] fileData;	
	TableComp<FileData> tc;

	public FileBrowser(Server server){
		super(new BorderLayout());
		this.server = server;
		this.path = new FilePath();		
		
		tc = new TableComp<FileData>(this);
		tc.addTableActionListener(new Listener());		
		add(tc, BorderLayout.CENTER);
	}

	@Override
	public FileData[] getData() {
		
		Message m = new Message(Message.Type.GET_FILELIST);		
		m.put(path.toString());
		server.sendMessage(m);
		m.waitForResponse();
		
		System.out.println(m.getResponse().get(0));
		
		
		fileData = (FileData[])m.getResponse().get(1);	
		
		return fileData;		
	}
	
	public void dataChanged(){
		tc.dataChanged();
	}
	
	/*
	public class Path {

		private ArrayList<String> pathList = new ArrayList<String>();
		
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
	
	*/
	
	
	private class Listener implements TableActionListener{

		@Override
		public void rowSelected(int row, TableData data) {
			
		}

		@Override
		public void rowDoubleClicked(int row, TableData data) {
			path.add(fileData[row].getName());
		}

		@Override
		public void rowRightClicked(int row, TableData data) {
			path.goUp();
		}
	}

	@Override
	public int getColumnCount() {
		//bad hack
		return new FileData("",0,false).getColumnCount();
	}
}