package gui;

import smrt2.SmartTableModel;
import smrt2.Solver;
import smrt2.SolverEulerForward;

public class SolverThread extends Thread {
	SmartTableModel model; 
	String[] formulas;
	Double[] stateInitialValues;
	Double[] parameterValues;
	double tStart;
	double tEnd;
	double tStep;
	
	public SolverThread(SmartTableModel model, String[] formulas, Double[] stateIntitialValues,
			Double[] parameterValues, double tStart, double tEnd, double tStep) {
		super();
		this.model = model;
		this.formulas = formulas;
		this.stateInitialValues = stateIntitialValues;
		this.parameterValues = parameterValues;
		this.tStart = tStart;
		this.tEnd = tEnd;
		this.tStep = tStep;
	}
	
	public void run(){
		Solver mySolver = new SolverEulerForward();
		mySolver.solve(model, formulas, stateInitialValues, parameterValues, 
				tStart, tEnd, tStep);
	}
}
