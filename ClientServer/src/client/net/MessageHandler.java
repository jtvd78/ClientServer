package client.net;

import java.util.ArrayList;

import client.ClientStart;
import client.ui.node.Server;
import client.ui.node.User;
import shared.net.request.MessageRequest;
import shared.net.response.MessageResponse;

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
	
	/*
	
	public void handleMessage(MessageResponse response){
		int messageID = m.getID();
		System.out.println("ID : " + m.getID() + " : " + m.type);
		
		for(Message pm : pendingMessages){
			if(pm.getID() == messageID){
				pm.setResponse(m);
				return;
			}
		}
		
		switch(m.type){
		case PM:			ClientStart.getClient().PM((String)m.get(0), s, (int)m.get(1));
							
						//	s.getUser((int)m.get(1)).PM("<" + s.getUser((int)m.get(1)).getName() + "> " + (String)m.get(0));
							break;
							
		case CHAT:			s.receiveChat((String)m.get(1), (String)m.get(0));
							break;
							
		case ADD_USER:		s.addUser((String)m.get(0),(int)m.get(1));
							break;
							
		case REMOVE_USER:	s.removeUser((int)m.get(0));
							break;
							
		case UPDATE_NAME:	String name = (String)m.get(0);
							User u = s.getUser((int)m.get(1));
							u.setName(name);
						//	Client.getClient().getFrame().getTree().updateUserName(u,s);
							System.out.println("Update User Name");
							break;
					
		default: System.out.println("Unhandled Message : " + m.type.toString());
			break;
		}
	}
	
	*/

	public int getNextID() {		
		ID++;
		
		System.out.println(ID);
		
		return ID;
	}
}