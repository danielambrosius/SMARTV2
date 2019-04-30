package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import smrt2.EquationParser2;

public class EquationParser2Test extends TestCase {
	private String equation;
	
	public void testCreateEquationParser2() {
		equation = "-k1+ k2";
		EquationParser2 myParser2 = new EquationParser2();
		assertNotNull(myParser2);
	}
	
	public void testParseVariables() {
		equation = "-k1+k2";
		EquationParser2 myParser2 = new EquationParser2();
		myParser2.parseVariables(equation);
		String[] expectedList = new String[]  {"","k1","k2"};
		assertEquals(Arrays.toString(expectedList), Arrays.toString(myParser2.getVariables(equation)));
	}
	
	public void testParseOperators() {
		equation = "-k1+k2";
		EquationParser2 myParser2 = new EquationParser2();
		String[] expectedList = new String[] {"-","+"};
		assertEquals(Arrays.toString(expectedList), Arrays.toString(myParser2.getOperators(equation)));
	}
	public void testobjectToStringArray() {
		EquationParser2 myParser2 = new EquationParser2();
		Object[] objectArray = {"k1","k2"};
		String[] stringArray = myParser2.objectToStringArray(objectArray);
		String[] expected = {"k1","k2"};
		assertEquals(Arrays.toString(expected), Arrays.toString(stringArray));
	}
	public void testParse1() {
		equation = "(A + 1)*k1";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList("","A","k1"));
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("(","+1)*"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	
	public void testParse2() {
		equation = "A*2 + k1";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList("A","k1"));
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("","*2+"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	
	public void testParse3() {
		equation = "-sin(2 + k1)";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList("","k1"));
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("-sin(2+",")"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	public void testParse4() {
		equation = "++++";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList());
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("++++"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	
	public void testParse5() {
		equation = "sin(2 + k1)";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList("","k1"));
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("sin(2+",")"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	public void testParse6() {
		equation = "log(A+B)+5";
		EquationParser2 myParser2 = new EquationParser2();
		List<List<String>> results = myParser2.parse(equation);
		List<String> expectedVariables = new ArrayList<String>(Arrays.asList("","A","B"));
		List<String> expectedOperators = new ArrayList<String>(Arrays.asList("log(","+",")+5"));
		assertEquals(expectedVariables, results.get(0));
		assertEquals(expectedOperators, results.get(1));
	}
	
	
public void testNumbersInOdesAndRandomSpaces() {
	equation = "Y^ 2 *    5";
	EquationParser2 myParser2 = new EquationParser2();
	List<List<String>> results = myParser2.parse(equation);
	List<String> expectedVariables = new ArrayList<String>(Arrays.asList("Y"));
	List<String> expectedOperators = new ArrayList<String>(Arrays.asList("","^2*5"));
	assertEquals(expectedVariables, results.get(0));
	assertEquals(expectedOperators, results.get(1));
	}


}
