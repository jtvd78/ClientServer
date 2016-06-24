package shared.net;

public interface TableActionListener {
	
	public void rowSelected(int row, TableData data);
	public void rowDoubleClicked(int row, TableData data);
	public void rowRightClicked(int row, TableData data);
	
	
}
