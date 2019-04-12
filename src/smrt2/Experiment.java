package smrt2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experiment implements Serializable{
	private Model model;
	private Double[] parameterValues;
	private Double[] stateInitialValues;
	private double tStart;
	private double tEnd = 10;
	private double tStep = 1;
	
	public Experiment(Model m) {
		model = m;
		stateInitialValues = new Double[m.getStates().size()];
		Arrays.fill(stateInitialValues, 1.0);
		parameterValues = new Double[m.getParameters().size()];
		Arrays.fill(parameterValues, 1.0);
	}

	public void setParameterValue(int i, double value) {
		parameterValues[i] = value;		
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
		double value = parameterValues[pos];
		return value;
	}
	
	public String getName() {
		
		return "Experiment";
	}

	public void setStateValue(int i, double value) {
		stateInitialValues[i] = value;		
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
		double value = stateInitialValues[pos];
		return value;
	}

	private Map<String, String> buildParamDict() {
		Map<String,String> paramDict = new HashMap<String,String>();
		List<String> params = model.getParameters();
		for (int i = 0; i < params.size();i++) {
			String value = "P" + "[" + i + "]";
			paramDict.put(params.get(i), value);
		}
		return paramDict;
	}
	
	private Map<String, String> buildStatesDict(){
		Map<String, String> statesDict = new HashMap<String, String>();
		List<String> states = model.getStates();
		for (int i = 0; i < states.size();i++) {
			String value = "S" + "[" + (i+1) + "]";
			statesDict.put(states.get(i), value);
		}
	return statesDict;
	}

	public String[] reconstructFormulas() {
		int nStates = model.getStates().size();
		String[] reconstuctedFormulaList = new String[nStates];
		Map<String,String> varDict = buildParamDict();
		varDict.putAll(buildStatesDict());
		
		for (int i = 0; i < nStates; i++) {
			String reconstructedFormula = "";
			String[] currentVariables = model.getVariablesOfOde(i);
			String[] currentOperators = model.getOperatorsOfOde(i);
			
			if (currentVariables[0].isEmpty()){
				for (int j = 0; j < currentOperators.length; j++) {
					reconstructedFormula += varDict.get(currentVariables[j]) + currentOperators[j];
				}
			}
			
			else if (currentOperators.length > 0) {
				if (currentOperators[0].isEmpty()){
					for (int j = 0; j < currentVariables.length; j++) {
						reconstructedFormula += currentOperators[j] + varDict.get(currentVariables[j]);
					}
				}
			}
		
			if (currentVariables.length < currentOperators.length){
				reconstructedFormula += currentOperators[currentOperators.length-1];
			}
			
			if (currentVariables.length > currentOperators.length){
				reconstructedFormula += varDict.get(currentVariables[currentVariables.length-1]);
			}
			
		reconstuctedFormulaList[i] = reconstructedFormula;
		}
		
	return reconstuctedFormulaList;	
	}

	public void setTimeFrame(double tStart, double tEnd, double tStep) {
		this.tStart = tStart;
		this.tEnd = tEnd;
		this.tStep = tStep;		
	}

	public Double[][] run() {
		Solver s = new Solver(reconstructFormulas(), this.stateInitialValues, this.parameterValues, this.tEnd, this.tStep);
		return s.solve();
	}

	public String[] getStateNames() {
		return (String[]) model.getStates().toArray(new String[0]);
	}
}