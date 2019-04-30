package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import smrt2.SmartTableModel;

public class SmartTableModelTest extends TestCase {
	public void testSetUp(){
		List<String> colNames = new ArrayList<String>();
		colNames.add("X");
		colNames.add("Y");
		SmartTableModel myTable = new SmartTableModel(colNames, "table name");
		
		String[] expected = {"Time", "X", "Y"};
		for (int i = 0; i < myTable.getColumnCount(); i++) {
			assertEquals(expected[i], myTable.getColumnName(i));
		}
	}
	
	public void testGetName() {
		List<String> colNames = new ArrayList<String>();
		colNames.add("X");
		colNames.add("Y");
		String expectedName = "table name";
		SmartTableModel myTable = new SmartTableModel(colNames, expectedName);
		assertEquals(expectedName, myTable.getName());
	}
	
	public void testManipulations(){
		List<String> colNames = new ArrayList<String>();
		colNames.add("X");
		colNames.add("Y");
		SmartTableModel myTable = new SmartTableModel(colNames, "table name");
		//Add data
		Double[] data1 = {0., 10., 50.};
		myTable.AddRow(data1);
		Double[] data2 = {1., 20., 100.};
		myTable.AddRow(data2);
		Double[] data3 = {2., 25., 80.};
		myTable.AddRow(data3);
		Double[] data4 = {3., 45., 70.};
		myTable.AddRow(data4);
		
		//Test manipulations
		assertEquals(3, myTable.getColumnCount());
		assertEquals(4, myTable.getRowCount());
		assertEquals(80., myTable.getValueAt(2, 2));
		
		//Test getRowAt
		Double[] expected = {3., 45., 70.};
		assertEquals(Arrays.asList(expected), Arrays.asList(myTable.getRowAt(3)));
	}
}
