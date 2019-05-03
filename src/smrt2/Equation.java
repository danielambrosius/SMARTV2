package smrt2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


// To enable deserialisation using correct subtype when stored polymorphically
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class Equation {

	protected String rightHandSide;
	protected String leftHandSide;
	protected String[] variables;
	protected String[] operators;

	
	public Equation(String leftHandSide, String rightHandSide){
		
		this.rightHandSide = rightHandSide;
		this.leftHandSide = leftHandSide;
		if (this.rightHandSide != null) {
			EquationParser2 parser = new EquationParser2();
			this.variables = parser.getVariables(rightHandSide);
			this.operators = parser.getOperators(rightHandSide);
		}
	}

	/**
	 * Used to get the left hand side of the equation
	 * @return the left hand side of the equation
	 */
	public String getLeftHandSide() {
		return this.leftHandSide;
	}

	/**
	 * Used to set the left hand side of the equation
	 * @param leftHandSide a string of the left hand side of the equation
	 */
	public void setLeftHandSide(String leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	/**
	 * Used to get the right hand side of the equation
	 * @return the right hand side of the equation
	 */
	public String getRightHandSide() {
		return this.rightHandSide;
	}

	/**
	 * Used to set the right hand side of the equation
	 * @param rightHandSide the right hand side of the equation
	 */
	public void setRightHandSide(String rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	/**
	 * Used to get the variables from the equation
	 * @return a string array with the variables
	 */
	@JsonIgnore
	public String[] getVariables() {
		return this.variables;
	}
	
	/**
	 * Used to get the operators from the equation
	 * @return a string array with the operators
	 */
	@JsonIgnore
	public String[] getOperators() {
		return this.operators;
	}

	/**
	 * Used to test the right hand side of the equation
	 * @return a boolean if the right hand side of the equation is readable for the solver
	 */
	public boolean testRightHandSide() {
		RightHandSideChecker tester = new RightHandSideChecker();
		return tester.test(this.getVariables(), this.getOperators());
	}

}