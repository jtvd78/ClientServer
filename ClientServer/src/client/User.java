package client;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import tree.NodeObject;

public class User implements NodeObject{
	
	int uID;
	private String name;	
	private Server server;
	private UserPanel panel;
	
	public User(String name, Server server, int uID){
		this.name = name;
		this.server = server;
		this.uID = uID;
		panel = new UserPanel(this);
	}	
	
	public String getName(){
		return name;
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public void PM(String s){
		panel.updateChat(s);
	}
	
	
	class UserPanel extends JPanel{
		
		User u;
		
		Chat chat;
		InputPanel input;
		
		public UserPanel(User u){
			super(new BorderLayout());
			this.u = u;
			
			chat = new Chat();
			input = new InputPanel();
			
			JSplitPane vert = new JSplitPane(JSplitPane.VERTICAL_SPLIT,chat, input);
			vert.setResizeWeight(1);
			
			add(vert,BorderLayout.CENTER);			
		}
		
		private class InputPanel extends JPanel{
			
			JTextArea input;
			
			public InputPanel(){
				super(new BorderLayout());
				
				//Input Text Area
				input = new JTextArea();
				input.setEditable(true);
				input.setMargin(new Insets(10,10,10,10));
				input.setWrapStyleWord(true);
				input.setLineWrap(true);
				Action action = new AbstractAction(){
					public void actionPerformed(ActionEvent arg0) {
						send(input.getText());
						input.setText("");
					}
				};
				KeyStroke keyStroke = KeyStroke.getKeyStroke("ENTER");
				InputMap im = input.getInputMap();
				input.getActionMap().put(im.get(keyStroke), action);
				
				//Send Button
				JButton send = new JButton("Send");
				send.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(input.getText().equals("")){
							return;
						}
						send(input.getText());
						input.setText("");
					}
				});
				
				//Input Scroll Pane
				JScrollPane inputScrollPane = new JScrollPane(input);

				add(inputScrollPane);
				add(send,BorderLayout.EAST);
			}
			
			private void send(String str){
				server.sendPM(str,uID);
				updateChat("<" + Client.getClient().getSettings().name + "> " + str);
			}
		}
		
		public void updateChat(String s){
			chat.updateChat(s);
		}
		
		private class Chat extends JPanel{
			
			JTextArea chat;
			
			public Chat(){
				super(new BorderLayout());
				
				//Main Chat Text Area
				chat = new JTextArea();
				chat.setEditable(false);
				chat.setMargin(new Insets(10,10,10,10));
				chat.setWrapStyleWord(true);
				chat.setLineWrap(true);
				
				//JScroll Panes
				JScrollPane chatScrollPane = new JScrollPane(chat);
				add(chatScrollPane,BorderLayout.CENTER);
			}
			
			void updateChat(String s){
				chat.append(s+"\n");
				chat.setCaretPosition(chat.getDocument().getLength());
			}		
		}
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean hasNotification() {
		// TODO Auto-generated method stub
		return false;
	}
}