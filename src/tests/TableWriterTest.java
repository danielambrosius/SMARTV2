package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import junit.framework.TestCase;
import smrt2.SmartTableModel;
import smrt2.TableWriter;

public class TableWriterTest extends TestCase {	
	
	public List<String> makeColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("State");
		return columnNames;
	}
	
	
	public void testCreateTableWriter() {
		SmartTableModel tm = new SmartTableModel(makeColumnNames(),"name");
		TableWriter tb = new TableWriter(tm, "./data/csvTestFile.csv", ",");
		assertNotNull(tb);
	}
	
	public void testExportToCSV() {
		String testPath = "./data/csvTestFile.csv";
		SmartTableModel tm = new SmartTableModel(makeColumnNames(),"name");
		Double[] row1 = {1.9,2.5};
		Double[] row2 = {2.2,5.6};
		tm.AddRow(row1);
		tm.AddRow(row2);
		String expected = "Time,State,\n" + 
				"1.9,2.5,\n2.2,5.6,";
		
		TableWriter tb = new TableWriter(tm, testPath, ",");
		try {
			BufferedReader read = new BufferedReader(new FileReader(testPath));
			String actual = read.lines().collect(Collectors.joining("\n"));
			assertEquals(expected, actual);
			//read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
