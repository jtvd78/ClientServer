package shared.net.response;

import client.ui.node.Server;
import shared.net.AbstractMessage;

public abstract class MessageResponse extends AbstractMessage{
	
	public abstract void handle(Server s);

}
