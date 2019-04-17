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

	public void handleButtonAddOde(String state, String equation) {
		// Tests if fields are empty before adding to model and updating table.
		state = state.replace(" ", "");
		equation = equation.replace(" ", "");
		if (!state.isEmpty() && !equation.isEmpty()) {
			myModel.addOde(state, equation);
			modelSaved = false;
		}
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

	public void openModel() {
		if (closeModel()) {
			String filePath = FileChooser.open("Model", "model");
			if (filePath != null) {
				myModel = (Model) saverLoader.load(filePath, Model.class);
				modelSaved = true;
			}
		}
	}
	
	public void openExperiment() {
		if (closeExperiment()) {
			String filePath = FileChooser.open("Experiment", "exp");
			if (filePath != null) {
				myExperiment = (Experiment) saverLoader.load(filePath, Experiment.class);
				experimentSaved = true;
			} 
		}
	}

	public void saveModel() {
		String filePath = FileChooser.save("Model", "model");
		myModel.setName(filePath.split("[\\\\/]+")[filePath.split("[\\\\/]+").length-1]); 
		saverLoader.save(filePath, myModel);
		modelSaved = true;
	}
	
	public void saveExperiment() {
		String filePath = FileChooser.save("Experiment", "exp");
		saverLoader.save(filePath, myExperiment);
		experimentSaved = true;
	}

	public void newModel(String name) {
		if (closeModel()) {
			if (name == null) {
				myModel = new Model("untitled");
			}
			else {
				myModel = new Model(name);
			}
		}
	}
	
	public void newExperiment() {
		if (closeExperiment()) {
			myExperiment = new Experiment(myModel);
			experimentSaved = false;
		}
		ExperimentView expGui = new ExperimentView(this);

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

	private boolean closeModel() {
		if (!modelSaved && myModel != null) {
			int result = JOptionPane.showConfirmDialog(null, myModel.getName() + " is not saved, continue?",
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
	public String getStateString() {
		List<String> states = myModel.getStates();
		String oneStateString = "";
		for (String s :states) {
			oneStateString += s + "\n";
		}
		return oneStateString;
	}

	public String getVariableString() {
		List<String> parameters = myModel.getParameters();
		String oneParamString = "";
		for (String p :parameters) {
			if (p.equals("")) {
				continue;
			}
			oneParamString += p + "\n";
		}
		if (oneParamString.equals("")) {
			return "";
		}
		return oneParamString;
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
		System.out.println(Arrays.toString(odeArray));
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

	public void setValues(ArrayList stateList, ArrayList paramList, String timeStep, String timeStart, String timeEnd) {
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
		dataFromLastRun = myExperiment.run();
		
		String[] stateNames = myExperiment.getStateNames();
		String[] headers = new String[dataFromLastRun[0].length];
		headers[0] = "time";
		for (int i = 1; i < headers.length; i++) {
			headers[i] = stateNames[i-1];
		} // TODO: fix, so that these are actually the state names
				
		TableViewer table = new TableViewer(dataFromLastRun, headers);
		table.setVisible(true);
	}

	public double[] getTimeValues() {
		double [] timeValues = myExperiment.getTimeValues();
		return timeValues;
	}

}

