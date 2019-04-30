package smrt2;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Model{
	
	private String name;
	private List<Equation> equationList;
	private boolean areEquationsValid;
	private List<String> unboundParameters;
	
	public List<String> getUnboundParameters() {
		return unboundParameters;
	}
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		equationList = new ArrayList<Equation>();	
		this.areEquationsValid = true;
		this.unboundParameters = new ArrayList<String>();
	}
	
	@JsonCreator
	public Model (@JsonProperty("name") String name,
				  @JsonProperty("Equation") List<Equation> equationList,
				  @JsonProperty("areEquationsValid") boolean areEquationsValid,
				  @JsonProperty("unboundParameters") List<String> unboundParameters) {
		this.name = name;
		this.equationList = equationList;
		this.areEquationsValid = areEquationsValid;
		this.unboundParameters = unboundParameters;
	}
	
	public boolean getAreEquationsValid() {
		return this.areEquationsValid;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}
	
	public boolean addOde(String state, String formula) {
		Ode odeToAdd = new Ode(state, formula);
		if (!this.getStates().contains(state)){
		equationList.add(odeToAdd);
		return true;
		}
		return false;
		
	}
	
	public boolean addAlgEq(String leftHandSide, String rightHandSide) {
		AlgEq algEqToAdd = new AlgEq(leftHandSide, rightHandSide);
		if (!this.getStates().contains(leftHandSide)){
		equationList.add(algEqToAdd);
		return true;
		}
		return false;
		
	}

	public List<Equation> getEquationList() {
		// Returns list of all Odes
		return this.equationList;
	}

	public String[][] displayEquationList() {
		String[][] displayEquationList = new String[equationList.size()][2];
		boolean allCorrect = true;
		for (int i = 0; i < equationList.size(); i++) {
			Equation currentEquation = equationList.get(i);
		if (currentEquation instanceof Ode) {
			displayEquationList[i][0] = "d" + currentEquation.getLeftHandSide() + "/dt";
		} else {
			displayEquationList[i][0] = currentEquation.getLeftHandSide();
		}
			if(currentEquation.testRightHandSide()) {
				displayEquationList[i][1] = currentEquation.getRightHandSide();
			}else {
				displayEquationList[i][1] = currentEquation.getRightHandSide()+" (Incorrect syntax)";
				allCorrect = false;
			}
		}
		this.areEquationsValid = allCorrect;
		return displayEquationList;
	}

	public void startNewModel() {
		setName(null);
		equationList = new ArrayList<Equation>();	
	}
	
	@JsonIgnore
	public List<String> getParameters() {
		List<String> params = new ArrayList<String>();
		List<String> states = getStates();
		String[] variables;
		for (Equation ode : equationList) {
			variables = ode.getVariables();
			for (String variable : variables) {
				// Check if variable is not a state and not already in parameters (params) 
				if (!states.contains(variable) && !params.contains(variable) && !variable.isEmpty() && !variable.equals("t")) {
					params.add(variable);
				}
			}
	
		}
		for (int i = 0; i < unboundParameters.toArray().length; i++) {
			if(!params.contains(unboundParameters.get(i)) && !states.contains(unboundParameters.get(i))) {
				params.add(unboundParameters.get(i));
			}else {
				unboundParameters.remove(unboundParameters.get(i));

			}
		}

		return params;
	}
	
	@JsonIgnore
	public List<String> getStates() {
		List<String> states = new ArrayList<String>();
		for (Equation ode : equationList) {
			states.add(ode.getLeftHandSide());
		}
		return states;
	}
	
	public void removeEquationAtIndex(int index) {
		equationList.remove(index);
	}



	public Equation getEquationAtIndex(Integer selectedTableRow) {
		Equation myEquation = equationList.get(selectedTableRow);
		return myEquation;
		
	}

	public String[] getVariablesOfEquation(int i) {
		Equation myEquation = equationList.get(i);
		return myEquation.getVariables();
	}

	public String[] getOperatorsOfEquation(int i) {
		Equation myEquation = equationList.get(i);
		return myEquation.getOperators();
	}
	
	//0 = added
	//1 = is already Parameter
	//2 = is already State
	public int addParameter(String parameter) {
		if(getParameters().contains(parameter)) {
			return 1;
		}
		if(getStates().contains(parameter)) {
			return 2;
		}		
		unboundParameters.add(parameter);
		return 0;
	}

}
