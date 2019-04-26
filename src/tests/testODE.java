package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.Equation;
import smrt2.Ode;

public class testODE extends TestCase {
	private String state = "E";
	private String equation = "2mc";
	
	public void testOdeExists(){
		Equation myOde = new Ode(null, null);
		assertNotNull(myOde);
	}
	
	public void testGetFormula(){
		Equation myOde = new Ode(null, equation);
		assertEquals(equation, myOde.getRightHandSide());
	}
	
	public void testSetFormula(){
		Equation myOde = new Ode(null, null);
		myOde.setRightHandSide(equation);
		assertEquals(equation, myOde.getRightHandSide());
	}
	
	public void testGetState(){
		Equation myOde = new Ode(state, null);
		assertEquals(state, myOde.getLeftHandSide());
	}
	
	public void testSetState(){
		Equation myOde = new Ode(null, null);
		myOde.setLeftHandSide(state);
		assertEquals(state, myOde.getLeftHandSide());
	}
	
	public void testEquationParser2Variables() {
		equation = "-A*B-(C*D)";
		Equation myOde = new Ode(state, equation);
		String[] actualVariables = myOde.getVariables();
		String[] expectedListVariables = {"","A","B","C","D"};
		assertEquals(Arrays.toString(expectedListVariables), 
				Arrays.toString(actualVariables));

	}
	public void testEquationParser2Operators() {
		equation = "A)";
		Equation myOde = new Ode(state, equation);
		String[] actualOperators = myOde.getOperators();
		String[] expectedListOperators = {"",")"};
		assertEquals(Arrays.toString(expectedListOperators), 
				Arrays.toString(actualOperators));
	}
	
	public void testTestFormula() {
		equation = "-A*B-(C*D)";
		Equation myOde = new Ode(state, equation);
		boolean isValid = myOde.testRightHandSide();
		assertTrue(isValid);
		String equation2 = "++++"; 
		Equation myOde2 = new Ode(state, equation2);
		boolean isValid2 = myOde2.testRightHandSide();
		assertFalse(isValid2);
	}

}
