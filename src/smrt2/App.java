package smrt2;

import java.util.ArrayList;
import gui.GraphBuilder;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.text.TableView;

import gui.ExperimentView;
import gui.FileChooser;
import gui.GeneralBinaryAlert;
import gui.TableViewer;
import javafx.stage.Stage;

public class App {
	private Model myModel;
	private Experiment myExperiment;
	private boolean modelSaved;
	private boolean experimentSaved;
	private String modelName;
	private Double[][] dataFromLastRun;
	private SaverLoader saverLoader;
	
	
	public App() {
		modelSaved = false;
		experimentSaved = false;
		myModel = new Model("untitled"); // App gets constructed w. a new model by default.
		saverLoader = SaverLoader.getInstance();
	}

	public boolean handleButtonAddOde(String state, String equation) {
		// Tests if fields are empty before adding to model and updating table.
		state = state.replace(" ", "");
		equation = equation.replace(" ", "");
		boolean isAdded = false;
		if (!state.isEmpty() && !equation.isEmpty()) {
			isAdded = myModel.addOde(state, equation);
			modelSaved = false;
		}
		return isAdded;
	}

	public String[][] displayModelOdeList() {
		return myModel.displayOdeList();
	}

	
	public String[][] displayVariablesList() {
		String[][] myVariables = new String[5][5];
		for (int i = 0; i < 5; i++) {
			myVariables[i][0] = "k" + i;
			myVariables[i][1] = "" + i;
		}
//		return myExperiment.displayVariableList();
		return myVariables;
	}

	public void openModel(String filePath) {
		if (filePath != null) {
			myModel = (Model) saverLoader.load(filePath, Model.class);
			modelSaved = true;
		}
	}
	
	public void openExperiment(String filePath) {
		if (closeExperiment() && filePath != null){
			myExperiment = (Experiment) saverLoader.load(filePath, Experiment.class);
			this.myModel = this.myExperiment.getModel();
			experimentSaved = true;
		}
	}

	public void saveModel(String filePath) {
		if (filePath != null) {
			myModel.setName(filePath.split("[\\\\/]+")[filePath.split("[\\\\/]+").length-1]); 
			saverLoader.save(filePath, myModel);
			modelSaved = true;
		}
	}
	
	public void saveExperiment(String filePath) {
		saverLoader.save(filePath, myExperiment);
		experimentSaved = true;
	}

	public void newModel(String name) {
		if (name == null) {
			myModel = new Model("untitled");
		}
		else {
			myModel = new Model(name);
		}
	}
	
	public boolean newExperiment() {
		
		if (closeExperiment() && myModel.getAreOdesValid()) {
			myExperiment = new Experiment(myModel);
			experimentSaved = false;
			
			return true;
		}
		return false; 
		

	}
	

	public void handleDeleteOde(Integer tableRow) {
		//TODO implement method in model class
		if (tableRow != null) {
			myModel.removeOdeAtIndex(tableRow);
			modelSaved = false;
		}
	}

	public void setVariablesList(Vector myVectors) {
		//TODO: call the map function in experiment and convert the Vector to array of doubles.
	}

	public boolean closeModel() {
		if (!modelSaved && myModel != null) {
			return true;
		}
		return false;
	}
	
	private boolean closeExperiment() {
		if (!experimentSaved && myExperiment != null) {
			int result = JOptionPane.showConfirmDialog(null, myExperiment.getName() + " is not saved, continue?",
                    "Existing file", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				return true;
				}
			else {
				 return false;
			}
		}
		else {
			return true;
		}
	}
	public String[][] getStateNames() {
		List<String> states = myModel.getStates();
		String[][] stateArray = new String[states.size()][3];
		for (int i = 0; i < states.size(); i++) {
			stateArray[i] = new String[] {states.get(i), "", ""};
		}
		return stateArray;
	}

	public String[][] getParameterNames() {
		List<String> parameter = myModel.getParameters();
		String[][] parameterArray = new String[parameter.size()][3];
		for (int i = 0; i < parameter.size(); i++) {
			parameterArray[i] = new String[] {parameter.get(i), "", ""};
		}
		return parameterArray;
	}

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

	public String[] handleEditOde(Integer selectedTableRow) {
		Ode ode = myModel.getOdeAtIndex(selectedTableRow);
		String[] odeArray = ode.toArray();
		myModel.removeOdeAtIndex(selectedTableRow);
		return odeArray;
	}


	public String[][] getStateNamesValues() {
		List<String> stateNames = myModel.getStates();
		String[][] stateNamesValues = new String[stateNames.size()][2];
		for (int i = 0; stateNames.size() != i; i++ ) {
			stateNamesValues[i][0] = "" + stateNames.get(i);
			stateNamesValues[i][1] = "" + myExperiment.getStateValue(i);
		}
		return stateNamesValues;
	}

	public boolean checkIfDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

	public void setValues(ArrayList stateList, ArrayList paramList, String timeStep, String timeStart, String timeEnd) {
		Map<String,String> incorrectValues = new HashMap<String,String>();
		for (int i = 0; i < stateList.size(); i++) {
			myExperiment.setStateValue(i, Double.parseDouble((String) stateList.get(i)));
		}
		for (int i = 0; i < paramList.size(); i++) {
			myExperiment.setParameterValue(i, Double.parseDouble((String) paramList.get(i)));
		}
		myExperiment.setTimeFrame(Double.parseDouble(timeStart), Double.parseDouble(timeEnd), Double.parseDouble(timeStep));
	}

	public String getModelName() {
		return myModel.getName();
	}

	public void runExperiment() {
		// TODO Check if experiment exists. If it doesn't, display error message.
		myExperiment.resetTable();	
		myExperiment.run();
	}

	public double[] getTimeValues() {
		double [] timeValues = myExperiment.getTimeValues();
		return timeValues;
	}

	public int handleButtonAddParameter(String parameter) {
		int isAdded = myModel.addParameter(parameter);
		return isAdded;
	}

	public SmartTableModel getTableModel() {
		return this.myExperiment.getTableModel();
	}

}

