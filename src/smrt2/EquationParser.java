package smrt2;

public class EquationParser {
	
	
	public EquationParser() {
		// TODO Auto-generated constructor stub
		
		
	}

	public String[] parseParameters(String imput) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < imput.length(); i++){
		    char c = imput.charAt(i);
		    
		    
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
