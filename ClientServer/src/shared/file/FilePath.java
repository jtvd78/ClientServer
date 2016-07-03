package shared.file;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FilePath implements Serializable{	
	
	
	public String lastString = "NeverSet";
	ArrayList<String> pathList;
	
	public FilePath(){
		pathList = new ArrayList<String>();
	}

	public void goUp(){		
		try{
			pathList.remove(pathList.size()-1);
		}catch(ArrayIndexOutOfBoundsException e){
			//Do nothing. Reached the root of the path.
		}
	}
	
	public void add(String s){
		pathList.add(s);
		lastString = s;
	}
	
	@Override
	public String toString(){
		String s = File.separator;
		
		for(String ss : pathList){
			s += ss;
			s += File.separator;
		}
		return s;
	}
	
}
