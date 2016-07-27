package client.ui.node;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;
import com.hoosteen.ui.table.TableActionListener;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.ClientStart;
import client.ui.ClientFrame;

public class UserManager extends ComponentNode{
	
	UsersComp comp;
	
	public UserManager(){
		super(true);
		
		comp = new UsersComp();
		
	}
	
	public void addUser(User u){
		addNode(u);		
		comp.dataChanged();
	}
	
	public void removeUser(User u) {
		removeNode(u);
		comp.dataChanged();
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	class UsersComp extends TableComp<UserData> {	

		public UsersComp() {
			super(new UserDataSource());
			this.addTableActionListener(new UserTableActionListener());
		}
		
	}
	
	class UserTableActionListener implements TableActionListener{

		@Override
		public void rowSelected(int row, TableData data) {}

		@Override
		public void rowDoubleClicked(int row, TableData data) {			
			
			User u = (User) UserManager.this.getNode(row);
			
			ClientFrame f = ClientStart.getClient().getFrame();
			
			f.getTreeComp().nodeLeftClicked(u);			
			
		}

		@Override
		public void rowRightClicked(int row, TableData data) {
			
		}
	}
	
	class UserDataSource implements TableDataSource<UserData>{
		@Override
		public UserData[] getData() {
			
			UserData[] data = new UserData[UserManager.this.size()];
			
			int ctr = 0;
			for(Node n : UserManager.this){
				User u = (User)n;
				
				data[ctr] = new UserData(u);
				
				ctr++;
			}
			
			return data;
		}

		@Override
		public int getColumnCount() {
			return 1;
		}
	}
	
	class UserData implements TableData{
		
		String name;
		
		public UserData(User u){
			this.name = u.getName();
		}

		@Override
		public String[] getTableHeaders() {
			return new String[]{"Name"};
		}

		@Override
		public String[] getTableValues() {
			return new String[]{name};
		}

		@Override
		public int getColumnCount() {
			return 1;
		}
		
	}
	
	public String toString(){
		return "Users";
	}

	public void receivePM(String message, int fromUID) {
		for(Node n : this){
			
			User u = (User)n;
			
			if(u.getID() == fromUID){
				u.receivePM( u.getName(), message);
			}
		}
	}

	public User getUser(int uID) {
		for(Node n : this){
			User u = (User)n;
			if(u.getID() == uID){
				return u;
			}
		}
		return null;
	}
}