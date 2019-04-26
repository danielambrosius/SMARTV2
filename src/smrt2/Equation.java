package smrt2;

public interface Equation {

	String getLeftHandSide();

	void setLeftHandSide(String leftHandSide);

	String getRightHandSide();

	void setRightHandSide(String rightHandSide);

	String[] getVariables();

	String[] getOperators();

	String[] toArray();

	boolean testFormula();

}