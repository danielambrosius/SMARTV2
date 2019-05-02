package smrt2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AlgEq extends Equation {
	
	@JsonCreator
	public AlgEq(@JsonProperty("leftHandSide") String leftHandSide,
	        	 @JsonProperty("rightHandSide") String rightHandSide) {
		super(leftHandSide, rightHandSide);
	}
	
}
