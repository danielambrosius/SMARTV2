package smrt2;

public class Ode {
	private String formula;
	private String state;
	
	public Ode(String state, String formula){
		this.formula = formula;
		this.state = state;
	}

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
}
