package tests;

import junit.framework.TestCase;
import smrt2.Ode;

public class testODE extends TestCase {
	private String state = "E";
	private String formula = "2mc";
	private String stringOde = "dE/dt = 2mc";
	
	public void testOdeExists(){
		Ode myOde = new Ode(null, null);
		assertNotNull(myOde);
	}
	
	public void testGetFormula(){
		Ode myOde = new Ode(null, formula);
		assertEquals(formula, myOde.getFormula());
	}
	
	public void testSetFormula(){
		Ode myOde = new Ode(null, null);
		myOde.setFormula(formula);
		assertEquals(formula, myOde.getFormula());
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
		Ode myOde = new Ode(state, formula);
		assertEquals(stringOde, myOde.toString());
	}
}