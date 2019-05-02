package tests;

import junit.framework.TestCase;
import smrt2.Ode;
import smrt2.RightHandSideChecker;

public class RightHandSideCheckerTest extends TestCase {

	public void testCheckRightHandSide() {
		Ode myOde = new Ode("A", "(a-b)/c");
		
		String[] variables = myOde.getVariables();
		String[] operators = myOde.getOperators();
		RightHandSideChecker checker = new RightHandSideChecker();
		assertTrue(checker.test(variables, operators));
	}
	
	public void testCheckRightHandSide2() {
		Ode myOde = new Ode("A", "2*(a-b)/c");
		
		String[] variables = myOde.getVariables();
		String[] operators = myOde.getOperators();
		RightHandSideChecker checker = new RightHandSideChecker();
		assertTrue(checker.test(variables, operators));
	}
	
	public void testCheckRightHandSideGivesError() {
		Ode myOde = new Ode("A", "(b%%c)");
		
		String[] variables = myOde.getVariables();
		String[] operators = myOde.getOperators();
		RightHandSideChecker checker = new RightHandSideChecker();
		assertFalse(checker.test(variables, operators));
	}
}
