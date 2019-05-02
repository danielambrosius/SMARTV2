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
	private Map<String, String[]> varDescription = new HashMap<String, String[]>();
	
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
		//TODO add descriptions to json
		this.name = name;
		this.equationList = equationList;
		this.areEquationsValid = areEquationsValid;
		this.unboundParameters = unboundParameters;
	}
	
	/**
	 * Used to get unbound parameters from the model
	 * @return a List with the unbound Parameters
	 */
	public List<String> getUnboundParameters() {
		return unboundParameters;
	}
	
	private void updateVarTable() {
		String[] placeHolder = {"", ""};
		for (Equation equation : equationList) {
			varDescription.putIfAbsent(equation.getLeftHandSide(), placeHolder);
			for (String var : equation.getVariables()) {
				varDescription.putIfAbsent(var, placeHolder);
			}
		}
		for (String var : unboundParameters) {
			varDescription.putIfAbsent(var, placeHolder);
		}
	}
	 /**
	  * Used to add the description to the variable table
	  * @param key is the name of the variable
	  * @param unit is the unit of the variable
	  * @param description is the description of the variable
	  */
	//TODO look at this description
	public void addDescriptionToVarTable(String key, String unit, String description) {
		if(varDescription.containsKey(key)) {
			String[] value = {unit, description};
			varDescription.replace(key, value);
		}
	}
	
	/**
	 * Used to get the description of a variable from the model
	 * @param key is a string of the parameter or variable
	 * @return is the description of the variable
	 */
	public String[] getDescriptionFromKey(String key) {
		return varDescription.get(key);
	}
	
	/**
	 * Used to remove a unbound parameter from the model
	 * @param parameter is the name of the unbound parameter that has to be deleted
	 */
	public void removeUnboundParameter(String parameter) {
		unboundParameters.remove(parameter);
	}
	
	/**
	 * Used to check if all the equations in the model are correct.
	 * @return a boolean which represents if all equations valid
	 */
	public boolean getAreEquationsValid() {
		return this.areEquationsValid;
	}
	
	/**
	 * Used to get the name of the model 
	 * @return the name of the model
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Used to set the name of the model
	 * @param name of that the model should have
	 */
	public void setName(String name) {
		this.name = name;	
	}
	
	/**
	 * Used to add an Ode to the model
	 * @param state is the right handside of the ode
	 * @param formula is the left handside of the ode 
	 * @return returns boolean true if the Ode is added to the model
	 */
	public boolean addOde(String state, String formula) {
		Ode odeToAdd = new Ode(state, formula);
		if (!this.getStates().contains(state)){
			equationList.add(odeToAdd);
			updateVarTable();
			return true;
		}
		return false;
	}
	
	/**
	 * Used to add an algebraic equation to the model
	 * @param leftHandSide is the left hand side of the algebraic equation
	 * @param rightHandSide is the right hand side of the algebraic equation
	 * @return the boolean true if the algebraic equation is added to the model
	 */
	public boolean addAlgEq(String leftHandSide, String rightHandSide) {
		AlgEq algEqToAdd = new AlgEq(leftHandSide, rightHandSide);
		if (!this.getStates().contains(leftHandSide)){
			equationList.add(algEqToAdd);
			updateVarTable();
			return true;
		}
		return false;
	}

	/**
	 * Used to get the full equation list from the model
	 * @return the list of equations of the model
	 */
	public List<Equation> getEquationList() {
		return this.equationList;
	}

	/**
	 * Used to get the Odes in the correct way to show to the user
	 * @return a string array with the equation list with the user lay-out
	 */
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
	
	/**
	 * Used to get the parameters from the model
	 * @return a list of the parameter names
	 */
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
	/**
	 * Used to get all the states from the model
	 * @return a list of all the state names
	 */
	@JsonIgnore
	public List<String> getStates() {
		List<String> states = new ArrayList<String>();
		for (Equation ode : equationList) {
			states.add(ode.getLeftHandSide());
		}
		return states;
	}
	
	/**
	 * Used to get the Ode states from the model
	 * @return a list of the Ode states names
	 */
	@JsonIgnore
	public List<String> getOdeStates() {
		List<String> states = new ArrayList<String>();
		for (Equation ode : equationList) {
			if (ode instanceof Ode) {
				states.add(ode.getLeftHandSide());
			}
		}
		return states;
	}
	
	/**
	 * Used to remove a equation from the model at a certain index of the 
	 * equation list
	 * @param index is the number of the equation to be removed from the model
	 */
	public void removeEquationAtIndex(int index) {
		equationList.remove(index);
	}

	/**
	 * Used to get the equation at a certain index of the equation list
	 * @param selectedTableRow is the index of the equation in the list
	 * @return the equation at that index
	 */
	public Equation getEquationAtIndex(Integer selectedTableRow) {
		Equation myEquation = equationList.get(selectedTableRow);
		return myEquation;
	}

	/**
	 * Used to get the variables from a certain equation
	 * @param i is the index of the equation in the list
	 * @return the string array with the variables of the equation
	 */
	public String[] getVariablesOfEquation(int i) {
		Equation myEquation = equationList.get(i);
		return myEquation.getVariables();
	}

	/**
	 * Used to get the operators of a certain equation
	 * @param i is the index of the equation in the list
	 * @return the string array with the operators of the equation
	 */
	public String[] getOperatorsOfEquation(int i) {
		Equation myEquation = equationList.get(i);
		return myEquation.getOperators();
	}
	
	/**
	 * used to add a parameter to the model
	 * @param parameter
	 * @return a number that corresponds with the reason 
	 * 		   of the parameter not added.
	 * 0 = parameter is added
	 * 1 = parameter to be added is already a parameter
	 * 2 =parameter to be added is already a state
	 */
	public int addParameter(String parameter) {
		if(getParameters().contains(parameter)) {
			return 1;
		}
		if(getStates().contains(parameter)) {
			return 2;
		}		
		unboundParameters.add(parameter);
		updateVarTable();
		return 0;
	}
	
	/** 
	 * Parameters and states in the formulas are replaced by references to arrays that will store the data.
	 * @return a String[] containing user specified formulas in solver compatible syntax.
	 */
	public String[] reconstructFormulas() {
		int nStates = getStates().size();
		//each state has a corresponding formula so they have the same length
		String[] reconstuctedFormulaList = new String[nStates];
		Map<String,String> varDict = buildParamDict();
		varDict.putAll(buildStatesDict()); 
		
		for (int i = 0; i < nStates; i++) {
			String reconstructedFormula = "";
			String[] currentVariables = getVariablesOfEquation(i);
			String[] currentOperators = getOperatorsOfEquation(i);
			
			/** 
			 * the parser adds a empty string to either the list of variables or list of operators.
			 * the empty string ends up in the list of whatever the formula starts with.
			 * this information is used to reconstruct the formula in the correct order.
			 */
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
	 * This method is used by the reconstructFormula method to build a dictionary for the parameters
	 * @return a HashMap that links a parameter name to a reference of an index in array P.
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
	 * This method is used by the reconstructFormula method to build a dictionary for the undefined states
	 * @return a HashMap that links a state name to a reference of an index in array S.
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
