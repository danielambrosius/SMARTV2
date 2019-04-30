package smrt2;

public class Equation {

	protected String rightHandSide;
	protected String leftHandSide;
	protected String[] variables;
	protected String[] operators;

	public Equation() {
		super();
	}

	public String getLeftHandSide() {
		return this.leftHandSide;
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

	public String[] getVariables() {
		return this.variables;
	}

	public String[] getOperators() {
		return this.operators;
	}

	public String[] toArray() {
		String[] AlgebraicEquationArray = {this.getLeftHandSide(),this.getRightHandSide()};
		return AlgebraicEquationArray;
	}

	public boolean testRightHandSide() {
		RightHandSideChecker tester = new RightHandSideChecker();
		return tester.test(this.getVariables(), this.getOperators());
	}

}