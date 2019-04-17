package smrt2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experiment implements Serializable{
	private String name;
	private Model model;
	private Double[] parameterValues;
	private Double[] stateInitialValues;
	private double tStart;
	private double tEnd;
	private double tStep;
	
	public Experiment(Model m){
		//TODO never call experiment without a name
		this(m, "Default experiment name");
	}
	
	public Experiment(Model m, String name) {
		this.model = m; //store the passed on model inside the class
		setName(name);
		setTimeFrame(0, 10, 1); //default timeframe values
		
		//Create an array for initial conditions of states and fill it with default values
		stateInitialValues = new Double[m.getStates().size()];
		Arrays.fill(stateInitialValues, 1.0);
		
		//Create an array to store the parameter values, with default values
		parameterValues = new Double[m.getParameters().size()];
		Arrays.fill(parameterValues, 1.0);
	}
	
	/**
	 * Returns the solution of the experiment, with time in the first column.
	 * Uses the solver class which employs the Euler foreward method.
	 */
	public Double[][] run() {
		Solver s = new Solver();
		return s.solveEulerForward(reconstructFormulas(), this.stateInitialValues, this.parameterValues, this.tEnd, this.tStep);
	}

	/**
	 * Used to set the time frame used by the solver. 
	 * The solver always starts at t=0, but tStart is used by the gui to display a potential subset.
	 * @param tStart used by gui to shows the desired time frame
	 * @param tEND determines at what time point the solver should stop
	 * @param tStep determines the step size the solver uses
	 */
	public void setTimeFrame(double tStart, double tEnd, double tStep) {
		this.tStart = tStart;
		this.tEnd = tEnd;
		this.tStep = tStep;		
	}

	public double[] getTimeValues() {
		double [] timeValues = {this.tStart, this.tEnd, this.tStep};
		return timeValues;
	}

	public void setParameterValue(int i, double value) {
		parameterValues[i] = value;		
	}

	public int getParameterPosition(String name) throws Exception {
		List<String> parameters= model.getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).equals(name)) {
				return i;
			}
		}
		throw new Exception("Parameter Name not found");		
	}

	public double getParameterValue(int pos) {
		double value = parameterValues[pos];
		return value;
	}
	
	public void setName(String name) {
		//TODO check if name is valid
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setStateValue(int i, double value) {
		stateInitialValues[i] = value;		
	}
	
	public int getStatePosition(String name) throws Exception {
		List<String> states= model.getStates();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).equals(name)) {
				return i;
			}
		}
		throw new Exception("State Name not found");
	}

	public double getStateValue(int pos) {
		double value = stateInitialValues[pos];
		return value;
	}

	public String[] getStateNames() {
		return (String[]) model.getStates().toArray(new String[0]);
	}
	
	/**
	 * Returns a String[] containing user specified formulas in solver compatible syntax.
	 * Parameters and states are replaced by references to arrays that will store the data.
	 */
	public String[] reconstructFormulas() {
		int nStates = model.getStates().size(); //number of states
		//create a list to store the results
		//each state has a corresponding formula so they have the same length
		String[] reconstuctedFormulaList = new String[nStates];
		//create a HashMap that translates variable names to array references that will hold the data.
		Map<String,String> varDict = buildParamDict();
		varDict.putAll(buildStatesDict()); //combine both HashMaps for convenience.
		
		for (int i = 0; i < nStates; i++) {
			String reconstructedFormula = "";
			String[] currentVariables = model.getVariablesOfOde(i);
			String[] currentOperators = model.getOperatorsOfOde(i);
			
			// the parser adds a empty string to either the list of variables or list of operators.
			// the empty string ends up in the list of whatever the formula starts with.
			// this information is used to reconstruct the formula in the correct order.
			if (currentVariables[0].isEmpty()){
				// the list that contains the empty string could be larger so index on the length of the other list 
				for (int j = 0; j < currentOperators.length; j++) {
					reconstructedFormula += varDict.get(currentVariables[j]) + currentOperators[j];
				}
			}
			
			//check if ode equation has an operator
			else if (currentOperators.length > 0 && currentOperators[0].isEmpty()) {
				for (int j = 0; j < currentVariables.length; j++) {
					reconstructedFormula += currentOperators[j] + varDict.get(currentVariables[j]);
				}
			}
			
			// if the variable list was larger that the operator list the last variable needs to be added and vice versa
			if (currentVariables.length > currentOperators.length){
				reconstructedFormula += varDict.get(currentVariables[currentVariables.length-1]);
			}
			
			if (currentVariables.length < currentOperators.length){
				reconstructedFormula += currentOperators[currentOperators.length-1];
			}

		reconstuctedFormulaList[i] = reconstructedFormula;
		}
		
	return reconstuctedFormulaList;	
	}

	/**
	 * Returns a HashMap that links a parameter name to a reference of an index in array P.
	 * Array P will hold user specified parameter values.
	 * This method is used by the reconstructFormula method.
	 */
	private Map<String, String> buildParamDict() {
		Map<String,String> paramDict = new HashMap<String,String>();
		List<String> params = model.getParameters();
		for (int i = 0; i < params.size();i++) {
			String value = "P" + "[" + i + "]";
			paramDict.put(params.get(i), value);
		}
		return paramDict;
	}

	/**
	 * Returns a HashMap that links a state name to a reference of an index in array S.
	 * Array S contains the solutions of the model at the previous step in time.
	 * This method is used by the reconstructFormula method.
	 */
	private Map<String, String> buildStatesDict(){
		Map<String, String> statesDict = new HashMap<String, String>();
		List<String> states = model.getStates();
		for (int i = 0; i < states.size();i++) {
			String value = "S" + "[" + (i+1) + "]";
			statesDict.put(states.get(i), value);
		}
	return statesDict;
	}
}