package client.ui.node;

import com.hoosteen.tree.Node;

public class MainTree extends com.hoosteen.tree.Tree{

	public MainTree(Node root) {
		super(root);
		root.setExpanded(true);		
	}
}