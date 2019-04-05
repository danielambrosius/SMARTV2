package smrt2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	public void save(String path){
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			objectOut.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
			displayOdeList[i][1] = currentOde.getFormula();
		}
			
		return displayOdeList;
	}
}
