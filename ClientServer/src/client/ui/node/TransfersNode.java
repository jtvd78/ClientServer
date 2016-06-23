package client.ui.node;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hoosteen.tree.ComponentNode;

public class TransfersNode extends ComponentNode{
	
	JComponent comp;
	
	public TransfersNode(){
		JPanel pan = new JPanel();
		pan.add(new JLabel("Under Construction"));
		comp = pan;
	}

	@Override
	public JComponent getComponent() {
		return comp;
	}
	
	public String toString(){
		return "Transfers";
	}
}