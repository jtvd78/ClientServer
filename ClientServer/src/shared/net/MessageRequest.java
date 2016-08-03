package shared.net;

import server.net.ServerMessageHandler;

public abstract class MessageRequest extends AbstractMessage{
	
	boolean timedOut = false;
	boolean serverResponded = false;
	
	private MessageResponse messageResponse;
	
	public MessageResponse waitForResponse() {
		long start = System.currentTimeMillis();
		
		while(!serverResponded){
			
			if (System.currentTimeMillis() - start > 5000){
				timedOut = true;
				break;
			}
		}
		
		return messageResponse;		
	}
	
	public void setResponse(MessageResponse m){
		serverResponded = true;
		messageResponse = m;
	}

	public abstract MessageResponse handle(ServerMessageHandler serverMessageHandler);
}