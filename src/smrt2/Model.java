package smrt2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Model implements Serializable {
	
	private String name;
	private List<Ode> odeList;
	private boolean areOdesValid;
	private List<String> unboundParameters;
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		odeList = new ArrayList<Ode>();	
		this.areOdesValid = true;
		this.unboundParameters = new ArrayList<String>();
	}
	
	@JsonCreator
	public Model (@JsonProperty("name") String name,
				  @JsonProperty("odeList") List<Ode> odeList,
				  @JsonProperty("areOdesValid") boolean areOdesValid,
				  @JsonProperty("unboundParameters") List<String> unboundParameters) {
		this.name = name;
		this.odeList = odeList;
		this.areOdesValid = areOdesValid;
		this.unboundParameters = unboundParameters;
	}
	
	public boolean getAreOdesValid() {
		return this.areOdesValid;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}
	
	public void addOde(String state, String formula) {
		Ode odeToAdd = new Ode(state, formula);
		odeList.add(odeToAdd);
	}

	public List<Ode> getOdeList() {
		// Returns list of all Odes
		return this.odeList;
	}

	public String[][] displayOdeList() {
		int nrOdes = odeList.size();
		String[][] displayOdeList = new String[nrOdes][2];
		boolean allCorrect = true;
		for (int i = 0; i < odeList.size(); i++) {
			Ode currentOde = odeList.get(i);
			displayOdeList[i][0] = "d" + currentOde.getState() + "/dt";
			if(currentOde.testFormula()) {
				displayOdeList[i][1] = currentOde.getFormula();
			}else {
				displayOdeList[i][1] = currentOde.getFormula()+" (Incorrect syntax)";
				allCorrect = false;
			}
		}
		this.areOdesValid = allCorrect;
		return displayOdeList;
	}

	public void startNewModel() {
		setName(null);
		odeList = new ArrayList<Ode>();	
	}
	
	@JsonIgnore
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
		for(String param : unboundParameters) {
			params.add(param);
		}
		return params;
	}
	
	@JsonIgnore
	public List<String> getStates() {
		List<String> states = new ArrayList<String>();
		for (Ode ode : odeList) {
			states.add(ode.getState());
		}
		return states;
	}
	
	public void removeOdeAtIndex(int index) {
		odeList.remove(index);
	}



	public Ode getOdeAtIndex(Integer selectedTableRow) {
		Ode ode = odeList.get(selectedTableRow);
		return ode;
		
	}

	public String[] getVariablesOfOde(int i) {
		Ode myOde = odeList.get(i);
		return myOde.getVariables();
	}

	public String[] getOperatorsOfOde(int i) {
		Ode myOde = odeList.get(i);
		return myOde.getOperators();
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
