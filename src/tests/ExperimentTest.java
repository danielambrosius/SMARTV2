package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.Experiment;
import smrt2.Model;
import smrt2.SmartTableModel;

public class ExperimentTest extends TestCase {
	private Model m;
	private Experiment e;
	
	public void testExperimentCreation() {
		m = new Model();
		e = new Experiment(m);
		assertNotNull(e);
	}
	
	
	public void testGetValueFromParameter() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos=e.getParameterPosition("k");
		double got = e.getParameterValue(pos);
		assertEquals(1.0, got);
	}
	
	public void testChangeParameterValue() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos = e.getParameterPosition("k");
		e.setParameterValue(pos, 1.96);
		double got = e.getParameterValue((e.getParameterPosition("k")));
		assertEquals(1.96, got);
	}
	public void testGetInitialFromState() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos=e.getStatePosition("A");
		double got = e.getStateValue(pos);
		assertEquals(1.0, got);
	}
	
	public void testChangeInitialStateValue() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos = e.getStatePosition("A");
		e.setStateValue(pos, 25.09);
		double got = e.getStateValue((e.getStatePosition("A")));
		assertEquals(25.09, got);
	}
	
	public void testReconstuctFormulas(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		e = new Experiment(m);
		
		String[] actual = e.reconstructFormulas();
		String[] expected = {"P[0]*((S[1]+S[2])+P[0])", "S[2]-S[1]+P[1]"};
		assertEquals(Arrays.asList(expected), Arrays.asList(actual));
	}

	public void testReconstuctFormulasTime(){
		Model m = new Model("Name");
		m.addOde("C", "x+b");
		m.addOde("A", "k1*((A+t+t+t+B)+k1)");
		m.addOde("B", "B-A+k2");
		e = new Experiment(m);
		
		String[] actual = e.reconstructFormulas();
//		System.out.println(actual[0]);
//		System.out.println(actual[1]);
//		System.out.println(actual[2]);
		String[] expected = {"P[0]+P[1]", "P[2]*((S[2]+S[0]+S[0]+S[0]+S[3])+P[2])", "S[3]-S[2]+P[4]"};
		assertTrue(Arrays.equals(expected, actual));
	}
	

	

	public void testReconstuctFormulas2(){
		// testing for a known bug
		Model m = new Model("Name");
		m.addOde("A", "2+k1");
		m.addOde("B", "k1+2");
		m.addOde("c", "(2+k1)*2");
		e = new Experiment(m);
		
		String[] actual = e.reconstructFormulas();
		String[] expected = {"2+P[0]", "P[0]+2", "(2+P[0])*2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(actual));
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
}
