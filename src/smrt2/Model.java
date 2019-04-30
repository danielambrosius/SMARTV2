package smrt2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void removeUnboundParameter(String parameter) {
		unboundParameters.remove(parameter);
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
				params.add(unboundParameters.get(i)+ " (Unbound)");
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
	
	/**
	 * Returns a String[] containing user specified formulas in solver compatible syntax.
	 * Parameters and states are replaced by references to arrays that will store the data.
	 */
	public String[] reconstructFormulas() {
		int nStates = getStates().size(); //number of states
		//create a list to store the results
		//each state has a corresponding formula so they have the same length
		String[] reconstuctedFormulaList = new String[nStates];
		//create a HashMap that translates variable names to array references that will hold the data.
		Map<String,String> varDict = buildParamDict();
		varDict.putAll(buildStatesDict()); //combine both HashMaps for convenience.
		
		for (int i = 0; i < nStates; i++) {
			String reconstructedFormula = "";
			String[] currentVariables = getVariablesOfEquation(i);
			String[] currentOperators = getOperatorsOfEquation(i);
			
			// the parser adds a empty string to either the list of variables or list of operators.
			// the empty string ends up in the list of whatever the formula starts with.
			// this information is used to reconstruct the formula in the correct order.
			if (currentVariables[0].isEmpty()){
				// the list that contains the empty string could be larger so index on the length of the other list 
				for (int j = 0; j < currentOperators.length; j++) {
					if(varDict.get(currentVariables[j]) != null) {
						reconstructedFormula += varDict.get(currentVariables[j]) + currentOperators[j];
					} else {
						reconstructedFormula += currentOperators[j];
					}
				}
			}
			
			//check if ode equation has an operator
			else if (currentOperators.length > 0) {
				for (int j = 0; j < currentVariables.length; j++) {
					reconstructedFormula += currentOperators[j] + varDict.get(currentVariables[j]);
				}
			}
			
			// if the variable list was larger that the operator list the last variable needs to be added and vice versa 
			if (currentVariables.length > currentOperators.length){
				if (varDict.get(currentVariables[currentVariables.length-1]) != null) {
					reconstructedFormula += varDict.get(currentVariables[currentVariables.length-1]);
				}
			}
			
			if (currentVariables.length < currentOperators.length){
				reconstructedFormula += currentOperators[currentOperators.length-1];
			}
		
		reconstuctedFormulaList[i] = reconstructedFormula;
		}
	return reconstuctedFormulaList;	
	}

	/**
	 * Returns a HashMap that links a parameter name to a reference of an index in array P.
	 * Array P will hold user specified parameter values.
	 * This method is used by the reconstructFormula method.
	 */
	private Map<String, String> buildParamDict() {
		Map<String,String> paramDict = new HashMap<String,String>();
		List<String> params = getParameters();
		params.removeAll(Arrays.asList("", null));
		if(params.size() > 0) {
			for (int i = 0; i < params.size();i++) {
				String value = "P" + "[" + i + "]";
				
				paramDict.put(params.get(i), value);
			}
		}
		
		return paramDict;
	}

	/**
	 * Returns a HashMap that links a state name to a reference of an index in array S.
	 * Array S contains the solutions of the model at the previous step in time.
	 * This method is used by the reconstructFormula method.
	 */
	private Map<String, String> buildStatesDict(){
		Map<String, String> statesDict = new HashMap<String, String>();
		List<String> states = getStates();
		
		
		for (int i = 0; i < states.size(); i++) {
			String value = "S" + "[" + (i+1) + "]";
			
			statesDict.put(states.get(i), value);
		}
		statesDict.put("t", "S[0]");
	return statesDict;
	}

}
