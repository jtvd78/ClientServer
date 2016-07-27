package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ServerSettings implements Serializable{
	private static final long serialVersionUID = -3376609133416416194L;
	
	public int port;
	public String address;
	public String name;
	public String login;
	public String password;
	
	public ServerSettings(String address, String login, String password, int port, String name){
		this.address = address;
		this.login = login;
		this.password = password;
		this.port = port;
		this.name = name;
	}
}