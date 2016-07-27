package client.ui.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;
import com.hoosteen.ui.table.TableActionListener;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.ClientStart;
import client.ui.ClientFrame;

public class ServerManager extends ComponentNode{
	
	ServersComp comp;
	
	public ServerManager() {
		super(true);
		comp = new ServersComp();
	}
	
	public void addServer(Server s){
		addNode(s);
		comp.dataChanged();
	}
	
	public void removeServer(Server s){
		removeNode(s);
		comp.dataChanged();
	}
	
	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	class ServersComp extends TableComp<ServerData>{	
		
		public ServersComp() {
			super(new ServerDataSource());
			this.addTableActionListener(new ServerTableActionListener());
		}
		
		
		
	}
	
	class ServerTableActionListener implements TableActionListener{

		@Override
		public void rowSelected(int row, TableData data) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rowDoubleClicked(int row, TableData data) {
			Server s = (Server) ServerManager.this.getNode(row);
			ClientFrame f = ClientStart.getClient().getFrame();
			f.getTreeComp().nodeLeftClicked(s);
		}

		@Override
		public void rowRightClicked(int row, TableData data) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ServerDataSource implements TableDataSource<ServerData>{

		@Override
		public ServerData[] getData() {
			ServerData[] data = new ServerData[ServerManager.this.size()];
			
			int ctr = 0;
			for(Node n : ServerManager.this){
				Server s = (Server)n;
				
				data[ctr] = new ServerData(s);
				
				ctr++;
			}
			
			return data;
		}

		@Override
		public int getColumnCount() {
			return 1;
		}
		
	}
	
	class ServerData implements TableData{

		Server s;
		
		public ServerData(Server s){
			this.s = s;
		}
		
		@Override
		public String[] getTableHeaders() {
			return new String[]{"Server Name"};
		}

		@Override
		public String[] getTableValues() {
			return new String[]{s.getName()};
		}

		@Override
		public int getColumnCount() {
			return 1;
		}
		
	}
	
	
	
	
	
	public String toString(){
		return "Servers";
	}

	public void PM(String message, Server s, int fromUID) {
		
		s.receivePM(message, fromUID);
	}

	public Set<Server> getServerList() {
		
		ArrayList<Server> servers = new ArrayList<Server>();
		
		for(Node n : this){
			servers.add((Server)n);
		}
		
		return new HashSet<Server>(servers);
	}
}