package gui;

import java.util.List;

import smrt2.Equation;
import smrt2.SmartTableModel;
import smrt2.Solver;
import smrt2.SolverEulerForward;

public class SolverThread extends Thread {
	SmartTableModel model; 
	List<Equation> equationList;
	String[] formulas;
	Double[] stateInitialValues;
	Double[] parameterValues;
	double tStart;
	double tEnd;
	double tStep;
	
	public SolverThread(SmartTableModel model, List<Equation> equationList, String[] formulas, Double[] stateIntitialValues,
			Double[] parameterValues, double tStart, double tEnd, double tStep) {
		super();
		this.model = model;
		this.equationList = equationList;
		this.formulas = formulas;
		this.stateInitialValues = stateIntitialValues;
		this.parameterValues = parameterValues;
		this.tStart = tStart;
		this.tEnd = tEnd;
		this.tStep = tStep;
	}
	
	public void run(){
		Solver mySolver = new SolverEulerForward();
		mySolver.solve(model, equationList, formulas, stateInitialValues, parameterValues, 
				tStart, tEnd, tStep);
	}
}
