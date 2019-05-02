package smrt2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TableWriter {
	
	public TableWriter(SmartTableModel tableModel, String filePath, String seperator) {
		exportToCSV(tableModel,filePath, seperator);
	}

	/**
	 * used to save the results in the table to a file that is readable by other programs
	 * @param model is the a table model containing the data to be saved
	 * @param pathToExportTo is the location where to save the program with the name of the file
	 * @param seperator is the seperator of columns that is used in the file
	 * @return is a boolean that represents if the data file is saved 
	 */
	public boolean exportToCSV(SmartTableModel model,
	        String pathToExportTo, String seperator) {

	    try {

	        FileWriter csv = new FileWriter(new File(pathToExportTo));

	        for (int i = 0; i < model.getColumnCount(); i++) {
	            csv.write(model.getColumnName(i) + seperator);
	        }

	        csv.write("\n");

	        for (int i = 0; i < model.getRowCount(); i++) {
	            for (int j = 0; j < model.getColumnCount(); j++) {
	                csv.write(model.getValueAt(i, j).toString() + seperator);
	            }
	            csv.write("\n");
	        }

	        csv.close();
	        return true;
	    } catch (IOException e) {
	    	return false;
	    }
	}
}
