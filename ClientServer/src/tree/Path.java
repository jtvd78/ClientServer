package tree;

import java.io.File;
import java.util.ArrayList;

public class Path {

	ArrayList<String> pathList = new ArrayList<String>();
	
	@Override
	public String toString(){
		String s = File.separator;
		for(String ss : pathList){
			s += ss;
			s += File.separator;
		}
		return s;
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
	}
}