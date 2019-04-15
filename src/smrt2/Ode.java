package smrt2;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import smrt2.EquationParser2;


public class Ode implements Serializable{
	// initialize variables
	// State and formula should not contain trailing and leading white spaces.
	private String formula;
	private String state;
	private String[] variables;
	private String[] operators;
	
	// create constructor
	public Ode(String state, String equation){
		this.formula = equation;
		this.state = state;
		
		if (this.formula != null) {
			EquationParser2 parser = new EquationParser2(equation);
			this.variables = parser.getVariables();
			this.operators = parser.getOperators();
		}
	}
	
	// Constructor for JSONifier, this makes Jackson able to reconstruct the class.
	@JsonCreator
	public Ode(@JsonProperty("formula") String formula, 
			   @JsonProperty("state") String state, 
			   @JsonProperty("variables") String[] variables,
			   @JsonProperty("operators") String[] operators) {
		this.formula = formula;
		this.state = state;
		this.variables = variables;
		this.operators = operators;
	}
	
	// Getters and setters for variables
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String equation) {
		this.formula = equation;
		
	}
	
	@Override
	public String toString(){
		String stringOde = "";
		stringOde = "d" + this.state + "/dt = " + this.formula;
		return stringOde;
	}
	public String[] getVariables() {
		return this.variables;
	}
	public String[] getOperators() {
		return this.operators;
	}
	public String[] toArray() {
		String[] odeArray = {this.getState(),this.getFormula()};
		return odeArray;
	}
}
