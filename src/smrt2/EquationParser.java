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

	public void parseEquation(String equation) {
		// TODO Auto-generated method stub
		boolean first = true; 
		boolean wasOp = true;	
		for (int i = 0; i < equation.length(); i++){
		    char c = equation.charAt(i);
		    String sc = Character.toString(c);
		    if (isOperator(sc)) {
		    	if (first){
		    		operators.add(sc);
		    	}
		    	else if (wasOp == true){
		    		operators.add(operators.size()-1, operators.get(operators.size()-1) + sc);
		    		operators.remove(operators.size() -1);
		    	}
		    	else {
			    	operators.add(sc);
		    	}
		    	wasOp = true;
		    	
		    }
		    else if (isOperator(sc) == false){
		    	
		    	if (first){
		    		parameters.add(sc);
		    	}
		    	else if (wasOp == false){
		    		parameters.add(parameters.size() -1, parameters.get(parameters.size()-1) + sc);
		    		parameters.remove(parameters.size() -1);

		    	}
		    	else{
			    	parameters.add(sc);
		    	}
		    	wasOp = false;
		    } 
		    first = false;
		}
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
	
	public List getOperators() {
		return operators;
	}

	public List getParameters() {
		return parameters;
	}

	
}
