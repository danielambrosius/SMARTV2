package smrt2;

public class Ode {
	// initialize variables
	// State and formula should not contain trailing and leading white spaces.
	private String formula;
	private String state;
	
	// create constructor
	public Ode(String state, String formula){
		this.formula = formula;
		this.state = state;
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

	public void setFormula(String formula) {
		this.formula = formula;
		
	}
	
	@Override
	public String toString(){
		String stringOde = "";
		stringOde = "d" + this.state + "/dt = " + this.formula;
		return stringOde;
	}
}
