package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.Ode;

public class testODE extends TestCase {
	private String state = "E";
	private String equation = "2mc";
	private String stringOde = "dE/dt = 2mc";
	
	public void testOdeExists(){
		Ode myOde = new Ode(null, null);
		assertNotNull(myOde);
	}
	
	public void testGetFormula(){
		Ode myOde = new Ode(null, equation);
		assertEquals(equation, myOde.getFormula());
	}
	
	public void testSetFormula(){
		Ode myOde = new Ode(null, null);
		myOde.setFormula(equation);
		assertEquals(equation, myOde.getFormula());
	}
	
	public void testGetState(){
		Ode myOde = new Ode(state, null);
		assertEquals(state, myOde.getState());
	}
	
	public void testSetState(){
		Ode myOde = new Ode(null, null);
		myOde.setState(state);
		assertEquals(state, myOde.getState());
	}
	
	public void testToString(){
		Ode myOde = new Ode(state, equation);
		assertEquals(stringOde, myOde.toString());
	}
	public void testEquationParser2Parameters() {
		equation = "-A*B-(C*D)";
		Ode myOde = new Ode(state, equation);
		String[] actualParameters = myOde.getParameters();
		String[] expectedListParameters = {"","A","B","C","D"};
		assertEquals(Arrays.toString(expectedListParameters), 
				Arrays.toString(actualParameters));

	}
	public void testEquationParser2Operators() {
		equation = "A)";
		Ode myOde = new Ode(state, equation);
		String[] actualOperators = myOde.getOperators();
		String[] expectedListOperators = {"",")"};
		assertEquals(Arrays.toString(expectedListOperators), 
				Arrays.toString(actualOperators));
	}
}
