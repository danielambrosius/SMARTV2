package smrt2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import smrt2.Parameter;

public class Experiment implements Serializable{
	private Model model;
	private List<Parameter> parameters;
	
	public Experiment(Model m) {
		model = m;
		parameters = new ArrayList<Parameter>();
		for (int i = 0; i < m.getParameters().size(); i++) {
			
			Parameter p = new Parameter(m.getParameters().get(i), 0.0);
			parameters.add(p);
			
		}
	}
		
	

	public List<Parameter> getParameters() {
		return parameters;
	}

	public int getParameterPosition(String name) throws Exception {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName() == name) {
				return i;
			}
			
		}
		throw new Exception("Parameter Name not found");
		
	}



	public String getName() {
		
		return "Experiment";
	}
	
	

}
