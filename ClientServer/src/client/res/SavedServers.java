package client.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import client.ServerSettings;

public class SavedServers {
	
	private File serversFile;
	private String filepath = System.getProperty("user.home") + File.separator + "." + client.res.Strings.programName + File.separator + client.res.Strings.version + File.separator;
	
	private ArrayList<ServerSettings> savedServers;
	
	public void addServer(ServerSettings ss){
		savedServers.add(ss);
		saveServers();
	}
	
	public ServerSettings[] getSavedServers(){
		
		if(savedServers == null){
			loadServers();
		}
		
		//Convert object array to ServerSettings array
		
		Object[] servers = savedServers.toArray();
		ServerSettings[] output = new ServerSettings[servers.length];
		for(int c = 0; c < servers.length; c++){
			output[c] = (ServerSettings) servers[c];
		}
		
		return output;
		
	}

	void loadServers(){
		serversFile = new File(filepath + "Servers.dat");
		
		//Checks to make sure the Servers.dat file exists. If not, it creates it. 
		if(!serversFile.exists()){
			System.out.println("Servers does not Exist. Creating Server File");
			new File(filepath).mkdirs();
			makeServersFile();
		}
		
		//Reads the Servers file.
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serversFile));
			savedServers  = (ArrayList<ServerSettings>) ois.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}		
	
	void makeServersFile() {
		try {
			serversFile.delete();
			serversFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		savedServers = new ArrayList<ServerSettings>();
		savedServers.add(new ServerSettings("Hoosteen.no-ip.biz","User","Password",31415,"Default Server")); //Default Server
		saveServers();
	}

	void saveServers() {
		try {
			new ObjectOutputStream(new FileOutputStream(serversFile)).writeObject(savedServers);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}