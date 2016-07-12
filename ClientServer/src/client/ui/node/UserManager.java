package client.ui.node;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;
import com.hoosteen.tree.Node;
import com.hoosteen.ui.table.TableComp;
import com.hoosteen.ui.table.TableData;
import com.hoosteen.ui.table.TableDataSource;

import client.User;

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