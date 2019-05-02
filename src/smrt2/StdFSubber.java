package smrt2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StdFSubber {

	private static String[] standardFunctions = {"sin","cos","tan","log","log10","log2","sqrt","abs"};
	private static String[] subOperators = {"ln","^"};
	private static String[] targetOperators = {"log","**"}; 
	private static Map<String,String> groupingDict = new HashMap<String,String>();
	
	/**
	 * Method for substituting a standard function for a version that java script can read
	 * @param odeOperator; the operator to be substituted
	 * @return: the substituted operator
	 */
	public static String substitute(String odeOperator) {
		for (int i = 0; i < subOperators.length; i++) {
			odeOperator = odeOperator.replace(subOperators[i],targetOperators[i]);
		}
		for (String function : standardFunctions) {
			odeOperator = odeOperator.replace(function, "Math."+function);
		}
		return odeOperator;
	}
	
	/**
	 * Method for substituting a power sign (** or ^) for a version that java script
	 * can read
	 * @param odeFormula; formula containing a power operator.
	 * @return: the substituted formula.
	 */
	public static String powerSubstitute(String odeFormula) {
		Pattern MY_PATTERN = Pattern.compile("[^A-z]?(\\(.+?\\))");
		Matcher m = MY_PATTERN.matcher(odeFormula);
		int i = 0;
		while(m.find()) {
			odeFormula = odeFormula.replace(m.group(1), "#"+i);
			groupingDict.put("#"+i, m.group(1));
			i++;
		}
		Pattern MY_PATTERN1 = Pattern.compile("([A-z0-9.,#]+)(\\*\\*)([A-z0-9.,#]+)");
		Matcher m1 = MY_PATTERN1.matcher(odeFormula);
		while(m1.find()) {
			odeFormula = odeFormula.replace(m1.group(0), String.format("Math.pow(%s,%s)",m1.group(1),m1.group(3)));
		}
		Pattern MY_PATTERN2 = Pattern.compile("(Math.+\\))(\\*\\*)([A-z0-9.,#]+)");
		Matcher m2 = MY_PATTERN2.matcher(odeFormula);
		while(m2.find()) {
			odeFormula = odeFormula.replace(m2.group(0), String.format("Math.pow(%s,%s)",m2.group(1),m2.group(3)));
		}
		
		for (String key : groupingDict.keySet()) {
			odeFormula = odeFormula.replace(key, groupingDict.get(key));
		}
		return odeFormula;
	}
}
