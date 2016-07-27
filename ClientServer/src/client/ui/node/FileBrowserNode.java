package client.ui.node;

import javax.swing.JComponent;

import com.hoosteen.tree.ComponentNode;

import client.ui.FileBrowser;

public class FileBrowserNode extends ComponentNode{
	
	Server server;
	FileBrowser comp;
	
	public FileBrowserNode(Server server){
		this.server = server;
		comp = new FileBrowser(server);
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	public String toString(){
		return "File Browser";
	}
	
	public void dataChanged(){
		comp.dataChanged();
	}
}