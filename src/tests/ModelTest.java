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
	
	public void testAddEquation() {
		/* Model should be able to create instances of ODEs from string input,
		 * Store them in an array.
		 */
		Ode testODE = new Ode("A", "2k + 3A^2");
		
		m.addODE(testODE);
		
		List<Ode> odesInModel = m.getODEs();
		
		assertTrue(odesInModel.contains(testODE));	
	}
	
	public void testReadAndCreateOde() {
		
		Ode testODE = new Ode("A", "2k + 3A^2");
		String expected = testODE.toString();
		
		m.readODE("A", "2k + 3A^2");
		
		String actual = m.getODEs().get(0).toString();
		
		assertEquals(expected, actual);
		
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
