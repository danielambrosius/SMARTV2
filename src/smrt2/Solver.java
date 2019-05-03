package smrt2;

public interface Solver {

	/** 
	 * Appends the tableModel with the solutions of the provided equations in the specified time frame.
	 * @params tableModel	Reference to the tableModel that stores all of the data
	 * @params myModel		Model of the system that the solver is going to solve
	 * @params S0			array of initial conditions for the dependent variables, in the same order as the model.equationList.
	 * @params P			array of parameter values.
	 * @params tStart		begin time point.
	 * @params tEnd			end time point.
	 * @params tStep		size of the time step.
	 */
	void solve(SmartTableModel S, Model myModel, Double[] S0, Double[] P, double tStart, double tEnd,
			double tStep);

}