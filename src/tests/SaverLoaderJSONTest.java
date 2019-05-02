package tests;

import java.util.Arrays;
import junit.framework.TestCase;
import smrt2.Equation;
import smrt2.Experiment;
import smrt2.Model;
import smrt2.Ode;
import smrt2.SaverLoader;

public class SaverLoaderJSONTest extends TestCase {

	public void testCreateSaverLoaderJSON() {
		SaverLoader mySl= SaverLoader.getInstance();
		assertNotNull(mySl);
	}
	
	public void testSaveLoadOde() {
		String savePath = "./data/test_Json.json";
		Equation expectedOde = new Ode("F","H*R");
		SaverLoader mySl= SaverLoader.getInstance();
		mySl.save(savePath, expectedOde);
		
		Equation observedOde =  (Equation) mySl.load(savePath, Ode.class);
		assertEquals(expectedOde.getLeftHandSide(), observedOde.getLeftHandSide());
		assertEquals(expectedOde.getRightHandSide(), observedOde.getRightHandSide());		
	}
	
	public void testSaveLoadModel() {
		String savePath = "./data/test_JsonModel.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		m.addUnboundParameter("L");
		
		SaverLoader mySl = SaverLoader.getInstance();
		mySl.save(savePath, m);
		
		Model mObserved = (Model) mySl.load(savePath, Model.class);
		assertTrue(m.getDependentVariables().equals(mObserved.getDependentVariables()));
	}
	
	public void testSaveLoadModelWithMixOfAlgAndOde() {
		String savePath = "./data/test_JsonModelWithODEAndAlgEQ.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		m.addAlgEq("R", "x + 5 * k2");
		m.addUnboundParameter("L");
		
		SaverLoader mySl = SaverLoader.getInstance();
		mySl.save(savePath, m);
		
		Model mObserved = (Model) mySl.load(savePath, Model.class);
		for (int i = 0; i < m.getEquationList().size(); i++) {
			assertEquals(m.getEquationAtIndex(i).getClass(), mObserved.getEquationAtIndex(i).getClass());
		}
	}
	
	public void testSaveLoadModelWithUnitsAndDescriptions() {
		String savePath = "./data/test_JsonModelWithUnitsAndDescriptions.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addAlgEq("R", "x + 5 * k2");
		
		String[] keys = {"H", "k2"};
		String[] units = {"m/s", "m^2/(s*K)"};
		String[] desc = {"state H", "k2 param"};
		
		m.addDescriptionToVarTable(keys[0], units[0], desc[0]);
		m.addDescriptionToVarTable(keys[1], units[1], desc[1]);
		
		SaverLoader mySl = SaverLoader.getInstance();
		mySl.save(savePath, m);
		
		Model mObserved = (Model) mySl.load(savePath, Model.class);
		
		for (int i = 0; i < keys.length; i++) {
			String[] observed = mObserved.getDescriptionFromKey(keys[i]);
			
			String obsUnit = observed[0];
			String obsDescription = observed[1];
			assertEquals(units[i], obsUnit);
			assertEquals(desc[i], obsDescription);
		}
		
		
	}
	
	
	public void testSaveLoadExperiment() {
		String savePath = "./data/test_JsonExperiment.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		m.addUnboundParameter("L");
		
		Experiment e = new Experiment(m, "Test experiment for JSON saving");

		SaverLoader mySl = SaverLoader.getInstance();
		
		mySl.save(savePath, e);
		
		Experiment eObs = (Experiment) mySl.load(savePath, Experiment.class);
		Model mObs=eObs.getModel();
		assertTrue(Arrays.equals(e.getTimeValues(), eObs.getTimeValues()));
		assertTrue(e.getName().equals(eObs.getName()));
		for(int i=0; i < m.getParameters().size(); i++) {
			assertEquals(e.getParameterValue(i), eObs.getParameterValue(i));
			assertEquals(m.getParameters().get(i), mObs.getParameters().get(i));
		}
		for(int i=0; i < m.getStates().size(); i++) {
			assertEquals(e.getStateValue(i), eObs.getStateValue(i));
			assertEquals(m.getDependentVariables().get(i), mObs.getDependentVariables().get(i));
		}
	}
}
