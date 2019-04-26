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
	public boolean testFormula() {
		String reconstructedFormula ="";
		String[] localOperatorlist = this.operators;
		// Substitute standard functions to JS readable code:
		for (int i = 0; i < localOperatorlist.length; i++) {
			if (!localOperatorlist[i].contains("Math")) {
				localOperatorlist[i] = StdFSubber.substitute(localOperatorlist[i]);
			}
		}
		
		if (variables.length > 0 && variables[0].isEmpty()){
			// the list that contains the empty string could be larger so index on the length of the other list 
			for (int j = 0; j < localOperatorlist.length; j++) {
				if(localOperatorlist[j].startsWith("Math")) {
					
					reconstructedFormula += localOperatorlist[j];
				}else {
					reconstructedFormula += "1" + localOperatorlist[j];
				}
			}
		}
		
		//check if ode equation has an operator
		else if (localOperatorlist.length > 0) {
			for (int j = 0; j < variables.length; j++) {
				reconstructedFormula += localOperatorlist[j] + "1";
			}
		}
		
		// if the variable list was larger that the operator list the last variable needs to be added and vice versa
		if (variables.length > localOperatorlist.length){
			reconstructedFormula += "1";
		}
		
		if (variables.length < localOperatorlist.length){
			reconstructedFormula += localOperatorlist[localOperatorlist.length-1];
		}
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		reconstructedFormula = StdFSubber.powerSubstitute(reconstructedFormula);

		try {
			engine.eval(reconstructedFormula);
		} catch (ScriptException e) {
			return false;
		}
		return true;
	}

}
