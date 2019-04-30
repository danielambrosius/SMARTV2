package smrt2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Ode extends Equation{
	
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
}
