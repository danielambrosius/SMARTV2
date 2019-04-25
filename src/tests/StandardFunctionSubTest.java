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
		String expected = "Math.pow(5,2)+Math.log(A)";
		String actual = StdFSubber.powerSubstitute(StdFSubber.substitute(ode));
		assertEquals(expected,actual);
	}
	
	public void testHandleExponentials() {
		String ode = "2+K2**2B**5";
		String expected = "2+Math.pow(Math.pow(K2,2B),5)";
		String actual = StdFSubber.powerSubstitute(ode);
		assertEquals(expected,actual);
	}
	public void testHandleExponentials2() {
		String ode = "(K+5)^(2*K)";
		String expected = "Math.pow((K+5),(2*K))";
		String actual = StdFSubber.powerSubstitute(StdFSubber.substitute(ode));
		assertEquals(expected,actual);
	}
	public void testHandleExponentials3() {
		String ode = "sin(5+A)^B";
		String expected = "Math.pow(Math.sin(5+A),B)";
		String actual = StdFSubber.powerSubstitute(StdFSubber.substitute(ode));
		assertEquals(expected,actual);
	}
	//@TODO handle this situation
//	public void testHandleExponentials4() {
//		String ode = "2^5^2^7";
//		String expected = "Math.pow(Math.sin(5+A),B)";
//		String actual = StdFSubber.substitute(ode);
//		assertEquals(expected,actual);
//	}

}
