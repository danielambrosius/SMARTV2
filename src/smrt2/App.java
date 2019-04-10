package smrt2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import gui.FileChooser;
import gui.GeneralBinaryAlert;

public class App {
	private Model myModel;
	private Experiment myExperiment;
	private boolean modelSaved;
	private boolean experimentSaved;
	
	
	public App() {
		modelSaved = false;
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

	public void openModel() {
		String filePath = FileChooser.open("Model", "model");
		myModel = (Model) SaverLoader.load(filePath);
	}
	
	public void openExperiment() {
		String filePath = FileChooser.open("Experiment", "exp");
		myExperiment = (Experiment) SaverLoader.load(filePath);	
	}

	public void saveModel() {
		String filePath = FileChooser.save("Model", "model");
		SaverLoader.save(myModel, filePath);
	}
	
	public void saveExperiment() {
		String filePath = FileChooser.save("Experiment", "exp");
		SaverLoader.save(myExperiment, filePath);
	}

	public void newModel(String name) {
		closeModel();
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

	private void closeModel() {
		if (!modelSaved) {
			System.out.println("AAAA");
			String input = JOptionPane.showInputDialog("HELLO");
			if (input != null) { 
				System.out.println("OK WAS CLICKED");
			} else {
				System.out.println("Cancel was clicked");
			}
			// TODO Continue tomorrow.
			//GeneralBinaryAlert prompt = new GeneralBinaryAlert("Open Model is not saved");
			// prompt.setVisible(true);
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
		List<String> paramaters = myModel.getParameters();
		String oneParamString = "";
		for (String p :paramaters) {
			oneParamString += p + "\n";
		}
		return oneParamString;
	}
	
}

