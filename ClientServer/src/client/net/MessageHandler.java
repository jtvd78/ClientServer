package client.net;

import java.util.ArrayList;

import client.ClientStart;
import client.ui.node.Server;
import client.ui.node.User;
import shared.net.MessageRequest;
import shared.net.MessageResponse;

public class MessageHandler {
	
	Server s;
	
	private int ID = 0;
	ArrayList<MessageRequest> pendingMessages = new ArrayList<MessageRequest>();
	
	public MessageHandler(Server s){
		this.s = s;
	}
	
	public void addPendingMessage(MessageRequest request){
		pendingMessages.add(request);
	}
	
	public void handleMessage(MessageResponse response){
		
		int messageID = response.getID();
		
		System.out.println("ID : " + response.getID() + " : " + response.getClass());
		
		
		//Only if this message is a response to another message
		if(messageID != -1){
			for(MessageRequest pm : pendingMessages){
				if(pm.getID() == messageID){
					pm.setResponse(response);
					break;
				}
			}
		}else{
			response.handle(s);
		}		
	}

	public int getNextID() {	
		return ++ID;
	}
}