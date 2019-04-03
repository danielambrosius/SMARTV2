package smrt2;

public class Model {
	
	private String name;
	
	
	//Constructor for anonymous instance.
	public Model() {
		this.name = null;
	}
	
	// Default constructor
	public Model(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;	
	}
	
}
