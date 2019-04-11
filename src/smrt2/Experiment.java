package smrt2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Experiment implements Serializable{
	private Model model;
	private List<Double> parameterValues;
	private List<Double> stateInitialValues;
	
	public Experiment(Model m) {
		model = m;
		stateInitialValues = new ArrayList<Double>();
		setStateValue(0,1.0);
		for (int i=1; m.getStates().size()+1!=i; i++){
			setStateValue(i,1.0);
		}
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

	public void setStateValue(int i, double value) {
		stateInitialValues.add(i,value);		
	}
	
	public int getStatePosition(String name) throws Exception {
		List<String> states= model.getStates();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).equals(name)) {
				return i;
			}
			
		}
		throw new Exception("State Name not found");
	}

	public double getStateValue(int pos) {
		double value = stateInitialValues.get(pos);
		return value;
	}

}