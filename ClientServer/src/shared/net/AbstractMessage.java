package shared.net;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable{	
	
	private int messageID = -1;
	
	public void setID(int ID){
		messageID = ID;
	}
	
	public int getID(){
		return messageID;
	}
}