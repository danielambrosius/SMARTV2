package smrt2;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonCreator
	public Ode(@JsonProperty("state") String state,
			   @JsonProperty("formula") String formula){
		this.formula = formula;
		this.state = state;
		
		if (this.formula != null) {
			EquationParser2 parser = new EquationParser2(this.formula);
			this.variables = parser.getVariables();
			this.operators = parser.getOperators();
		}
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
	@JsonIgnore
	public String[] getVariables() {
		return this.variables;
	}
	@JsonIgnore
	public String[] getOperators() {
		return this.operators;
	}
	public String[] toArray() {
		String[] odeArray = {this.getState(),this.getFormula()};
		return odeArray;
	}
}
