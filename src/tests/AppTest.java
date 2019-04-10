package tests;

import junit.framework.TestCase;
import smrt2.App;

public class AppTest extends TestCase {
	public void testSetModelChanged() {
		App a = new App();
		
		
	}
	
	public void testIsModelSaved() {
		App a = new App();
		a.newModel("something");
		a.saveModel("./data/testModelIsSaved.model");
		assertTrue(a.isModelSaved());
		a.handleButtonAddOde("A", "A+B");
		assertFalse(a.isModelSaved());
	}
	
	public void testIsExperimentSaved() {
		App a = new App();
		a.newExperiment();
		a.saveExperiment("./data/testExperimentIsSaved.exp");
		assertTrue(a.isExperimentSaved());
		a.newExperiment();
		assertFalse(a.isExperimentSaved());
		
	}
	

	
}
