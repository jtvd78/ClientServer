package server.file;

import java.net.Socket;

import client.Server;

public class FileTransferConnection {
	
	public enum TransferType{
		DOWNLOAD, UPLOAD
	}
	
	String filepath;
	Socket socket;
	Server server;
	TransferType type;
	
	public FileTransferConnection(Server server, TransferType type, String filepath){
		this.server = server;
		this.type = type;
		this.filepath = filepath;
		
		//Connect to server and create socket
	}
	
	public void startTransfer(){
		
	}
	
	
	
	
}