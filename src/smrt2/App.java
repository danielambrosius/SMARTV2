package smrt2;

import smrt2.AlgEq;
import smrt2.Ode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


import gui.SolverThread;

public class App {
	private Model myModel;
	private Experiment myExperiment;
	private boolean modelSaved;
	private boolean experimentSaved;
	private SaverLoader saverLoader;
	
	
	public App() {
		modelSaved = false;
		experimentSaved = false;
		saverLoader = SaverLoader.getInstance();
	}

	/**
	 * Used to handle adding of a formula. Ode or algebraic equation. Returns 
	 * errors for several cases that are then handled by the ModelVieuw.
	 * @param state: the left hand side of the formula
	 * @param equation: the right hand side of the formula
	 * @param isOde: true when the function is a ode
	 * @return 0,1,2 or 3. 0 if the function was added. 1 if a duplicate state 
	 * was added. 2 if the state or equation was empty. 3 because the state is
	 * already defined as a unbound parameter.
	 */
	public int handleButtonAddFormula(String state, String equation, Boolean isOde) {
		state = state.replace(" ", "");
		equation = equation.replace(" ", "");
		if (state.isEmpty() && equation.isEmpty()){
			return 2;
		}
		if (myModel.getUnboundParameters().contains(state)) {
			return 3;
		}
		boolean added;
		if(isOde) {
			added = myModel.addOde(state, equation);
		} else {
			added = myModel.addAlgEq(state, equation);
		}
		if (added) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Used to get the list of equations from the model to display.
	 * @return the list of equations.
	 */
	public String[][] displayModelOdeList() {
		return myModel.displayEquationList();
	}

	/**
	 * Used to open a Model class instance that was saved earlier.
	 * @param filePath: the path to the file that you want to open
	 */
	public void openModel(String filePath) {
		if (filePath != null) {
			try {
				closeModel();
				myModel = (Model) saverLoader.load(filePath, Model.class);
				modelSaved = true;
			} catch (FileNotClosedException e) {
				// File Was not closed, then do nothing
			}
		}
	}
	
	/**
	 * Used to open an Experiment class instance that was saved earlier.
	 * @param filePath: the path to the file that you want to open
	 */
	public void openExperiment(String filePath) {
		if (filePath != null){
			try {
				closeExperiment();
				myExperiment = (Experiment) saverLoader.load(filePath, Experiment.class);
				this.myModel = this.myExperiment.getModel();
				experimentSaved = true;
			} catch (FileNotClosedException e) {
				// File Was not closed, then do nothing
			}
		}
	}

	/**
	 * Used to save a Model class instance.
	 * @param filePath: the path to the file that you want to open
	 */
	public void saveModel(String filePath) {
		if (filePath != null) {
			myModel.setName(filePath.split("[\\\\/]+")[filePath.split("[\\\\/]+").length-1]); 
			saverLoader.save(filePath, myModel);
			modelSaved = true;
		}
	}
	
	/**
	 * Used to save an Experiment class instance.
	 * @param filePath: the path to the file that you want to open
	 */
	public void saveExperiment(String filePath) {
		saverLoader.save(filePath, myExperiment);
		experimentSaved = true;
	}

	/**
	 * Used to make a new Model instance.
	 * @param name; the name of the model.
	 */
	public void newModel(String name) {
		try {
			closeModel();
			if (name == null) {
				myModel = new Model("untitled");
			}
			else {
				myModel = new Model(name);
			}
		} catch (FileNotClosedException e) {
			// File Was not closed, then do nothing
		}
	}
	
	/**
	 * Used to make a new Experiment instance.
	 * @return: a boolean that signifies the ModelVieuw if all equations that are
	 * entered are valid and that the user can continue whit the experiment.
	 */
	public boolean newExperiment() {
		try {
			if (myModel.getAreEquationsValid()) {
				closeExperiment();
				myExperiment = new Experiment(myModel);
				experimentSaved = false;
				return true;
			} else {
				return false; 
			}
		} catch (FileNotClosedException e) {
			return false;
		}
		

	}
	
	/**
	 * Used to delete equations from the model
	 * @param tableRow: the row selected in the table that has to be deleted.
	 * 	 */
	public void handleDeleteOde(Integer tableRow) {
		if (tableRow != null) {
			myModel.removeEquationAtIndex(tableRow);
			modelSaved = false;
		}
	}

	private void closeModel() throws FileNotClosedException {
		if (!modelSaved && myModel != null) {
			int result = JOptionPane.showConfirmDialog(null, myModel.getName() + " is not saved, continue?",
                    "Existing file", JOptionPane.OK_CANCEL_OPTION);
			if (!(result == JOptionPane.OK_OPTION)) {
				throw new FileNotClosedException("Experiment not closed");
			}
		}
	}
	
	private void closeExperiment() throws FileNotClosedException {
		if ((!experimentSaved || !modelSaved) && myExperiment != null) {
			int result = JOptionPane.showConfirmDialog(null, myExperiment.getName() + " is not saved, continue?",
                    "Existing file", JOptionPane.OK_CANCEL_OPTION);
			if (!(result == JOptionPane.OK_OPTION)) {
				throw new FileNotClosedException("Experiment not closed");
			}
		}
	}
	
	/**
	 * Used to ask state information for each equation in the current Model instance.
	 * @return: an array of arrays that contains all states with their respective 
	 * name, unit and description.  
	 */
	public String[][] getStateNames() {
		List<String> states = myModel.getStates();
		String[][] stateArray = new String[states.size()][3];
		for (int i = 0; i < states.size(); i++) {
			String key = states.get(i);
			String[] unitDescription = myModel.getDescriptionFromKey(key);
			stateArray[i] = new String[] {key, unitDescription[0], unitDescription[1]};
		}
		return stateArray;
	}
	
	/**
	 * Used to ask parameter information for each equation in the current Model instance.
	 * @return: an array of arrays that contains all parameters with their respective 
	 * name, unit and description.  
	 */
	public String[][] getParameterNames() {
		List<String> parameter = myModel.getParameters();
		String[][] parameterArray = new String[parameter.size()][3];
		for (int i = 0; i < parameter.size(); i++) {
			String key = parameter.get(i);
			String[] unitDescription = myModel.getDescriptionFromKey(key);
			parameterArray[i] = new String[] {key, unitDescription[0], unitDescription[1]};
		}
		return parameterArray;
	}
	
	/**
	 * Used to ask parameter information for each equation in the current Model instance.
	 * @return: an array of arrays that contains all parameters with their respective 
	 * name and value 
	 */
	public String[][] getParameterNamesValues() {
		List<String> parameterNames = myModel.getParameters();
		String[][] paramNamesValues = new String[parameterNames.size()][2];
		for (int i = 0; parameterNames.size() != i; i++ ) {
			if(parameterNames.get(i).isEmpty()) {
					
			}
			paramNamesValues[i][0] = "" + parameterNames.get(i);
			paramNamesValues[i][1] = "" + myExperiment.getParameterValue(i);
		}
		return paramNamesValues;
		
	}
	
	/**
	 * Used to get, the selected equation in the table, that has to be edited.
	 * @param selectedTableRow: integer that is the number of the row that has to be changed.
	 * @return
	 */
	//IS BROKEN!!!!!!!!!!!!!!!!!!!
	public String[] handleEditOde(Integer selectedTableRow) {
		Equation ode = myModel.getEquationAtIndex(selectedTableRow);
		String[] odeArray = ode.toArray();
		myModel.removeEquationAtIndex(selectedTableRow);
		return odeArray;
	}

	/**
	 * Used to ask state information for each equation in the current Model instance.
	 * @return: an array of arrays that contains all states with their respective 
	 * name and value 
	 */
	public String[][] getStateNamesValues() {
		List<String> stateNames = myModel.getOdeStates();
		String[][] stateNamesValues = new String[stateNames.size()][2];
		for (int i = 0; stateNames.size() != i; i++ ) {
			stateNamesValues[i][0] = "" + stateNames.get(i);
			stateNamesValues[i][1] = "" + myExperiment.getStateValue(i);
			} 
		return stateNamesValues;
	}	

	/**
	 * Used to assert that a string can be read as a integer.
	 * @param value: a String representing a double
	 * @return: true if the String can be read as a boolean and false otherwise.
	 */
	public boolean checkIfDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Used to set values from that the user has specified in the experiment table.
	 * @param stateList: list of state names and their values
	 * @param paramList: list of parameter names and their values.
	 * @param timeStep: stepsize for the euler
	 * @param timeStart: starting point for the graph and table
	 * @param timeEnd: end point for the euler table and graph.
	 */
	public void setValues(ArrayList<String> stateList, ArrayList<String> paramList, String timeStep, String timeStart, String timeEnd) {
		for (int i = 0; i < stateList.size(); i++) {
			myExperiment.setStateValue(i, Double.parseDouble((String) stateList.get(i)));
		}
		for (int i = 0; i < paramList.size(); i++) {
			myExperiment.setParameterValue(i, Double.parseDouble((String) paramList.get(i)));
		}
		myExperiment.setTimeFrame(Double.parseDouble(timeStart), Double.parseDouble(timeEnd), Double.parseDouble(timeStep));
		experimentSaved = false;
	}
	
	/**
	 * Used to get the name of the current model.
	 * @return: name of the model
	 */
	public String getModelName() {
		return myModel.getName();
	}

	/**
	 * Function to run the SolverThread class. To have the solver run on a seperate thread.
	 * @return: the instance of the solver thread so that the main thread can wait on
	 * it in the GraphTread.
	 */
	public SolverThread runExperiment() {
		// TODO Check if experiment exists. If it doesn't, display error message.
		myExperiment.resetTable();	
		SolverThread ts = myExperiment.run();
		return ts;
	}
	
	/**
	 * Used to get a array of time values from the Experiment instance.
	 * @return: an array of time values in the order timeStart, timeEnd, timeStep.
	 */
	public double[] getTimeValues() {
		double [] timeValues = myExperiment.getTimeValues();
		return timeValues;
	}

	/**
	 * Used to add a unbound parameter to the model.
	 * @param parameter: name of the parameter.
	 * @return: 0, 1 or 2. 0 if the parameter is added. 1 if the parameter already exists
	 * and 2 if the parameter is an existing state.
	 */
	public int handleButtonAddParameter(String parameter) {
		parameter = parameter.replace(" ","");
		int isAdded = myModel.addParameter(parameter);
		modelSaved = false;
		return isAdded;
	}

	/**
	 * returns the instance of the SmartTableModel associated with the Experiment instance.
	 * @return: instance of the SmartTableModel.
	 */
	public SmartTableModel getTableModel() {
		return this.myExperiment.getTableModel();
	}
	
	/**
	 * Used to remove a unbound parameter from the Model instance
	 * @param parameter: name of the parameter to be removed.
	 */
	public void handleDeleteUnboundParameter(String rowParameter) {
		if (myModel.getUnboundParameters().contains(rowParameter)) {
			myModel.removeUnboundParameter(rowParameter);
		}
	}

	/**
	 * Used to add a description and unit to a variable.
	 * @param key: name of the variable
	 * @param unit: the unit of the variable
	 * @param description: description of the variable.
	 */
	public void addDescriptionToVarTable(String key, String unit, String description) {
		myModel.addDescriptionToVarTable(key, unit, description);
	}

}

