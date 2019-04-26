package smrt2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CSVWriter {
	
	public CSVWriter(SmartTableModel tableModel, String filePath) {
		exportToCSV(tableModel,filePath);
	}

	public boolean exportToCSV(SmartTableModel model,
	        String pathToExportTo) {

	    try {

	        FileWriter csv = new FileWriter(new File(pathToExportTo));

	        for (int i = 0; i < model.getColumnCount(); i++) {
	            csv.write(model.getColumnName(i) + ",");
	        }

	        csv.write("\n");

	        for (int i = 0; i < model.getRowCount(); i++) {
	            for (int j = 0; j < model.getColumnCount(); j++) {
	                csv.write(model.getValueAt(i, j).toString() + ",");
	            }
	            csv.write("\n");
	        }

	        csv.close();
	        return true;
	    } catch (IOException e) {
	    	//TODO add error message
	    	return false;
	    }
	}
}
