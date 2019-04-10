package smrt2;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class App {
	private Model myModel;
	private Experiment myExperiment;
	private boolean modelSaved;
	private boolean experimentSaved;
	
	
	public App() {
		myModel = new Model("untitled"); // App gets constructed w. a new model by default.
	}

	public void handleButtonAddOde(String state, String equation) {
		// Tests if fields are empty before adding to model and updating table.
		state = state.replace(" ", "");
		equation = equation.replace(" ", "");
		if (!state.isEmpty() && !equation.isEmpty()) {
			myModel.addOde(state, equation);
			
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

	public void openModel(String filePath) {
		myModel = (Model) SaverLoader.load(filePath);
	}
	
	public void openExperiment(String filePath) {
		myExperiment = (Experiment) SaverLoader.load(filePath);
	}

	public void saveModel(String filePath) {
		SaverLoader.save(myModel, filePath);
	}
	
	public void saveExperiment(String filePath) {
		SaverLoader.save(myExperiment, filePath);
	}

	public void newModel(String name) {
		if (name == null) {
			myModel = new Model("untitled");
		}
		else {
			myModel = new Model(name);
		}
		//TODO check the model is saved
	}
	
	public void newExperiment() {
		myExperiment = new Experiment(myModel);
	}
	

	public void handleDeleteOde(int tableRow) {
		//TODO implement method in model class
		myModel.removeOdeAtIndex(tableRow);
	}

	public void setVariablesList(Vector myVectors) {
		//TODO: call the map function in experiment and convert the Vector to array of doubles.
		
	}

	public boolean isModelSaved() {
		return modelSaved;
	}

	public boolean isExperimentSaved() {
		return experimentSaved;
	}
	
	

}
