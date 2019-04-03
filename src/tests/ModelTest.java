package tests;

import junit.framework.TestCase;
import smrt2.Model;

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
	
	
	
}
