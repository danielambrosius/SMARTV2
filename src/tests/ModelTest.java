package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import smrt2.Model;

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
		m.addOde("A", "k");
		
		String actual = m.getEquationAtIndex(0).getLeftHandSide();
		assertEquals("A", actual);
		
		actual = m.getEquationAtIndex(0).getRightHandSide();
		assertEquals("k", actual);
	}
	
	public void testDisplayOdes()  {

		Model m = new Model("Name");
		m.addOde("A", "k1");
		m.addOde("B", "k2");
		m.addOde("C", "**++++///BC");
		String[][] actual = m.displayEquationList();
		String[][] expected = {{"dA/dt", "k1"}, {"dB/dt", "k2"}, {"dC/dt", "**++++///BC (Incorrect syntax)"}}; 
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}	
		}
	}
	
	public void testStartNewModel() throws Exception {
		Model m = new Model("Name");
		m.addOde("A", "k1");
		m.setName("Kees");
		m.startNewModel();
		
		assertEquals(null, m.getName());
		assertEquals("[]" , m.getEquationList().toString());
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
		m.addAlgEq("B", "B-A+k2");
		
		List<String> actual = m.getStates();
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("A","B"));
		assertEquals(expected, actual);
	}
	
	public void testGetParameters(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addAlgEq("B", "B-A+k2");
		
		List<String> actual = m.getParameters();
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("k1","k2"));
		assertEquals(expected, actual);
	}
	
	public void testDuplicateStates() {
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("A", "e");
		List<String> actual = m.getStates();
		assertEquals(actual.toString(), "[A]");
	}
	
	public void testAddAlgEq() {
		Model m = new Model("Name");
		m.addAlgEq("A", "k");
		
		String actual = m.getEquationAtIndex(0).getLeftHandSide();
		assertEquals("A", actual);
		
		actual = m.getEquationAtIndex(0).getRightHandSide();
		assertEquals("k", actual);
	}
	
	public void testReconstuctFormulas(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		
		String[] actual = m.reconstructFormulas();
		String[] expected = {"P[0]*((S[1]+S[2])+P[0])", "S[2]-S[1]+P[1]"};
		assertEquals(Arrays.asList(expected), Arrays.asList(actual));
	}
	
	public void testReconstuctFormulasTime(){
		Model m = new Model("Name");
		m.addOde("C", "x+b");
		m.addOde("A", "k1*((A+t+t+t+B)+k1)");
		m.addOde("B", "B-A+k2");

		
		String[] actual = m.reconstructFormulas();
		String[] expected = {"P[0]+P[1]", "P[2]*((S[2]+S[0]+S[0]+S[0]+S[3])+P[2])", "S[3]-S[2]+P[3]"};
		assertTrue(Arrays.equals(expected, actual));
	}
	
	public void testReconstuctFormulas2(){
		// testing for a known bug
		Model m = new Model("Name");
		m.addOde("A", "2+k1");
		m.addOde("B", "k1+2");
		m.addOde("c", "(2+k1)*2");
		
		String[] actual = m.reconstructFormulas();
		String[] expected = {"2+P[0]", "P[0]+2", "(2+P[0])*2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(actual));
	}

}