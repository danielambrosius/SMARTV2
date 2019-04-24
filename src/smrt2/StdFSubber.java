package smrt2;

public class StdFSubber {

	private static String[] standardFunctions = {"sin","cos","tan","log","log10","log2","sqrt","abs"};
	private static String[] subOperators = {"ln","^"};
	private static String[] targetOperators = {"log","**"}; 
	
	public static String substitute(String odeFormula) {
		for (int i = 0; i < subOperators.length; i++) {
			odeFormula = odeFormula.replace(subOperators[i],targetOperators[i]);
		}
		for (String function : standardFunctions) {
			odeFormula = odeFormula.replace(function, "Math."+function);
		}
		return odeFormula;
	}
}
