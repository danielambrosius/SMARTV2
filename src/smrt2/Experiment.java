package smrt2;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gui.SolverThread;

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
					  @JsonProperty("stateIntialValues") Double[] stateIntialValues,
					  @JsonProperty("timeValues") Double[] timeValues) {
		this(model, name);
		this.parameterValues = parameterValues;
		this.stateInitialValues = stateIntialValues;
		this.tStart = timeValues[0];
		this.tEnd = timeValues[1];
		this.tStep = timeValues[2];
	}
	
	public Experiment(Model m, String name) {
		this.model = m; //store the passed on model inside the class
		setName(name);
		setTimeFrame(0, 10, 1); //default timeframe values
		
		//Create an array for initial conditions of states and fill it with default values
		stateInitialValues = new Double[m.getDependentVariables().size()];
		Arrays.fill(stateInitialValues, 1.0);
		
		//Create an array to store the parameter values, with default values
		parameterValues = new Double[m.getParameters().size()];
		Arrays.fill(parameterValues, 1.0);
		
		//Instantiate the table model
		this.tableModel = new SmartTableModel(generateColnames(), this.name);
	}
	
	/**
	 * Returns the solution of the experiment, with time in the first column.
	 * Uses the solver class which employs the Euler foreward method.
	 */

	public SolverThread run() {
		SolverThread t = new SolverThread(this.tableModel, this.model, this.stateInitialValues, this.parameterValues, 
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
	
	/**
	 * Used to get the three time values in a list of doubles.
	 * @return: array of doubles containing: tStart, tEnd, tStep.
	 */
	public double[] getTimeValues() {
		double [] timeValues = {this.tStart, this.tEnd, this.tStep};
		return timeValues;
	}

	/**
	 * Used to set a parameter value at a index that corresponds to the index of the name in
	 * the models getParameters() function.
	 * @param i: index of the value.
	 * @param value: the value to change to.
	 */
	public void setParameterValue(int i, double value) {
		parameterValues[i] = value;		
	}

	/**
	 * Uses an index to get a value from the parameter array.
	 * @param i: index to get value at
	 * @return: double of the value requested.
	 */
	public double getParameterValue(int i) {
		double value = parameterValues[i];
		return value;
	}
	
	/**
	 * used to get the instance of a model that is bound to the experiment.
	 * @return: the instance of the model associated whit the experiment.
	 */
	public Model getModel() {
		return this.model;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Used to get the name of the experiment for saving purposes.
	 * @return: String containing the name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Used to set a state value at a index that corresponds to the index of the name in
	 * the models getStates() function.
	 * @param i: index of the value.
	 * @param value: the value to change to.
	 */
	public void setStateValue(int i, double value) {
		stateInitialValues[i] = value;		
	}

	/**
	 * Uses an index to get a value from the state array.
	 * @param i: index to get value at
	 * @return: double of the value requested.
	 */
	public double getStateValue(int pos) {
		double value = stateInitialValues[pos];
		return value;
	}
	/**
	 * Used to get the table model associated whit the experiment.
	 * @return: a SmartTableModel instance of the used table model.
	 */
	@JsonIgnore
	public SmartTableModel getTableModel() {
		return tableModel;
	}
	
	/**
	 * Used to reset the associated table model to when reruning an experiment whit the same
	 * experiment class.
	 */
	public void resetTable() {
		this.tableModel = new SmartTableModel(generateColnames(), this.name);
		
	}
	
	private List<String> generateColnames() {
		// Add units to the colNames
		List<String> colNames = this.model.getStates();
		String colName;
		String unit;
		
		for (int i = 0; i < colNames.size(); i++) {
			colName = colNames.get(i);
			unit = this.model.getDescriptionFromKey(colName)[0];
			if (!unit.isEmpty()) {
				colName += " (" + unit + ")";
			}
			
			colNames.set(i, colName);
		}
		return colNames;
	}
	
	// Used to serialize
	public Double[] getParameterValues() {
		return this.parameterValues;
	}
	
	// Used to serialize
	public Double[] getStateIntialValues() {
		return this.stateInitialValues;
	}
}