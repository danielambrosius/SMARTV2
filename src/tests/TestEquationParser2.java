package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.EquationParser2;

public class TestEquationParser2 extends TestCase {
	private String equation;
	
	public void testCreateEquationParser2() {
		equation = "-k1+k2";
		EquationParser2 myParser2 = new EquationParser2(equation);
		assertNotNull(myParser2);
	}
	
	public void testParseVariables() {
		equation = "-k1+k2";
		EquationParser2 myParser2 = new EquationParser2(equation);
		myParser2.parseVariables();
		String[] expectedList = new String[]  {"","k1","k2"};
		System.out.println(Arrays.toString(myParser2.getVariables()));
		assertEquals(Arrays.toString(expectedList), Arrays.toString(myParser2.getVariables()));
	}
	
	public void testParseOperators() {
		equation = "-k1+k2";
		EquationParser2 myParser2 = new EquationParser2(equation);
		myParser2.parseOperators();
		String[] expectedList = new String[] {"-","+"};
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
