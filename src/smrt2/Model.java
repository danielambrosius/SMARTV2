package smrt2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Serializable {
	
	private String name;
	private List<String> states;
	private List<Ode> odeList;
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		odeList = new ArrayList<Ode>();	
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}
	
	public void addOde(String state, String formula) {
		try {
			Ode odeToAdd = new Ode(state, formula);
			odeList.add(odeToAdd);
		} catch (Exception e) {
		}
	}

	public List<Ode> getOdeList() {
		// Returns list of all Odes
		return this.odeList;
	}

	public String[][] displayOdeList() {
		int nrOdes = odeList.size();
		String[][] displayOdeList = new String[nrOdes][2];
		
		for (int i = 0; i < odeList.size(); i++) {
			Ode currentOde = odeList.get(i);
			displayOdeList[i][0] = "d" + currentOde.getState() + "/dt";
			displayOdeList[i][1] = currentOde.getFormula();
		}
			
		return displayOdeList;
	}

	public void startNewModel() {
		setName(null);
		odeList = new ArrayList<Ode>();	
	}
	
	public List<String> getParameters() {
		List<String> params = new ArrayList<String>();
		List<String> states = getStates();
		String[] variables;
		for (Ode ode : odeList) {
			variables = ode.getVariables();
			for (String variable : variables) {
				// Check if variable is not a state and not already in parameters (params)
				if (!states.contains(variable) && !params.contains(variable)) {
					params.add(variable);
				}
			}
			
		}
		return params;
	}
	
	public List<String> getStates() {
		List<String> states = new ArrayList<String>();
		for (Ode ode : odeList) {
			states.add(ode.getState());
		}
		return states;
	}
	
	public void removeOdeAtIndex(int index) {
		//TODO add fucntionality
	}

	public Map<String, String> buildParamDict() {
		Map<String,String> paramDict = new HashMap<String,String>();
		List<String> params = getParameters();
		for (int i = 0; i < params.size();i++) {
			String value = "P" + "[" + i + "]";
			paramDict.put(params.get(i), value);
		}
		return paramDict;
	}
	
	public Map<String, String> buildStatesDict(){
		Map<String, String> statesDict = new HashMap<String, String>();
		List<String> states = getStates();
		for (int i = 0; i < states.size();i++) {
			String value = "S" + "[" + i + "]";
			statesDict.put(states.get(i), value);
		}
	return statesDict;
	}

	public String[] reconstructFormulas() {
		String[] reconstuctedFormulaList = new String[odeList.size()];
		Map<String,String> varDict = buildParamDict();
		varDict.putAll(buildStatesDict());
		
		for (int i = 0; i < odeList.size(); i++) {
			String reconstructedFormula = "";
			Ode currentOde = odeList.get(i);
			String[] currentVariables = currentOde.getVariables();
			String[] currentOperators = currentOde.getOperators();
			
			if (currentVariables[0].isEmpty()){
				for (int j = 0; j < currentOperators.length; j++) {
					reconstructedFormula += varDict.get(currentVariables[j]) + currentOperators[j];
				}
			}
			
			if (currentOperators[0].isEmpty()){
				for (int j = 0; j < currentVariables.length; j++) {
					reconstructedFormula += currentOperators[j] + varDict.get(currentVariables[j]);
				}
			}
			
			if (currentVariables.length < currentOperators.length){
				reconstructedFormula += currentOperators[currentOperators.length-1];
			}
			
			if (currentVariables.length > currentOperators.length){
				reconstructedFormula += varDict.get(currentVariables[currentVariables.length-1]);
			}
			
		reconstuctedFormulaList[i] = reconstructedFormula;
		}
		
	return reconstuctedFormulaList;	
	}
}
