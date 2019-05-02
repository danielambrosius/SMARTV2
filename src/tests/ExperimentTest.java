package tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import smrt2.Experiment;
import smrt2.Model;
import smrt2.SmartTableModel;

public class ExperimentTest extends TestCase {
	private Model m;
	private Experiment e;
	
	public void testExperimentCreation() {
		m = new Model("");
		e = new Experiment(m);
		assertNotNull(e);
	}
	
	public void testGetValueFromParameter() throws Exception {
		m = new Model("");
		m.addOde("A", "k");
		e = new Experiment(m);
		double got = 0;
		List<String> paramNames = m.getParameters();
		for (int j = 0; j < paramNames.size(); j++) {
			if (paramNames.get(j).equals("k")) {
				got = e.getParameterValue(j);
			}
		}
		assertEquals(1.0, got);
	}
	
	public void testChangeParameterValue() throws Exception {
		m = new Model("");
		m.addOde("A", "k");
		e = new Experiment(m);
		String[] paramValArray = new String[] {"1.96"};
		for (int i = 0; i < paramValArray.length; i++ ) {
			e.setParameterValue(i, Double.parseDouble(paramValArray[i]));
		}
		double got = 0;
		List<String> paramNames = m.getParameters();
		for (int j = 0; j < paramNames.size(); j++) {
			if (paramNames.get(j).equals("k")) {
				got = e.getParameterValue(j);
			}
		}
		assertEquals(1.96, got);
	}
	public void testGetInitialFromState() throws Exception {
		m = new Model("");
		m.addOde("A", "k");
		e = new Experiment(m);
		double got = 0;
		List<String> stateNames = m.getStates();
		for (int j = 0; j < stateNames.size(); j++) {
			if (stateNames.get(j).equals("A")) {
				got = e.getStateValue(j);
			}
		}
		assertEquals(1.0, got);
	}
	
	public void testChangeInitialStateValue() throws Exception {
		m = new Model("");
		m.addOde("A", "k");
		e = new Experiment(m);
		String[] stateValArray = new String[] {"25.09"};
		for (int i = 0; i < stateValArray.length; i++ ) {
			e.setStateValue(i, Double.parseDouble(stateValArray[i]));
		}
		double got = 0;
		List<String> stateNames = m.getStates();
		for (int j = 0; j < stateNames.size(); j++) {
			if (stateNames.get(j).equals("A")) {
				got = e.getStateValue(j);
			}
		}
		assertEquals(25.09, got);
	}
	
	public void testSolverCreation() {
		Model mo = new Model("Name");
		mo.addOde("A", "k1");
		mo.addOde("B", "k2*A");
		Experiment ex = new Experiment(mo, null);
		ex.setParameterValue(0, 1);
		ex.setParameterValue(1, 2);
		ex.setStateValue(0, 0);
		ex.setStateValue(1, 0);
		ex.setTimeFrame(0.0, 10.0, 1.0);
		
		Double[][] expected = {{0.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {2.0, 2.0, 2.0}, {3.0, 3.0, 6.0}, {4.0, 4.0, 12.0}, {5.0, 5.0, 20.0}, {6.0, 6.0, 30.0},
				{7.0, 7.0, 42.0}, {8.0, 8.0, 56.0}, {9.0, 9.0, 72.0}, {10.0, 10.0, 90.0}};
		ex.run();
		SmartTableModel actual = ex.getTableModel();
		
		for (int i = 0; i < actual.getRowCount(); i++) {
			for (int j = 0; j < actual.getColumnCount(); j++) {
				assertEquals(expected[i][j], actual.getValueAt(i, j));
			}
		}
	}
	
	public void testGetTimeValues() {
		Model mo = new Model("Name");
		mo.addOde("A", "k1");
		mo.addOde("B", "k2*A");
		Experiment ex = new Experiment(mo, null);
		ex.setTimeFrame(0, 0.1, 50);
		assertEquals(Arrays.toString(new double[] {0.0,0.1,50.0}),
				Arrays.toString(ex.getTimeValues()));
	}
}
