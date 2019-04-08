package smrt2;

public class App {
	private Model myModel;
	private Experiment myExperiment;
	
	
	public App() {
		myModel = new Model("untitled");
	}

	public void handleButtonAdd(String state, String equation) {
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
	
	

}
