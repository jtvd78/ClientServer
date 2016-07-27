package shared.net.response;

import client.ui.node.Server;
import shared.file.FileData;

public class FileListResponse extends MessageResponse {
	
	String path;
	FileData[] fileList;

	public FileListResponse(String path, FileData[] fileList) {
		this.path = path;
		this.fileList = fileList;
	}

	@Override
	public void handle(Server s) {
		// TODO Auto-generated method stub

	}
	
	public FileData[] getFileList(){
		return fileList;
	}
}