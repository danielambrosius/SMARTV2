package smrt2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EquationParser2 {
	private String[] variables;
	private String[] operators;
	private String equation;
	//regex pattern to split on all operators
	
	private final String consideredOperators = "(\\+|\\-|\\*|\\/|=|>|<|>=|<=|&|\\||%|!|\\^|\\(|\\))+|(sin\\(|cos\\(|tan\\(|log\\(|ln\\()";
	//regex pattern to split on all non operators.
	//private final String consideredVariables = "";

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
		Pattern MY_PATTERN = Pattern.compile(consideredOperators);
		Matcher m = MY_PATTERN.matcher(equation);
		List<String> operatorList = new ArrayList<String>();
		while(m.find())
        {
			operatorList.add(m.group(0));
        }
		operators = operatorList.toArray(new String[operatorList.size()]);
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
