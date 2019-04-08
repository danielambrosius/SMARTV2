package smrt2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Serializable {
	
	private String name;
	private List<String> params;
	private List<String> states;
	private List<Ode> odeList;
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		params = new ArrayList<String>();
		states = new ArrayList<String>();
		odeList = new ArrayList<Ode>();	
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}

	public void addParameter(String param) throws Exception {
		if (params.contains(param)) {
			throw new Exception("Duplicate parameters not allowed");
		} else {
			params.add(param);
		}
	}

	public List<String> getParameters() {
		return params;
	}
	
	public void addState(String state) throws Exception {
		if (states.contains(state)) {
			throw new Exception("Duplicate states not allowed");
		} else {
			states.add(state);
		}
	}

	public List<String> getStates() {
		return states;
	}

	
	public void addOde(String state, String formula) {
		try {
			addState(state);
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
		params = new ArrayList<String>();
		states = new ArrayList<String>();
		odeList = new ArrayList<Ode>();	
	}
	
	public void findParameters() {
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
		
	}
	
	public void removeOdeAtIndex(int index) {
		//TODO add fucntionality
	}

	public Map<String, Double> buildParamDict(double[] paramValues) {
		Map<String,Double> paramDict = new HashMap<String,Double>();
		for (int i = 0; i < paramValues.length;i++) {
			paramDict.put(getParameters().get(i), paramValues[i]);
		}
		return paramDict;
	}
}
