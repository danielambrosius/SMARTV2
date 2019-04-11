package tests;

import junit.framework.TestCase;
import smrt2.Experiment;
import smrt2.Model;


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
	
}
