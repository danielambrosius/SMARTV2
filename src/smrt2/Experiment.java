package smrt2;
import java.util.ArrayList;
import java.util.List;
import smrt2.Parameter;

public class Experiment {
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

	public double getParameterValue(String name) {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName() == name) {
				return parameters.get(i).getValue();
			}
			
		}
		
		return 1000.0;
		
	}

}
