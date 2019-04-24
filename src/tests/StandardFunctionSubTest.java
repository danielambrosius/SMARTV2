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
	public void testHandleSpecialFunctions2() {
		String ode = "sqrt(A)";
		String expected = "Math.sqrt(A)";
		String actual = StdFSubber.substitute(ode);
		assertEquals(expected,actual);
	}
	public void testTranslateOperators() {
		String ode = "5^2+ln(A)";
		String expected = "5**2+Math.log(A)";
		String actual = StdFSubber.substitute(ode);
		assertEquals(expected,actual);
	}
	
	
}
