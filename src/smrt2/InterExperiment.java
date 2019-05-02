package smrt2;

import gui.SolverThread;

public interface InterExperiment {

	/**
	 * Returns the solution of the experiment, with time in the first column.
	 * Uses the solver class which employs the Euler foreward method.
	 */

	SolverThread run();

	/**
	 * used to get the instance of a model that is bound to the experiment.
	 * @return: the instance of the model associated whit the experiment.
	 */
	Model getModel();

	/**
	 * Used to get the name of the experiment for saving purposes.
	 * @return: String containing the name.
	 */
	String getName();

}