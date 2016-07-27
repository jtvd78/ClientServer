package client.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SettingsLoader {
	
	File settingsFile;
	String settingsPath = System.getProperty("user.home") + File.separator + "." + client.settings.Settings.programName + File.separator + shared.Settings.version + File.separator;

	public Settings loadSettings() {
		settingsFile = new File(settingsPath + "Settings.dat");
		
		if(!settingsFile.exists()){
			System.out.println("Settings does not Exist. Creating Settings File");
			new File(settingsPath).mkdirs();
			makeSettings();
		}
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(settingsFile));
			Settings s = (Settings)(ois.readObject());
			ois.close();
			return s;
		}catch(InvalidClassException e){
			makeSettings();
			return loadSettings();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			makeSettings();
			
			try {
				if(ois != null){
					ois.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return loadSettings();
		}
		
		return null;
	}
	
	public void makeSettings(){
		
		try {
			settingsFile.delete();
			settingsFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		saveSettings(new Settings());
		
	}
	
	public void saveSettings(Settings s){
		try {
			new ObjectOutputStream(new FileOutputStream(settingsFile)).writeObject(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}