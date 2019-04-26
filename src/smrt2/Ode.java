package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import smrt2.EquationParser2;


public class Ode{
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
	public String getLeftHandSide() {
		return leftHandSide;
	}

	public void setLeftHandSide(String leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public String getRightHandSide() {
		return this.rightHandSide;
	}

	public void setRightHandSide(String rightHandSide) {
		this.rightHandSide = rightHandSide;
		
	}
	
	@Override
	public String toString(){
		String stringOde = "";
		stringOde = "d" + this.leftHandSide + "/dt = " + this.rightHandSide;
		return stringOde;
	}
	@JsonIgnore
	public String[] getVariables() {
		return this.variables;
	}
	@JsonIgnore
	public String[] getOperators() {
		return this.operators;
	}
	public String[] toArray() {
		String[] odeArray = {this.getLeftHandSide(),this.getRightHandSide()};
		return odeArray;
	}
	
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
		else if (localOperatorlist.length > 0 && localOperatorlist[0].isEmpty()) {
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
		System.out.println(reconstructedFormula);

		try {
			engine.eval(reconstructedFormula);
		} catch (ScriptException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
