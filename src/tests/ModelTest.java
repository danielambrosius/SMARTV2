package tests;

import java.util.List;

import junit.framework.TestCase;
import smrt2.Model;
import smrt2.Ode;

public class ModelTest extends TestCase {
	
	private Model m;
	
	public void setUp() {
		m = new Model();
	}
	
	public void testModelCreation() {
		Model m = new Model();
		assertNotNull(m);
	}
	
	public void testModelHasName() {
		Model m = new Model("Name");
		assertEquals("Name", m.getName());
	}
	
	public void testModelSetName() {
		m.setName("Tim");
		assertEquals("Tim", m.getName());
	}
	
	public void testAddParameter() throws Exception {
		m.addParameter("a");
		assertTrue(m.getParameters().contains("a"));
	}
	
	public void testAddParameters() throws Exception {
		m.addParameter("a");
		m.addParameter("b");
		assertTrue(m.getParameters().contains("a"));
		assertTrue(m.getParameters().contains("b"));
	}	
	
	public void testDuplicateParameters() {
		try {
			m.addParameter("a");
			m.addParameter("a");
			fail("Exception not raised");
		} catch (Exception e) {
			String m = e.getMessage();
			assertEquals("Duplicate parameters not allowed", m);
		}
		
	}
	
	public void testAddState() throws Exception {
		m.addState("a");
		assertTrue(m.getStates().contains("a"));
	}
	
	public void testAddStates() throws Exception {
		m.addState("a");
		m.addState("b");
		assertTrue(m.getStates().contains("a"));
		assertTrue(m.getStates().contains("b"));
	}	
	
	public void testDuplicateStates() {
		try {
			m.addState("a");
			m.addState("a");
			fail("Exception not raised");
		} catch (Exception e) {
			String m = e.getMessage();
			assertEquals("Duplicate states not allowed", m);
		}
		
	}
	
	public void testAddOde() {
		
		Ode testODE = new Ode("A", "k");
		String expected = testODE.toString();
		
		m.addOde("A", "k");
		
		String actual = m.getOdeList().get(0).toString();
		
		assertEquals(expected, actual);	
	}
	
	public void testDisplayOdes(){
		m.addOde("A", "k1");
		m.addOde("B", "k2");
		String[][] actual = m.displayOdeList();
		String[][] expected = {{"dA/dt", "k1"}, {"dB/dt", "k2"}}; 
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
			
		}
	}
	
	public void testCompileModel() {
		// Will probably need subtests.
		// Model should analyse all the contained equations and parse them for errors
		
		fail("Not implemented");
	}
	
	
	public void testSaveModel() {
		fail("Note implemented");
	}
	
	
	
	
	

}
