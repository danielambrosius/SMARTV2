package gui;

import smrt2.Model;
import smrt2.SmartTableModel;
import smrt2.Solver;
import smrt2.SolverEulerForward;

public class SolverThread extends Thread {
	SmartTableModel tableModel; 
	Model myModel;
	Double[] stateInitialValues;
	Double[] parameterValues;
	double tStart;
	double tEnd;
	double tStep;
	
	public SolverThread(SmartTableModel tableModel, Model myModel, Double[] stateIntitialValues,
			Double[] parameterValues, double tStart, double tEnd, double tStep) {
		super();
		this.tableModel = tableModel;
		this.myModel = myModel;
		this.stateInitialValues = stateIntitialValues;
		this.parameterValues = parameterValues;
		this.tStart = tStart;
		this.tEnd = tEnd;
		this.tStep = tStep;
	}
	
	public void run(){
		Solver mySolver = new SolverEulerForward();
		mySolver.solve(tableModel, myModel, stateInitialValues, parameterValues, tStart, tEnd, tStep);
	}
}
