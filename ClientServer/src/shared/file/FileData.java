package shared.file;

import java.io.Serializable;

import com.hoosteen.graphics.table.TableData;

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
		return new String[]{"Name", "Size", "Type"};
	}
	@Override
	public String[] getTableValues() {
		return new String[]{name, directory ? "" : formatFileSize(size), directory ? "Folder" : "File"};
	}
	
	private static final String[] prefixes = new String[]{"B","kB", "MB", "GB", "TB"};
	
	public static String formatFileSize(long fileSizeLong){
		
		float filesize = fileSizeLong;
		
		int ctr = 0;
		while(filesize > 1024){
			ctr++;
			filesize /= 1024.0;
		}
		
		return String.format("%.2f", filesize) + " " +prefixes[ctr];
	}

	public String getName() {
		return name;
	}

	public int getColumnCount() {
		return 3;
	}
}