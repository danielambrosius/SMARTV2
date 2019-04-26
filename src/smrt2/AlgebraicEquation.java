package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AlgebraicEquation implements Equation {

	private String rightHandSide;
	private String leftHandSide;
	private String[] variables;
	private String[] operators;

	
	// create constructor
	@JsonCreator
	public AlgebraicEquation(@JsonProperty("leftHandSide") String leftHandSide,
			   @JsonProperty("rightHandSide") String rightHandSide){
		this.rightHandSide = rightHandSide;
		this.leftHandSide = leftHandSide;
		
		if (this.rightHandSide != null) {
			EquationParser2 parser = new EquationParser2();
			this.variables = parser.getVariables(rightHandSide);
			this.operators = parser.getOperators(rightHandSide);
		}
		
	}
	
	@Override
	public String getLeftHandSide() {
		return this.leftHandSide;
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
	public String[] getVariables() {
		return this.variables;
	}

	@Override
	public String[] getOperators() {
		return this.operators;
	}

	@Override
	public String[] toArray() {
		String[] AlgebraicEquationArray = {this.getLeftHandSide(),this.getRightHandSide()};
		return AlgebraicEquationArray;
	}

	@Override
	public boolean testRightHandSide() {
		RightHandSideChecker tester = new RightHandSideChecker();
		return tester.test(this.getVariables(), this.getOperators());
	}

}
