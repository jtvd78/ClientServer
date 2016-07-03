package server.net;

import server.ServerStart;
import server.ServerUser;
import server.file.FileHandler;
import server.ui.Console;
import shared.file.FilePath;
import shared.net.Message;
import shared.net.Message.Type;

public class ServerMessageHandler {
	
	ServerUser u;
	ServerConnection c;
	ServerStart s;
	FileHandler fileHandler;
	
	public ServerMessageHandler(ServerConnection c){
		s = ServerStart.mainServer;
		this.c = c;
		fileHandler = s.getFileHandler();
	}	
	
	public void handleMessage(Message m){
		Console.out.println(m.type.toString());
		switch(m.type){
		
		case LOGIN:	u = c.createUser((String)m.get(0));
					boolean result = s.login(u, (String)m.get(1),(String)m.get(2),(String)m.get(3), m.getID());
					
					
					if(result){
						
					}
					break;
		case PM:	
					Message send= new Message(Message.Type.PM);
					send.put(m.get(0));
					send.put(u.uID);
					s.sendPMToClient(send,(int)m.get(1));
		
					break;
							
		case CHAT:	s.sendChatToClients(m);
					break;
		case CORRECT_LP:
			break;
		case INCORRECT_LP:
			break;
		case GET_FILELIST: 	String path = (String)m.get(0);
							Message sendd = new Message(Message.Type.SEND_FILELIST);
							
							sendd.put(path);
							sendd.put(fileHandler.getFileList(path));
							sendd.setID(m.getID());
							c.sendMessage(sendd);
			break;
			
		case UPDATE_NAME: 	String name = (String)m.get(0);
							u.setName(name);
							s.updateUserName(name,u.uID);
			
			
		}
	}
	
	//HANDLE MESSAGE
}
