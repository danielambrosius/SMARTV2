package smrt2;

import java.lang.reflect.Array;
import java.util.Arrays;

public class EquationParser2 {
	
	private String equation;
	private final String consideredOperators = "[\\+|\\-|\\*|\\/|\\(|\\)|\\^"
			+ "|sin|cos|tan|log|ln|sqrt]+";
	private final String consideredParameters = "[A-z]+";

	public EquationParser2(String equation) {
		this.equation = equation;
	}

	public String[] parseParameters() {
		String[] parameterList = equation.split(consideredOperators);
//		String eq = equation;
//		String operator = "";
//		for(String param: parameterList) {
//			eq = eq.replace(param, "#");
//		}
//		System.out.println(eq);
//		String[] operatorList = eq.split("#");
//		System.out.println(Arrays.toString(operatorList));
//		System.out.println(Arrays.toString(parameterList));
		return parameterList;
	}

	public String[] parseOperators() {
		String[] operatorList = equation.split(consideredParameters);
		return operatorList;
	}

}
