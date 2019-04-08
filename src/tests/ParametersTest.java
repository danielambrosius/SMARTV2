package tests;

import junit.framework.TestCase;
import smrt2.Parameter;


public class ParametersTest extends TestCase {
	private Parameter p;
	
	public void setUp() {
		p = new Parameter("p", 0.92);
		assertNotNull(p);
	}
	
	public void testGetValue() {
		p = new Parameter("p", 0.92);
		double got = p.getValue();
		assertEquals(0.92, got);	
	}
	
	public void testSetValue(){
		p = new Parameter("p", 0.92);
		p.setValue(0.93);
		double got = p.getValue();
		assertEquals(0.93, got);	
	}
	
	public void testGetName() {
		p = new Parameter("p", 0.92);
		String got = p.getName();
		assertEquals("p", got);	
	}
	
}
