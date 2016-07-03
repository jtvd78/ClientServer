package server.file;

import java.io.File;
import java.util.ArrayList;

import server.ui.Console;
import shared.file.FileData;
import shared.file.FilePath;

public class FileHandler{
	
	public String root;
	
	public FileHandler(String root){
		this.root = root; 
	}
	
	public synchronized FileData[] getFileList(String path){
		Console.out.println(path);
		File dir = new File(root + path);	
		
		if(dir.isDirectory()){
			
			ArrayList<FileData> files = new ArrayList<FileData>();
			
			for(File f : dir.listFiles()){
				files.add(new FileData(f.getName(), f.length(), f.isDirectory()));
			}		
			
			return files.toArray(new FileData[0]);
		}else{
			return null;
		}
		
		
		
	}
}
