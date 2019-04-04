package smrt2;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
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

	public void addODE(Ode odeToAdd) {
		odeList.add(odeToAdd);
	}
	
	public void readODE(String state, String formula) {
		addODE(new Ode(state, formula));
	}

	public List<Ode> getODEs() {
		// Returns list of all Odes
		return this.odeList;
	}
}
