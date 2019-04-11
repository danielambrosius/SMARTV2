package smrt2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import smrt2.Parameter;

public class Experiment implements Serializable{
	private Model model;
	private List<Double> parameterValues;
	
	public Experiment(Model m) {
		model = m;
		parameterValues = new ArrayList<Double>();
		for (int i=0; m.getParameters().size()!=i; i++){
			setParameterValue(i,1.0);
		}
	}

	public void setParameterValue(int i, double value) {
		parameterValues.add(i,value);		
	}

	public int getParameterPosition(String name) throws Exception {
		List<String> parameters= model.getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).equals(name)) {
				return i;
			}
			
		}
		throw new Exception("Parameter Name not found");
		
	}

	public double getParameterValue(int pos) {
		double value = parameterValues.get(pos);
		return value;
	}
	
	public String getName() {
		
		return "Experiment";
	}

}