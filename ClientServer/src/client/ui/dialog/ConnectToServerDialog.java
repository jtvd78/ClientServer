package client.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ClientStart;
import client.ServerSettings;
import client.ui.ClientFrame;
import client.ui.node.Server;

public class ConnectToServerDialog extends JDialog{
	
	public ConnectToServerDialog(JFrame owner){
		super(owner, "Connect to a Server", true);
		
		setLayout(new BorderLayout());
		setResizable(false);
		
		add(new InputPanel(), BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(owner);
	}
	
	public ConnectToServerDialog(JFrame owner, ServerSettings ss){
		super(owner, "Connect to a Server", true);
		
		setLayout(new BorderLayout());
		setResizable(false);
		
		add(new InputPanel(), BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(owner);
		
		connectToServer(ss);
	}
	
	void connectToServer(ServerSettings ss){
		ConnectPanel cp = new ConnectPanel();
		
		
		getContentPane().removeAll();
		getContentPane().add(cp, BorderLayout.CENTER);
		validate();
		repaint();
		
		cp.connect(ss);
	}
	
	public class ConnectPanel extends JPanel{
		
		JLabel text;
		
		public ConnectPanel(){
			setLayout(new BorderLayout());
			text = new JLabel();
			text.setHorizontalAlignment(SwingConstants.CENTER);
			add(text, BorderLayout.CENTER);
		}
		
		public void setText(String str){
			text.setText(str);
		}
		
		public void connect(ServerSettings ss){
			setText("Connecting...");
			Server s = new Server(ss);
			
			try {
				
				ClientStart.getClient().addServer(s);		
				s.connect();
				
				
				setText("Connected");
			}catch(ConnectException e){
				setText("Could not connect to server. Connection Refused.");
			}catch(UnknownHostException e){
				setText("The server address was invalid");
			}catch (IOException e) {
				setText("There was an error connecting to the server \"" + ss.name + "\"");
				e.printStackTrace();
			}
		}
	}
	
	public class InputPanel extends JPanel{
		
		JTextField name;
		JTextField address;
		JTextField login;
		JTextField password;
		JTextField port;

		public InputPanel(){
			
			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(10,10,10,10));
			
			JPanel left = new JPanel(new GridLayout(0,1));
			JPanel right = new JPanel(new GridLayout(0,1));
			JPanel both = new JPanel(new BorderLayout());
			both.add(left, BorderLayout.WEST);
			both.add(right, BorderLayout.CENTER);
			
			name = new JTextField("Server Name");
			address = new JTextField("localhost");
			login = new JTextField("User");
			password = new JTextField("Password");	
			port = new JTextField(shared.Settings.defaultPort + "");
			
			left.add(new JLabel("Name:"));
			right.add(name);
			left.add(new JLabel("Address:"));
			right.add(address);
			left.add(new JLabel("Login:"));
			right.add(login);
			left.add(new JLabel("Password:"));
			right.add(password);
			left.add(new JLabel("Port: "));
			right.add(port);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(new JButton(new ConnectAction()));
			buttonPanel.add(new JButton(new CancelAction()));
			
			
			name.setPreferredSize(new Dimension(200,20));
			
			add(both, BorderLayout.CENTER);
			add(buttonPanel, BorderLayout.SOUTH);
		}	
		
		class ConnectAction extends AbstractAction{
			
			public ConnectAction(){
				super("Connect");
			}

			@Override
			public void actionPerformed(ActionEvent e){
				
				String serverName = name.getText();
				String serverAddress = address.getText();
				String serverLogin = login.getText();
				String serverPassword = password.getText();
				int serverPort;
				
				//Checks the Port to make sure it is valid. 
				//Notifies the user, and returns if the port is invalid.	
				try{
					serverPort = Integer.parseInt(port.getText());
					if(serverPort < 0 || serverPort > 65535){
						JOptionPane.showMessageDialog(ClientStart.getClient().getFrame(),"The entered port is not valid. Please enter a number from 0 to 65535");
						return;
					}
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(ClientStart.getClient().getFrame(),"The entered port is not a number. Please insert a valid number");
					return;
				}
				
				ServerSettings ss = new ServerSettings(serverAddress,serverLogin,serverPassword,serverPort,serverName);
				
				
				connectToServer(ss);
			}		
		}
		
		class CancelAction extends AbstractAction{
			
			public CancelAction(){
				super("Cancel");
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}		
		}
	}
}