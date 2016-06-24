package server.file;

import java.io.File;

import server.ui.Console;

public class FileHandler{
	
	public String root;
	
	public FileHandler(String root){
		this.root = root; 
	}
	
	public synchronized String[] getFileList(String path){
		Console.out.println(path);
		File dir = new File(root + path);
		if(dir.isDirectory()){
			return dir.list();
		}else{
			return null;
		}
	}
}
