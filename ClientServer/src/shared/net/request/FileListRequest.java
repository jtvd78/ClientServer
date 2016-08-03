package shared.net.request;

import server.ServerStart;
import server.net.ServerMessageHandler;
import shared.net.MessageRequest;
import shared.net.MessageResponse;
import shared.net.response.FileListResponse;

public class FileListRequest extends MessageRequest{
	
	String path;
	
	public FileListRequest(String path){
		
		this.path = path;
		
	}

	@Override
	public MessageResponse handle(ServerMessageHandler serverMessageHandler) {

		MessageResponse response = new FileListResponse(path, ServerStart.mainServer.getFileHandler().getFileList(path));
		
		return response;		
	}
}