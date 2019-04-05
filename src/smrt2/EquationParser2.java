package smrt2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EquationParser2 {
	private String[] parameters;
	private String[] operators;
	private String equation;
	//regex pattern to split on all operators
	
	private final String consideredOperators = "[\\+|\\*|\\/|\\(|\\)|\\^"
			+ "|sin|cos|tan|log|ln|sqrt]+";
	//regex pattern to split on all non operators.
	private final String consideredParameters = "-?[^\\+|\\-|\\*|\\/|\\(|\\)|\\^"
			+ "|sin|cos|tan|log|ln|sqrt]+";

	public EquationParser2(String equation) {
		this.equation = equation;
	}

	public void parseParameters() {
		parameters = equation.split(consideredOperators);
		parameters = removeUnwantedMatches(parameters);
	}

	public String[] getParameters() {
		return parameters;
	}

	public String[] getOperators() {
		return operators;
	}

	public void parseOperators() {
		operators = equation.split(consideredParameters);
	}

	public String[] removeUnwantedMatches(String[] parameterList) {
		//stream that will remove empty matches and - sign using filters.
		List<String> nonEmptyList = Arrays.asList(parameterList).
				stream().filter(i -> !i.equals("") && !i.equals("-")).
				collect(Collectors.toList()); 
		return nonEmptyList.toArray(new String[nonEmptyList.size()]);
	}

}
