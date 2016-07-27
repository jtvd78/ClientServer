package shared.net.request;

import server.net.ServerMessageHandler;
import shared.net.AbstractMessage;
import shared.net.response.MessageResponse;

public abstract class MessageRequest extends AbstractMessage{
	
	boolean timedOut = false;
	boolean serverResponded = false;
	
	private MessageResponse messageResponse;
	
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
	
	public void setResponse(MessageResponse m){
		serverResponded = true;
		messageResponse = m;
	}
	
	public MessageResponse getResponse(){
		return messageResponse;
	}

	public abstract MessageResponse handle(ServerMessageHandler serverMessageHandler);

}
