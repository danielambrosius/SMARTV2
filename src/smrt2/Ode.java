package smrt2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Ode extends Equation{

	@JsonCreator
	public Ode(@JsonProperty("leftHandSide") String leftHandSide,
       	 	   @JsonProperty("rightHandSide") String rightHandSide) {
		super(leftHandSide, rightHandSide);
	}
	
}
