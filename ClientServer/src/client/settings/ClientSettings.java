package client.settings;

import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hoosteen.schedule.settings.ScheduleSettings;
import com.hoosteen.settings.Settings;
import com.hoosteen.settings.SettingsWindow;

import client.ClientStart;



public class ClientSettings extends Settings<ClientSettingsWindow>{
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final String programName = "JVDX";

	
	public String name;	
	
	public ClientSettings(){
		name = requestName();
	}
	
	public static String randomName(){
		char[] list = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l',
				'm','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'
				
		};
		String s = "";
		
		int length = 10;
		for(int c = 0; c < length; c++){
			
			int place = (int)(Math.random()*list.length);
			boolean chance = (int)(Math.random()*2) == 0;
			
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

	@Override
	protected ClientSettingsWindow getSettingsWindow(JFrame owner) {
		return new ClientSettingsWindow(owner, this);
	}
	
		
}