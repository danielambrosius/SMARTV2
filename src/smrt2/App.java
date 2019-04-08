package smrt2;

public class App {
	Model myModel;
	
	
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
		myModel = SaverLoader.load(filePath);
	}

	public void saveModel(String filePath) {
		SaverLoader.save(myModel, filePath);
		
	}
	
	

}
