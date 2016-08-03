package shared.net;

import client.ui.node.Server;

public abstract class MessageResponse extends AbstractMessage{
	
	public abstract void handle(Server s);

}
