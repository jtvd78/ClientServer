package client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.hoosteen.ui.table.TableActionListener;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.ui.node.Server;
import shared.file.FileData;
import shared.file.FilePath;
import shared.net.request.FileListRequest;
import shared.net.response.FileListResponse;

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
		
		FileListRequest request = new FileListRequest(path.toString());
		server.sendMessage(request);
		request.waitForResponse();
		
		FileListResponse response = (FileListResponse) request.getResponse();
		
		
		fileData = response.getFileList();
		
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