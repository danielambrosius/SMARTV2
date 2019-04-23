package tests;

import junit.framework.TestCase;
import smrt2.StdFSubber;

public class StandardFunctionSubTest extends TestCase {
	public void testHandleSpecialFunctions() {
		String ode = "sin(5)";
		String expected = "Math.sin(5)";
		String actual = StdFSubber.substitute(ode);
		assertEquals(expected,actual);
	}
	
}
