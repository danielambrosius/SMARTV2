package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RightHandSideChecker {
	public boolean test(String[] variables, String[] operators) {
		String reconstructedFormula = reconstructEquationWithDummy(variables, operators);
		return checkRunsInJS(reconstructedFormula);
	}

	private String reconstructEquationWithDummy(String[] variables, String[] operators) {
		//use this number to replace every state as dummy data (do not use 0 as that gives devision errors)
		double dummyNumber = 1.0;
		String reconstructedFormula ="";

		// Substitute standard functions to JS readable code:
		for (int i = 0; i < operators.length; i++) {
			if (!operators[i].contains("Math")) {
				operators[i] = StdFSubber.substitute(operators[i]);
			}
		}
		
		if (variables.length > 0 && variables[0].isEmpty()){
			// the list that contains the empty string could be larger so index on the length of the other list 
			for (int j = 0; j < operators.length; j++) {
				if(operators[j].startsWith("Math")) {
					
					reconstructedFormula += operators[j];
				}else {
					reconstructedFormula += dummyNumber + operators[j];
				}
			}
		}
		
		//check if ode equation has an operator
		else if (operators.length > 0) {
			for (int j = 0; j < variables.length; j++) {
				reconstructedFormula += operators[j] + dummyNumber;
			}
		}
		
		// if the variable list was larger that the operator list the last variable needs to be added and vice versa
		if (variables.length > operators.length){
			reconstructedFormula += dummyNumber;
		}
		
		if (variables.length < operators.length){
			reconstructedFormula += operators[operators.length-1];
		}
		reconstructedFormula = StdFSubber.powerSubstitute(reconstructedFormula);
		return reconstructedFormula;
	}

	private boolean checkRunsInJS(String reconstructedFormula) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			engine.eval(reconstructedFormula);
		} catch (ScriptException e) {
			return false;
		}
		return true;
	}
}
