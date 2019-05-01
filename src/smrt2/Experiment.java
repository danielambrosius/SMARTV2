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
		tableModel = new SmartTableModel(m.getStates(), this.name);
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

	public double[] getTimeValues() {
		double [] timeValues = {this.tStart, this.tEnd, this.tStep};
		return timeValues;
	}

	public void setParameterValue(int i, double value) {
		parameterValues[i] = value;		
	}

	public double getParameterValue(int pos) {
		double value = parameterValues[pos];
		return value;
	}
	
	public Model getModel() {
		return this.model;
	}
	
	private void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setStateValue(int i, double value) {
		stateInitialValues[i] = value;		
	}

	public double getStateValue(int pos) {
		double value = stateInitialValues[pos];
		return value;
	}

	@JsonIgnore
	public SmartTableModel getTableModel() {
		return tableModel;
	}

	public void resetTable() {
		this.tableModel = new SmartTableModel(this.model.getStates(), this.name);
		
	}
}