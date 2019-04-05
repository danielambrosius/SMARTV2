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
		boolean first = true; 
		boolean wasOp = true;	
		for (int i = 0; i < equation.length(); i++){
		    char c = equation.charAt(i);
		    String po = Character.toString(c);
		    if (isOperator(po)) {
		    	addOperator(po, wasOp, first);
		    	wasOp = true;
		    }
		    else if (isOperator(po) == false){
		    	addParameter(po, wasOp, first);
		    	wasOp = false;
		    } 
		    first = false;
		}
	}

	public boolean isOperator(String c) {
		//operators as sin cos and tan log ln, sqrt are not acounted for yet
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

	public void addParameter(String param, boolean wasOp, boolean first) {
		if (first){
			parameters.add(param);
    	}
    	else if (wasOp == false){
    		parameters.add(parameters.size() -1, parameters.get(parameters.size()-1) + param);
    		parameters.remove(parameters.size() -1);
    	}
    	else{
    		parameters.add(param);
    	}
	}

	public void addOperator(String oper, boolean wasOp, boolean first) {
		// TODO Auto-generated method stub
		if (first){
    		operators.add(oper);
    	}
    	else if (wasOp == true){
    		operators.add(operators.size()-1, operators.get(operators.size()-1) + oper);
    		operators.remove(operators.size() -1);
    	}
    	else {
	    	operators.add(oper);
    	}
	}

	
}
