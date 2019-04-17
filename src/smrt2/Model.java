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
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		odeList = new ArrayList<Ode>();	
	}
	
	@JsonCreator
	public Model (@JsonProperty("name") String name,
				  @JsonProperty("odeList") List<Ode> odeList) {
		this.name = name;
		this.odeList = odeList;
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
		
		for (int i = 0; i < odeList.size(); i++) {
			Ode currentOde = odeList.get(i);
			displayOdeList[i][0] = "d" + currentOde.getState() + "/dt";
			
			if(currentOde.testFormula()) {
				displayOdeList[i][1] = currentOde.getFormula();
			}else {
				displayOdeList[i][1] = currentOde.getFormula()+" (this is bold)";
			}
		}
			
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
}
