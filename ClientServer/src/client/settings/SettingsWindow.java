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

import client.Client;
import client.Server;
import shared.net.Message;

public class SettingsWindow extends JDialog{
	
	Settings set; 
	SettingsPanel sp;
	
	public SettingsWindow(JFrame owner){
		
		super(owner, "Settings", true);
		
		set = Client.getClient().getSettings();
		sp = new SettingsPanel();
		
		setLayout(new BorderLayout());
		setSize(640,480);
		
		add(new BottomPanel(), BorderLayout.SOUTH);
		add(sp, BorderLayout.CENTER);
		
		setLocationRelativeTo(owner);
		
	}
	
	class BottomPanel extends JPanel{
		
		JButton ok;
		JButton cancel;
		
		public BottomPanel(){
			
			setLayout(new BorderLayout());
			
			JPanel right = new JPanel();
			
			ok = new JButton(new OKAction());
			cancel = new JButton(new CancelAction());
			
			right.add(ok);
			right.add(cancel);
			
			add(right, BorderLayout.EAST);
			
		}
	}
	
	class CancelAction extends AbstractAction{
		
		public CancelAction(){
			super("Cancel");
		}
		
		public void actionPerformed(ActionEvent e){
			dispose();
		}
		
	}
	
	class OKAction extends AbstractAction{
		
		public OKAction(){
			super("OK");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Client.getClient().getSettings().name = sp.name.getText();
			
			Message m = new Message(Message.Type.UPDATE_NAME);
			m.put(Client.getClient().getSettings().name);
			
			for(Server s : Client.getClient().getServerManager().getServerList()){
				s.sendMessage(m);
			}
			
			dispose();
		}
	}
	
	class SettingsPanel extends JPanel{
		
		JTextField name;
		
		
		public SettingsPanel(){	
			
			JPanel left = new JPanel();
			JPanel right = new JPanel();
			JPanel both = new JPanel(new BorderLayout());
			both.add(left, BorderLayout.WEST);
			both.add(right, BorderLayout.CENTER);			
			
			name = new JTextField(set.name);
			name.setPreferredSize(new Dimension(150,20));
			
			left.add(new JLabel("Name:"));
			right.add(name);
			
			add(both);
			
		}
	}
}
