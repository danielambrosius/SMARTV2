package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Solver {
	
	public static Double[][] solveEulerForeward(String[] odeFormulas, Double[] S0, Double[] P, double tEnd, double tStep){
		/**
		 * Returns the results of the provided odeFormulas simulated over the specified time frame.
		 * odeFormulas should contain references to array P for parameters and references to S for states.
		 * odeFormulas should be in the same order as S0, so odeFormula[i] describes S0[i].
		 * @params odeFormulas 	array of strings that describe the ode formulas.
		 * @params S0 			array of initial conditions, in same order as odeFormulas.
		 * @params P 			array of parameter values.
		 * @params tEnd 		end time of the simulation
		 * @params tStep 		size of the time step in the simulation
		 */
		int nTimesteps = (int) (tEnd/tStep);
		Double[][] S = new Double[nTimesteps+1][odeFormulas.length+1];

		
		//make first variable of first row 0 because we start at t = 0;
		S[0][0] = 0.0;
		//add the initial conditions as the first row;
		for (int i = 0; i < S0.length; i++) {
			S[0][i+1] = S0[i];	
		}
		
		for (int i = 1; i <= nTimesteps; i++) {
			S[i] = eulerForeward(odeFormulas, S[i-1], P, tStep);
		}
	return S;
	}
	
	private static Double[] eulerForeward(String[] odeFormulas ,Double[] S, Double[] P, double dt) {
		/**
		 * Returns the result of array S in the next point in time with time step dt.
		 * @params odeFormulas	array of strings that describe the ode formulas.
		 * @params S			array of the state of the system at the previous point in time S[0] should reference time
		 * @params P			array of parameter values.
		 * @params dt			size of the time step.
		 */
		//start an instance of ScriptEngineManager to create a javascipt engine.
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		//states and parameters are passed to the script engine as a string representation
		String states = listDoubleToString(S);
		String parameters = listDoubleToString(P);
		//create an array to store the results
		Double[] results = new Double[S.length];
		double dxdt;
		
		results[0] = S[0] + dt; //increment time
		
		for (int i = 1; i < results.length; i++) {
			//first element of results contains time so we skip it
			String odeFormula = odeFormulas[i-1];
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
	
	private static String listDoubleToString(Double[] listDouble){
		/**
		 * Returns a sting representation of a Double[]
		 */
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
