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
		m.addParameter("k");
		e = new Experiment(m);

		double got = e.getParameters().get(e.getParameterPosition("k")).getValue();
		assertEquals(0.0, got);
	}
	
	public void testChangeParameterValue() throws Exception {
		m = new Model();
		m.addParameter("k");
		e = new Experiment(m);
		
		e.getParameters().get(e.getParameterPosition("k")).setValue(1.96);
		double got = e.getParameters().get(e.getParameterPosition("k")).getValue();
		assertEquals(1.96, got);
		
	}
}
