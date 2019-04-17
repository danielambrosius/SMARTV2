package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import smrt2.Model;
import smrt2.Ode;

public class ModelTest extends TestCase {
	
	
	public void testModelCreation() {
		Model m = new Model();
		assertNotNull(m);
	}
	
	public void testModelHasName() {
		Model m = new Model("Name");
		assertEquals("Name", m.getName());
	}
	
	public void testModelSetName() {
		Model m = new Model("Name");
		m.setName("Tim");
		assertEquals("Tim", m.getName());
	}
	
	public void testAddOde() {
		Model m = new Model("Name");
		Ode testODE = new Ode("A", "k");
		String expected = testODE.toString();
		
		m.addOde("A", "k");
		
		String actual = m.getOdeList().get(0).toString();
		
		assertEquals(expected, actual);	
	}
	
	public void testDisplayOdes()  {

		Model m = new Model("Name");
		m.addOde("A", "k1");
		m.addOde("B", "k2");
		m.addOde("C", "**++++///BC");
		String[][] actual = m.displayOdeList();
		String[][] expected = {{"dA/dt", "k1"}, {"dB/dt", "k2"}, {"dC/dt", "**++++///BC (this is bold)"}}; 
		System.out.println(actual[2][1]);
		System.out.println("help");
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
			
		}
	}
	public void testAddingDuplicateStates() {
		//TODO test duplicate states
	}
	public void testStartNewModel() throws Exception {
		Model m = new Model("Name");
		m.addOde("A", "k1");
		m.setName("Kees");
		m.startNewModel();
		
		assertEquals(null, m.getName());
		assertEquals("[]" , m.getOdeList().toString());
		assertEquals("[]" , m.getStates().toString());
		assertEquals("[]" , m.getParameters().toString());
	}
	public void testFindParameters() {
		Model m = new Model("Name");
		m.addOde("A", "k1*A+B");
		m.addOde("B", "B-A+k2-k1");
		m.getParameters();
		
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("k1","k2"));
		
		assertEquals(expected, m.getParameters());
		}
	
	public void testGetStates(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		
		List<String> actual = m.getStates();
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("A","B"));
		assertEquals(expected, actual);
	}
	
	public void testGetParameters(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		
		List<String> actual = m.getParameters();
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("k1","k2"));
		assertEquals(expected, actual);
	}
	
	
	
//public void testBuildParamDict() {
//Model m = new Model("Name");
//m.addOde("A", "k1*A+B");
//m.addOde("B", "B-A+k2");
//
//Map<String, String> paramDict = m.buildParamDict();
//
//String expected = "{k1=P[0], k2=P[1]}";
//assertEquals(expected, paramDict.toString());
//}
//
//public void testBuildStatesDict() {
//Model m = new Model("Name");
//m.addOde("A", "k1*A+B");
//m.addOde("B", "B-A+k2-k1");
//m.getParameters();
//
//Map<String, String> statesDict = m.buildStatesDict();
//
//String expected = "{A=S[1], B=S[2]}";
//assertEquals(expected, statesDict.toString());
//}
//
//public void testReconstuctFormulas(){
//Model m = new Model("Name");
//m.addOde("A", "k1*((A+B)+k1)");
//m.addOde("B", "B-A+k2");
//
//String[] actual = m.reconstructFormulas();
//String[] expected = {"P[0]*((S[1]+S[2])+P[0])", "S[2]-S[1]+P[1]"};
//assertTrue(Arrays.equals(expected, actual));
//}

}