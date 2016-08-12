package client.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hoosteen.settings.SettingsWindow;

import client.ClientStart;
import client.ui.node.Server;
import shared.net.MessageRequest;
import shared.net.request.UpdateNameRequest;

public class ClientSettingsWindow extends SettingsWindow{	
	
	ClientSettings settings;
	JTextField nameTextField;
	
	public ClientSettingsWindow(JFrame owner, ClientSettings settings){		
		super(owner, settings);		
		this.settings = settings;
		
		add(new SettingsPanel(), BorderLayout.CENTER);		
	}
	
	class SettingsPanel extends JPanel{
		
		public SettingsPanel(){	 
			
			JPanel left = new JPanel();
			JPanel right = new JPanel();
			JPanel both = new JPanel(new BorderLayout());		
			
			nameTextField = new JTextField(settings.name);
			nameTextField.setPreferredSize(new Dimension(150,20));
			
			left.add(new JLabel("Name:"));
			right.add(nameTextField);
			
			both.add(left, BorderLayout.WEST);
			both.add(right, BorderLayout.CENTER);	
			
			add(both);
			
		}
	}

	@Override
	public void refreshOptions() {
		nameTextField.setText(settings.name);
	}

	@Override
	protected void updateSettings() {
		ClientStart.getClient().getSettings().name = nameTextField.getText();
		
		MessageRequest req = new UpdateNameRequest(ClientStart.getClient().getSettings().name);
		
		for(Server s : ClientStart.getClient().getServerList()){
			s.sendMessage(req);
		}
	}
}