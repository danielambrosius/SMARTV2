package smrt2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Solver {
	private String[] odeFormulas;
	private double[] S0;
	private String P;
	private double tStep;
	private double nTimesteps; 
	private double[][] S;

	public Solver(String[] odeFormulas, double[] S0, double[] P, double tEnd, double tStep) {
		this.odeFormulas = odeFormulas;
		this.S0 = S0;
		this.tStep = tStep;
		this.nTimesteps = tEnd/tStep;
		if(odeFormulas.length != S0.length ) {
			throw new IllegalArgumentException("Number of odeFormulas and State variables do not match");			
		}
		this.S = new double[(int) nTimesteps+1][odeFormulas.length+1];
		this.P = "";
		
		for (int i = 0; i < P.length; i++) {
			if (i ==0) {
				this.P += P[i];
			}
			else {
				this.P += "," + P[i];
			}
		}
		
		
	}
	
	private double[] eulerForeward(String[] odeFormulas ,double[] S, String P, double dt) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		double[] results = new double[S.length];
		double dxdt;
		String states = "";
		
		for (int i = 0; i < S.length; i++) {
			if (i == 0) {
				states += S[i];
			}
			else {
				states += "," + S[i];
			}
		}
		
		results[0] = S[0] + dt;
		
		for (int i = 1; i <= odeFormulas.length; i++) {
			String odeFormula = odeFormulas[i-1];
			try {
				dxdt = Double.parseDouble(engine.eval(String.format("var P = [%s]; var S = [%s] ;%s", P, states, odeFormula )).toString());
				results[i] = S[i] + dxdt*dt;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			}	
		}
	return results;
	}
	
	public double[][] solve(){
		//make first variable of first row 0 because we start at t = 0;
		S[0][0] = 0;
		//add the initial conditions as the first row;
		for (int i = 0; i < S0.length; i++) {
			S[0][i+1] = S0[i];	
		}
		
		for (int i = 1; i <= nTimesteps; i++) {
			S[i] = eulerForeward(this.odeFormulas, S[i-1], P, tStep);
		}
	return S;
	}

	
	
}
