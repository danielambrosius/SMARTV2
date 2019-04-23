package smrt2;

public class StdFSubber {

	private static String[] standardFunctions = {"sin","cos","tan","log"};
	
	public static String substitute(String odeFormula) {
		for (String function : standardFunctions) {
			odeFormula = odeFormula.replace(function, "Math."+function);
		}
		return odeFormula;
	}
}
