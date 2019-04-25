package smrt2;

public interface Solver {

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
	void solve(SmartTableModel S, String[] odeFormulas, Double[] S0, Double[] P, double tStart, double tEnd,
			double tStep);

}