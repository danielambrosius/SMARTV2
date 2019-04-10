package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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
	
	public void testDisplayOdes()  {
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
	public void testAddingDuplicateODE() {
		m.addOde("A", "k1");
		m.addOde("A", "k2");		
		String actual = m.getOdeList().toString();		
		assertEquals("[dA/dt = k1]", actual);
		m.addOde("B",  "k1");
		actual = m.getOdeList().toString();
		assertEquals("[dA/dt = k1, dB/dt = k1]", actual);
		// model should ignore duplicate states.
	}
	public void testStartNewModel() throws Exception {
		m.addOde("A", "k1");
		m.addParameter("para");
		m.setName("Kees");
		m.startNewModel();
		
		assertEquals(null, m.getName());
		assertEquals("[]" , m.getOdeList().toString());
		assertEquals("[]" , m.getStates().toString());
		assertEquals("[]" , m.getParameters().toString());
	}
	public void testFindParameters() {
		m.addOde("A", "k1*A+B");
		m.addOde("B", "B-A+k2-k1");
		m.findParameters();
		
		ArrayList<String> expected = new ArrayList<String>(
			    Arrays.asList("k1","k2"));
		
		assertEquals(expected, m.getParameters());
		}
	public void testBuildParamDict() {
		m.addOde("A", "k1*A+B");
		m.addOde("B", "B-A+k2");
		m.findParameters();
		
		Map<String, String> paramDict = m.buildParamDict();
		
		String expected = "{k1=P[0], k2=P[1]}";
		assertEquals(expected, paramDict.toString());
	}
	
	public void testBuildStatesDict() {
		m.addOde("A", "k1*A+B");
		m.addOde("B", "B-A+k2-k1");
		m.findParameters();

		Map<String, String> statesDict = m.buildStatesDict();
		
		String expected = "{A=S[0], B=S[1]}";
		assertEquals(expected, statesDict.toString());
	}
	
	public void testReconstuctFormulas(){
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		m.findParameters();
		
		String[] actual = m.reconstructFormulas();
		String[] expected = {"P[0]*((S[0]+S[1])+P[0])", "S[1]-S[0]+P[1]"};
		assertTrue(Arrays.equals(expected, actual));
	}
	
}
		

