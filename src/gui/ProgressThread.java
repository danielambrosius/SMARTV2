package gui;

import javax.swing.JProgressBar;

import smrt2.SmartTableModel;

public class ProgressThread extends Thread {
	private JProgressBar progressBar;
	private SmartTableModel tableModel;
	private double[] timeValues;
	
	public ProgressThread(JProgressBar progressBar, SmartTableModel tableModel, double[] timeValues) {
		this.progressBar = progressBar;
		this.tableModel = tableModel;
		this.timeValues = timeValues;
	}

	public void run() {
		double maxLenght = (int) ((timeValues[1] - timeValues[0])/ timeValues[2]);
		double n = 0;
		progressBar.setValue(50);
		while (n < 100) {
			n = (tableModel.getRowCount()/ maxLenght) *100;
			progressBar.setValue((int) n);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
