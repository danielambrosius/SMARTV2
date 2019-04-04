package tests;

import junit.framework.TestCase;
import smrt2.EquationParser;
import smrt2.Model;

public class EquationParserTest extends TestCase {
	
	public void testEquationParser() {
		EquationParser myEP = new EquationParser();
		assertNotNull(myEP);
	}
	
	public void testParseEquation() {
		//if operater and parameter list are same lenght the equation starts whit a operator
		//otherwise whit a parameter
		String imput = "--A*-BC";
		EquationParser myEP = new EquationParser();
		myEP.parseEquation(imput);
		System.out.print(myEP.getParameters());
		System.out.print(myEP.getOperators());
		assertEquals("A",myEP.getParameters().get(0));
		assertEquals("BC", myEP.getParameters().get(1));
		assertEquals("--",myEP.getOperators().get(0));
		assertEquals("*-",myEP.getOperators().get(1));


	}
	
	public void testIsOperator() {
		EquationParser myEP = new EquationParser();
		boolean minus = myEP.isOperator("-");
		boolean somea = myEP.isOperator("A");
		assertEquals(minus, true);
		assertEquals(somea, false);
		
	}
	
	public void testGetOperators() {
		EquationParser myEP = new EquationParser();
		assertNotNull(myEP.getOperators());
	}
	
	public void testGetParameters() {
		EquationParser myEP = new EquationParser();
		assertNotNull(myEP.getParameters());
	}
	
	public void testAddParameter() {
		EquationParser myEP = new EquationParser();
		myEP.addParameter("A", true, true);
		assertEquals("A",myEP.getParameters().get(0));

	}
	
	public void testAddOperator() {
		EquationParser myEP = new EquationParser();
		myEP.addOperator("-", true, true);
		assertEquals("-",myEP.getOperators().get(0));

	}

	
}
