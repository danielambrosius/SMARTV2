package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.Ode;
import smrt2.RightHandSideChecker;

public class RightHandSideCheckerTest extends TestCase {
	public void testCheckRightHandSide() {
		Ode myOde = new Ode("A", "(a-b)/c");
		
		String[] variables = myOde.getVariables();
		System.out.println(Arrays.asList(variables));
		String[] operators = myOde.getOperators();
		System.out.println(Arrays.asList(operators));
		RightHandSideChecker checker = new RightHandSideChecker();
		assertTrue(checker.test(variables, operators));
	}
	
	public void testCheckRightHandSide2() {
		Ode myOde = new Ode("A", "2*(a-b)/c");
		
		String[] variables = myOde.getVariables();
		System.out.println(Arrays.asList(variables));
		String[] operators = myOde.getOperators();
		System.out.println(Arrays.asList(operators));
		RightHandSideChecker checker = new RightHandSideChecker();
		assertTrue(checker.test(variables, operators));
	}
	
	public void testCheckRightHandSideGivesError() {
		Ode myOde = new Ode("A", "(b%%c)");
		
		String[] variables = myOde.getVariables();
		System.out.println(Arrays.asList(variables));
		String[] operators = myOde.getOperators();
		System.out.println(Arrays.asList(operators));
		RightHandSideChecker checker = new RightHandSideChecker();
		assertFalse(checker.test(variables, operators));
	}
}
