package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.AlgEq;
import smrt2.Equation;

public class AlgebraicEquationTest extends TestCase {
	private String leftHandSide = "a";
	private String rightHandSide = "2+b";
	
	public void testAlgEqExists(){
		Equation myAlgEq = new AlgEq(null, null);
		assertNotNull(myAlgEq);
	}
	
	public void testGetRightHandSide(){
		Equation myAlgEq = new AlgEq(null, rightHandSide);
		assertEquals(rightHandSide, myAlgEq.getRightHandSide());
	}
	
	public void testSetRightHandSide(){
		Equation myAlgEq = new AlgEq(null, null);
		myAlgEq.setRightHandSide(rightHandSide);
		assertEquals(rightHandSide, myAlgEq.getRightHandSide());
	}
	
	public void testGetLeftHandSide(){
		Equation myAlgEq = new AlgEq(leftHandSide, null);
		assertEquals(leftHandSide, myAlgEq.getLeftHandSide());
	}
	
	public void testSetLeftHandSide(){
		Equation myAlgEq = new AlgEq(null, null);
		myAlgEq.setLeftHandSide(leftHandSide);
		assertEquals(leftHandSide, myAlgEq.getLeftHandSide());
	}
	
	public void testEquationParser2Variables() {
		rightHandSide = "-A*B-(C*D)";
		Equation myAlgEq = new AlgEq(leftHandSide, rightHandSide);
		String[] actualVariables = myAlgEq.getVariables();
		String[] expectedListVariables = {"","A","B","C","D"};
		assertEquals(Arrays.toString(expectedListVariables), 
				Arrays.toString(actualVariables));

	}
	public void testEquationParser2Operators() {
		rightHandSide = "A)";
		Equation myAlgEq = new AlgEq(leftHandSide, rightHandSide);
		String[] actualOperators = myAlgEq.getOperators();
		String[] expectedListOperators = {"",")"};
		assertEquals(Arrays.toString(expectedListOperators), 
				Arrays.toString(actualOperators));
	}
	
	public void testTestFormula() {
		rightHandSide = "-A*B-(C*D)";
		Equation myAlgEq = new AlgEq(leftHandSide, rightHandSide);
		boolean isValid = myAlgEq.testRightHandSide();
		assertTrue(isValid);
		String equation2 = "++++"; 
		Equation myAlgEq2 = new AlgEq(leftHandSide, equation2);
		boolean isValid2 = myAlgEq2.testRightHandSide();
		assertFalse(isValid2);
	}
}
