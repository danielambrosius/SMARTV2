package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.EquationParser2;

public class TestEquationParser2 extends TestCase {
	private final String equation = "--sin(A)*-BCd";
	
	public void testCreateEquationParser2() {
		EquationParser2 myParser2 = new EquationParser2(equation);
		assertNotNull(myParser2);
	}
	
	public void testParseParameters() {
		EquationParser2 myParser2 = new EquationParser2(equation);
		String[] paramList = myParser2.parseParameters();
		String[] expectedList = new String[] {"","A","BCd"};
		System.out.println(Arrays.toString(paramList));
		assertEquals(Arrays.toString(expectedList), Arrays.toString(paramList));
	}
	
	public void testParseOperators() {
		EquationParser2 myParser2 = new EquationParser2(equation);
		String[] operList = myParser2.parseOperators();
		String[] expectedList = new String[] {"--","(",")*-"};
		System.out.println(Arrays.toString(operList));
		assertEquals(Arrays.toString(expectedList), Arrays.toString(operList));
	}


}
