package smrt2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser2 {
	private String[] variables;
	private String[] operators;
	//regex pattern to split on all operators
	
	private final String consideredOperators = "(\\+|\\-|\\*|\\/|=|>|<|>=|<=|&|\\||%|!|\\^|\\(|\\)|sin\\(|cos\\(|tan\\(|log\\(|ln\\()+";
	//regex pattern to split on all non operators.
	//private final String consideredVariables = "";

//	public EquationParser2(String equation) {
//		this.equation = equation;
//		this.variables = parseVariables();
//		this.operators = parseOperators();
//	}

	public String[] parseVariables(String equation) {
		variables = equation.split(consideredOperators);
		//variables = removeUnwantedMatches(variables);
		return variables;
	}

	public String[] getVariables(String equation) {
		return objectToStringArray(parse(equation).get(0).toArray());
	}

	public String[] getOperators(String equation) {
		return objectToStringArray(parse(equation).get(1).toArray());
	}

	public String[] parseOperators(String equation) {
		Pattern MY_PATTERN = Pattern.compile(consideredOperators);
		Matcher m = MY_PATTERN.matcher(equation);
		List<String> operatorList = new ArrayList<String>();
		while(m.find())
        {
			operatorList.add(m.group(0));
        }
		if (operatorList.size() > 0 && !operatorList.get(0).substring(0,1).equals(equation.substring(0,1))) {
			operatorList.add(0, "");
		}
		operators = operatorList.toArray(new String[operatorList.size()]);
		return operators;
	}
	
	public List<List<String>> parse(String equation){
		equation = equation.replace(" ", "");
		List<String> parsedVariables = new ArrayList<String>(Arrays.asList(parseVariables(equation)));
		List<String> parsedOperators = new ArrayList<String>(Arrays.asList(parseOperators(equation)));
		
		if (parsedVariables.size() != 0 && parsedVariables.get(0).isEmpty()) {
			int j = parsedVariables.size();
			for (int i = 1; i < j; i++) {
				if (parsedVariables.get(i).matches("[0-9.,]+")){
					parsedOperators.set(i-1, parsedOperators.get(i-1) + parsedVariables.get(i) + parsedOperators.get(i));
					parsedVariables.remove(i);
					parsedOperators.remove(i);
					j = parsedVariables.size();
					i--;
				}
			}
		}
		
		if (parsedOperators.size() != 0 && parsedOperators.get(0).isEmpty()) {
			int j = parsedVariables.size();
			for (int i = 0; i < j; i++) {
				if (parsedVariables.get(i).matches("[0-9.,]+")){
					if (parsedOperators.size() > i+1 ) {
						parsedOperators.set(i,
								parsedOperators.get(i) + parsedVariables.get(i) + parsedOperators.get(i + 1));
						parsedOperators.remove(i+1);
					} else {
						parsedOperators.set(i, parsedOperators.get(i) + parsedVariables.get(i));
					}
					
					parsedVariables.remove(i);
					j = parsedVariables.size();
					i--;
				}
			}
		}
		List<List<String>> results = new ArrayList<List<String>>();
		results.add(parsedVariables);
		results.add(parsedOperators);
		return results;
	}

	public String[] objectToStringArray(Object[] objectArray) {
		String stringArray[] = Arrays.stream(objectArray)
				.map(Object::toString)
				.toArray(String[]::new);
		return stringArray;
	}

//	public String[] removeUnwantedMatches(String[] variableList) {
//		//stream that will remove empty matches and - sign using filters.
//		List<String> nonEmptyList = Arrays.asList(variableList).
//				stream().filter(i -> !i.equals("") && !i.equals("-")).
//				collect(Collectors.toList()); 
//		return nonEmptyList.toArray(new String[nonEmptyList.size()]);
//	}

}
