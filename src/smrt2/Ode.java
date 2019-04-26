package smrt2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import smrt2.EquationParser2;


public class Ode implements Equation{
	// initialize variables
	// State and formula should not contain trailing and leading white spaces.
	private String rightHandSide;
	private String leftHandSide;
	private String[] variables;
	private String[] operators;

	
	// create constructor
	@JsonCreator
	public Ode(@JsonProperty("leftHandSide") String leftHandSide,
			   @JsonProperty("rightHandSide") String rightHandSide){
		this.rightHandSide = rightHandSide;
		this.leftHandSide = leftHandSide;
		
		if (this.rightHandSide != null) {
			EquationParser2 parser = new EquationParser2();
			this.variables = parser.getVariables(rightHandSide);
			this.operators = parser.getOperators(rightHandSide);
		}
		
	}

	// Getters and setters for variables
	@Override
	public String getLeftHandSide() {
		return leftHandSide;
	}

	@Override
	public void setLeftHandSide(String leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	@Override
	public String getRightHandSide() {
		return this.rightHandSide;
	}

	@Override
	public void setRightHandSide(String rightHandSide) {
		this.rightHandSide = rightHandSide;
		
	}
	
	@Override
	@JsonIgnore
	public String[] getVariables() {
		return this.variables;
	}
	
	@Override
	@JsonIgnore
	public String[] getOperators() {
		return this.operators;
	}
	
	@Override
	public String[] toArray() {
		String[] odeArray = {this.getLeftHandSide(),this.getRightHandSide()};
		return odeArray;
	}
	
	
	@Override
	public boolean testRightHandSide() {
		RightHandSideChecker tester = new RightHandSideChecker();
		return tester.test(this.getVariables(), this.getOperators());
	}
}
