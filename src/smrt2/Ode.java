package smrt2;
import java.io.Serializable;

import smrt2.EquationParser2;


public class Ode implements Serializable{
	// initialize variables
	// State and formula should not contain trailing and leading white spaces.
	private String equation;
	private String state;
	private String[] parameters;
	private String[] operators;
	
	// create constructor
	public Ode(String state, String equation){
		this.equation = equation;
		this.state = state;
		
		if (this.equation != null) {
			EquationParser2 parser = new EquationParser2(equation);
			this.parameters = parser.getParameters();
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
		return this.equation;
	}

	public void setFormula(String equation) {
		this.equation = equation;
		
	}
	
	@Override
	public String toString(){
		String stringOde = "";
		stringOde = "d" + this.state + "/dt = " + this.equation;
		return stringOde;
	}
	public String[] getParameters() {
		return this.parameters;
	}
	public String[] getOperators() {
		return this.operators;
	}
}
