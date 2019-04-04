package smrt2;

import java.util.ArrayList;
import java.util.List;

public class EquationParser {
	private List operators;
	private List parameters;
	
	
	public EquationParser() {
		this.operators = new ArrayList();
		this.parameters = new ArrayList();
		// TODO Auto-generated constructor stub
		
		
	}

	public String[] parseParameters(String equation) {
		// TODO Auto-generated method stub
		boolean wasOp = true;	
		for (int i = 0; i < equation.length(); i++){
		    char c = equation.charAt(i);
		    String sc = Character.toString(c);
		    if (isOperator(sc)) {
		    	if (wasOp == true){
		    		operators.add(-1, operators.get(-1) + sc);
		    	}
		    	else {
			    	operators.add(sc);
		    	}
		    	wasOp = true;
		    	
		    }
		    else if (isOperator(sc) == false){
		    	if (wasOp == false){
		    		parameters.add(-1, parameters.get(-1) + sc);
		    	}
		    	else {
			    	parameters.add(sc);
		    	}
		    	wasOp = false;
		    }  
		}
		return null;
	}

	public boolean isOperator(String c ) {
		// TODO Auto-generated method stub
		String[] operators = {"+", "-", "*", "/", "^", "!", "%", "(", ")"};
		for (String op : operators){
			if(op.equals(c)) {
				return true;
			}
			
		}
		return false;
	}
	
}
