package shared.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{
	
	boolean serverResponded = false;
	boolean timedOut = false;
	
	public enum Type{
		LOGIN, PM, INCORRECT_LP,CORRECT_LP, CHAT, INCORRECT_VERSION, GET_FILELIST, SEND_FILELIST, ADD_USER, REMOVE_USER, GET_FILE, SEND_FILE, UPDATE_NAME
	}
	
	private int messageID;
	public Type type;
	List<Object> list;
	
	private Message messageResponse;
	
	public Message(Type type){
		this.type = type;
		list = new ArrayList<Object>();
	}
	
	public Object get(int index){
		return list.get(index);
	}

	public void put(Object o) {
		list.add(o);
	}
	
	public int getLength(){
		return list.toArray().length;
	}

	public void waitForResponse() {
		long start = System.currentTimeMillis();
		
		System.out.println("Started Waiting");
		
		while(!serverResponded){
			
			System.out.print("");
			
			if (System.currentTimeMillis() - start > 5000){
				timedOut = true;
				break;
			}
		}
		
		System.out.println(System.currentTimeMillis() - start);
		
	}
	
	public void setResponse(Message m){
		System.out.println("setResponse" + m.type.toString());
		serverResponded = true;
		messageResponse = m;
	}
	
	public Message getResponse(){
		return messageResponse;
	}
	
	public void setID(int ID){
		messageID = ID;
	}
	
	public int getID(){
		return messageID;
	}
}