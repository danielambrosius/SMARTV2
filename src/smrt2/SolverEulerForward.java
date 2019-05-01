package smrt2;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class SolverEulerForward implements Solver {

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
	@Override
	public void solve(SmartTableModel tableModel, Model myModel, Double[] S0, Double[] P, double tStart, double tEnd, double tStep){
		int nTimesteps = (int) ((tEnd-tStart)/tStep);
		String[] formulaList = myModel.reconstructFormulas();
		List<Equation> equationList = myModel.getEquationList();
		
		//make first variable of first row 0 because we start at t = 0;
		Double[] firstRow = new Double[formulaList.length+1];
		firstRow[0] = tStart;
	
		//add the initial conditions as the first row;
		for (int i = 0; i < S0.length; i++) {
			firstRow[i+1] = S0[i];	
		}
		
		Double[] AlgResults = eulerForward(equationList, formulaList, firstRow, P, tStep);
		
		for (int i = 1; i < firstRow.length; i++) {
			if (equationList.get(i-1) instanceof AlgEq) {
				firstRow[i] = AlgResults[i];
			}	
		}
		tableModel.AddRow(firstRow);
		
		for (int i = 1; i <= nTimesteps; i++) {
			tableModel.AddRow(eulerForward(equationList, formulaList, tableModel.getRowAt(i-1), P, tStep));
		}
	}
	
	/**
	 * Returns the result of array S in the next point in time with time step dt.
	 * @params equationList	list of equation classes, used to determine the type of an equation.
	 * @params formulaArray	array of reconstructed formula in with parameters and states are replaced with indexes to array S & P
	 * @params S			array of the state of the system at the previous point in time S[0] should reference time
	 * @params P			array of parameter values.
	 * @params dt			size of the time step.
	 */
	private Double[] eulerForward(List<Equation> equationList, String[] formulaArray ,Double[] S, Double[] P, double dt) {
		//start an instance of ScriptEngineManager to create a javascipt engine.
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		//states and parameters are passed to the script engine as a string representation
		String states = arrayDoubleToString(S);
		String parameters = arrayDoubleToString(P);
		//create an array to store the results
		Double[] results = new Double[S.length];
		double result;
		
		results[0] = S[0] + dt; //increment time
		
		for (int i = 1; i < results.length; i++) {
			//first element of results contains time so we skip it
			String formula = formulaArray[i-1];
			formula = StdFSubber.powerSubstitute(formula);
			//use javascript to evaluate the result of the formula
			try {
				result = Double.parseDouble(engine.eval(String.format("var P = %s; var S = %s ;%s", 
						parameters, states, formula)).toString());
				//handle ODEs differently from AlgEq
				if (equationList.get(i-1) instanceof Ode){ 
					results[i] = S[i] + result * dt;
				} else { //if not an Ode it must be an AlgEq
					results[i] = result;
				}
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
