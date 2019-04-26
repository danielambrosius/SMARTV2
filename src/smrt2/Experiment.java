package smrt2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Experiment{
	private String name;
	private Model model;
	private Double[] parameterValues;
	private Double[] stateInitialValues;
	private double tStart;
	private double tEnd;
	private double tStep;
	private SmartTableModel tableModel;
	
	public Experiment(Model m){
		this(m, "Default experiment name");
	}
	
	@JsonCreator
	public Experiment(@JsonProperty("name") String name,
					  @JsonProperty("model") Model model,
					  @JsonProperty("parameterValues") Double[] parameterValues,
					  @JsonProperty("stateValues") Double[] stateValues,
					  @JsonProperty("timeValues") Double[] timeValues) {
		this(model, name);
		this.parameterValues = parameterValues;
		this.stateInitialValues = stateValues;
		this.tStart = timeValues[0];
		this.tEnd = timeValues[1];
		this.tStep = timeValues[2];
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
		
		//Instantiate the table model
		tableModel = new SmartTableModel(m.getStates());
	}
	
	/**
	 * Returns the solution of the experiment, with time in the first column.
	 * Uses the solver class which employs the Euler foreward method.
	 */

	public SolverThread run() {
		SolverThread t = new SolverThread(tableModel, reconstructFormulas(), this.stateInitialValues, this.parameterValues, 
					this.tStart, this.tEnd, this.tStep);
		t.start();
		return t;
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
	
	public Double[] getParameterValues() {
		return this.parameterValues;
	}
	
	public Model getModel() {
		return this.model;
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
	
	@JsonIgnore
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
	public Double[] getStateValues() {
		return this.stateInitialValues;
	}

	@JsonIgnore
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
					if(varDict.get(currentVariables[j]) != null) {
						reconstructedFormula += varDict.get(currentVariables[j]) + currentOperators[j];
					} else {
						reconstructedFormula += currentOperators[j];
					}
				}
			}
			
			//check if ode equation has an operator
			else if (currentOperators.length > 0) {
				for (int j = 0; j < currentVariables.length; j++) {
					reconstructedFormula += currentOperators[j] + varDict.get(currentVariables[j]);
				}
			}
			
			// if the variable list was larger that the operator list the last variable needs to be added and vice versa 
			if (currentVariables.length > currentOperators.length){
				if (varDict.get(currentVariables[currentVariables.length-1]) != null) {
					reconstructedFormula += varDict.get(currentVariables[currentVariables.length-1]);
				}
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
		params.removeAll(Arrays.asList("", null));
		if(params.size() > 0) {
			int isTAdded = 0;
			for (int i = 0; i < params.size();i++) {
				String value;
				
				if(params.get(i).equals("t")) {
					value = "S" + "[0]";
					isTAdded++;
				}else {
					value = "P" + "[" + (i-isTAdded) + "]";
				}
				paramDict.put(params.get(i), value);
			}
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
		
		
		for (int i = 0; i < states.size(); i++) {
			String value = "S" + "[" + (i+1) + "]";
			
			statesDict.put(states.get(i), value);
		}
	return statesDict;
	}
	@JsonIgnore
	public SmartTableModel getTableModel() {
		return tableModel;
	}

	public void resetTable() {
		this.tableModel = new SmartTableModel(this.model.getStates());
		
	}
}