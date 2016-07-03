package client.settings;

import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientStart;



public class Settings implements Serializable{

	public static final int defaultPort = 31415;
	public static SettingsWindow settingsWindow;
	
	public String name;	
	
	public Settings(){
		name = requestName();
	}
	
	public static String randomName(){
		char[] list = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l',
				'm','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'
				
		};
		String s = "";
		
		int length = 10;
		for(int c = 0; c < length; c++){
			
			int place = ((int)((Math.random())*list.length));
			boolean chance = true;
			if(((int) (Math.random()*2)) == 0){
				chance = false;
			}
			char ch = list[place];
			if (place < 26 && chance){
				ch = Character.toUpperCase(ch);
			}
			
			s+=ch;
		}
		return s;
	}
	
	public String requestName(){		
		JPanel jp = new JPanel(new GridLayout(1,2));
		JLabel jl = new JLabel("Enter your nickname");
		JTextField jtf = new JTextField(randomName());
		jp.add(jl);
		jp.add(jtf);
		
		JOptionPane.showMessageDialog(ClientStart.getClient().getFrame(),jp);
		
		String name = jtf.getText();
		if(name.equals("")){
			name = randomName();
		}
		
		return name;
	}
	
	public static void showSettingsWindow(){
		
		if(settingsWindow == null){
			settingsWindow = new SettingsWindow(ClientStart.getClient().getFrame());
			settingsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		settingsWindow.setVisible(true);
	}	
}