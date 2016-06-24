package shared.net;

import java.io.Serializable;

public class FileData implements TableData, Serializable{
	
	String name;
	long size;
	boolean directory;
	
	public FileData(String name, long size, boolean directory){
		this.name = name;
		this.size = size;
		this.directory = directory;
	}	
	
	@Override
	public String[] getTableHeaders() {
		return new String[]{"Name", "Size", "Directory"};
	}
	@Override
	public String[] getTableValues() {
		return new String[]{name,formatFileSize(size), Boolean.toString(directory)};
	}
	
	private static final String[] prefixes = new String[]{"kB", "MB", "GB", "TB"};
	
	public static String formatFileSize(long filesize){
		
		int ctr = 0;
		while(filesize > 1024){
			ctr++;
			filesize /= 1024;
		}
		
		return filesize + " " +prefixes[ctr];
	}

	public String getName() {
		return name;
	}

	public int getColumnCount() {
		return 3;
	}
}