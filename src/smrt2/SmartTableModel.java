package smrt2;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SmartTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Double[]> data = new ArrayList<Double[]>();
	private List<String> colNames = new ArrayList<String>();
	private String name;
	
	public SmartTableModel(List<String> colNames, String name){
		this.colNames.add("Time");
		this.colNames.addAll(colNames);
		this.name = name;
	}
	
	/**
	 * Returns the name of the SmartTableModel.
	 * @return: the names.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns data from a specified column
	 * @param i: the index of the column.
	 * @return: the data in that column.
	 */
	public Double[] getColumnAt(int i) {
		Double[] columnData = new Double[getRowCount()];
		for (int j = 0; j < getRowCount(); j++) {
			columnData[j] = (Double) getValueAt(j, i);
		}
		return columnData;
	}
	
	/**
	 * Returns data from a specified row
	 * @param i: the index of the row.
	 * @return: the data in that row.
	 */
	public Double[] getRowAt(int i){
		return data.get(i);
	}
	
	/**
	 * Used to dynamically add data to the SmartTableModel
	 * @param row: the row of data to be added.
	 */
	public void AddRow(Double[] row){
		data.add(row);
		fireTableRowsInserted(getRowCount(), getRowCount());
	}

	@Override
	public int getColumnCount() {
		return colNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int i){
		return this.colNames.get(i);
	}

	/**
	 * Used to get the names of the columns
	 * @return: array of column names.
	 */
	public String[] getColumnNames() {
		String[] newToReturn = new String[getColumnCount()];
		for(int i = 0; i < getColumnCount(); i++) {
			newToReturn[i] = getColumnName(i);
		}
			
		return newToReturn;
	}


}
