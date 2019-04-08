package smrt2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EquationParser2 {
	private String[] variables;
	private String[] operators;
	private String equation;
	//regex pattern to split on all operators
	
	private final String consideredOperators = "[\\+|\\*|\\/|\\-|\\(|\\)|\\^"
			+ "|sin|cos|tan|log|ln|sqrt]+";
	//regex pattern to split on all non operators.
	private final String consideredVariables = "[^\\+|\\-|\\*|\\/|\\(|\\)|\\^"
			+ "|sin|cos|tan|log|ln|sqrt]+";

	public EquationParser2(String equation) {
		this.equation = equation;
		this.variables = parseVariables();
		this.operators = parseOperators();
	}

	public String[] parseVariables() {
		variables = equation.split(consideredOperators);
		//variables = removeUnwantedMatches(variables);
		return variables;
	}

	public String[] getVariables() {
		return this.variables;
	}

	public String[] getOperators() {
		return this.operators;
	}

	public String[] parseOperators() {
		operators = equation.split(consideredVariables);
		return operators;
	}

//	public String[] removeUnwantedMatches(String[] variableList) {
//		//stream that will remove empty matches and - sign using filters.
//		List<String> nonEmptyList = Arrays.asList(variableList).
//				stream().filter(i -> !i.equals("") && !i.equals("-")).
//				collect(Collectors.toList()); 
//		return nonEmptyList.toArray(new String[nonEmptyList.size()]);
//	}

}
