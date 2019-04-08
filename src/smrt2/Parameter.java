package smrt2;

public class Parameter {
	private String name;
	private double value;
	public Parameter(String string, double d) {
		this.name = string;
		this.value = d;
	}
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double d) {
		this.value = d;
	}

	public String getName() {
		return this.name;
	}

}
