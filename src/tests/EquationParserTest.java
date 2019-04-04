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
		String imput = "A*B";
		EquationParser myEP = new EquationParser();
		String[] parameters = myEP.parseParameters(imput);
		//assertEquals(parameters[0], "A");
		//assertEquals(parameters[1], "B");
	}
	
	public void testIsOperator() {
		EquationParser myEP = new EquationParser();
		boolean minus = myEP.isOperator("-");
		boolean somea = myEP.isOperator("A");
		assertEquals(minus, true);
		assertEquals(somea, false);
		
	}
	
}
