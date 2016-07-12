package client.ui.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.Server;

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