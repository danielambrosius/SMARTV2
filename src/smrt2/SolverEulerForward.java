package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class SolverEulerForward implements Solver {

	/* (non-Javadoc)
	 * @see smrt2.Solver#solve(smrt2.SmartTableModel, java.lang.String[], java.lang.Double[], java.lang.Double[], double, double, double)
	 */
	@Override
	public void solve(SmartTableModel S, String[] odeFormulas, Double[] S0, Double[] P, double tStart, double tEnd, double tStep){
		int nTimesteps = (int) ((tEnd-tStart)/tStep);
//		Double[][] S = new Double[nTimesteps+1][odeFormulas.length+1];
		
		//make first variable of first row 0 because we start at t = 0;
		Double[] firstRow = new Double[odeFormulas.length+1];
		firstRow[0] = tStart;
//		S[0][0] = tStart;
	
		//add the initial conditions as the first row;
		for (int i = 0; i < S0.length; i++) {
			firstRow[i+1] = S0[i];	
		}
		S.AddRow(firstRow);
		
		for (int i = 1; i <= nTimesteps; i++) {
			S.AddRow(eulerForward(odeFormulas, S.getRowAt(i-1), P, tStep));
		}
	}
	
	/**
	 * Returns the result of array S in the next point in time with time step dt.
	 * @params odeFormulas	array of strings that describe the ode formulas.
	 * @params S			array of the state of the system at the previous point in time S[0] should reference time
	 * @params P			array of parameter values.
	 * @params dt			size of the time step.
	 */
	private Double[] eulerForward(String[] odeFormulas ,Double[] S, Double[] P, double dt) {
		//start an instance of ScriptEngineManager to create a javascipt engine.
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		//states and parameters are passed to the script engine as a string representation
		String states = arrayDoubleToString(S);
		String parameters = arrayDoubleToString(P);
		//create an array to store the results
		Double[] results = new Double[S.length];
		double dxdt;
		
		results[0] = S[0] + dt; //increment time
		
		for (int i = 1; i < results.length; i++) {
			//first element of results contains time so we skip it
			String odeFormula = odeFormulas[i-1];
			odeFormula = StdFSubber.powerSubstitute(odeFormula);
			//use javascript to evaluate the result of the formula
			try {
				dxdt = Double.parseDouble(engine.eval(String.format("var P = %s; var S = %s ;%s", parameters, states, odeFormula )).toString());
				results[i] = S[i] + dxdt*dt;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			}	
		}
	return results;
	}
	
	/**
	 * Returns a string representation of a Double[]
	 */
	private String arrayDoubleToString(Double[] listDouble){
		
		String resultingString = "[";
		
		for (int i = 0; i < listDouble.length; i++) {
			if (i ==0) {
				resultingString += listDouble[i];
			}
			else {
				resultingString += "," + listDouble[i];
			}
		}
	return resultingString + "]";
	}
	
		
}
