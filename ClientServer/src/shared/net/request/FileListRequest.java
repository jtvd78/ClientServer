package shared.net.request;

import server.ServerStart;
import server.net.ServerMessageHandler;
import shared.net.response.FileListResponse;
import shared.net.response.MessageResponse;

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