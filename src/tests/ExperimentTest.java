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
	
}
