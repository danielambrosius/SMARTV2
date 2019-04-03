package smrt2;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private String name;
	private List<String> params;
	
	
	//Constructor for anonymous instance.
	public Model() {
		this(null);
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
		params=new ArrayList<String>();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}

	public void addParameter(String param) {
		params.add(param);	
	}

	public List<String> getParameters() {
		return params;
	}
	
}
