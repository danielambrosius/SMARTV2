package tests;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.EquationParser2;

public class TestEquationParser2 extends TestCase {
	private String equation = "-A*B-(C*D)";
	
	public void testCreateEquationParser2() {
		EquationParser2 myParser2 = new EquationParser2(equation);
		assertNotNull(myParser2);
	}
	
	public void testParseParameters() {
		equation = "-A*B-(C*D)";
		EquationParser2 myParser2 = new EquationParser2(equation);
		myParser2.parseParameters();
		String[] expectedList = new String[]  {"","A","B","C","D"};
		System.out.println(Arrays.toString(myParser2.getParameters()));
		assertEquals(Arrays.toString(expectedList), Arrays.toString(myParser2.getParameters()));
	}
	
	public void testParseOperators() {
		equation = "-A*B-(C*D)";
		EquationParser2 myParser2 = new EquationParser2(equation);
		myParser2.parseOperators();
		String[] expectedList = new String[] {"-","*","-(","*",")"};
		System.out.println(Arrays.toString(myParser2.getOperators()));
		assertEquals(Arrays.toString(expectedList), Arrays.toString(myParser2.getOperators()));
	}
	
	
//	public void testRemoveUnwantedMatches() {
//		EquationParser2 myParser2 = new EquationParser2(equation);
//		String[] paramArray = new String[] {"A","B","C","D"};;
//		String[] expectedArray = new String[]  {"-","*","-(","*",")"};
//		String[] adjustedArray = myParser2.removeUnwantedMatches(paramArray);
//		System.out.println(Arrays.toString(adjustedArray));
//		assertEquals(Arrays.toString(expectedArray), Arrays.toString(adjustedArray));
//	}


}
