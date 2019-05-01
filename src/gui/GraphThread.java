package gui;

import javax.swing.JTabbedPane;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import smrt2.SmartTableModel;

public class GraphThread extends Thread {

	private SolverThread st;
	private JTabbedPane tabbedPane;
	private SmartTableModel tableModel;
	
	public GraphThread(SolverThread st, JTabbedPane tabbedPane, SmartTableModel tableModel) {
		super();
		this.st = st;
		this.tabbedPane = tabbedPane;
		this.tableModel = tableModel;
	}

	public void run(){
		//wait for the solverThread to finish before drawing the graphs
		try {
			st.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//draw the graphs
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			JFXPanel graphPane = new JFXPanel();
			tabbedPane.addTab(tableModel.getColumnName(i), null, graphPane, null);
			StackPane pane = new StackPane();
			GraphBuilder GB = new GraphBuilder(tableModel, new int[]{i});
			LineChart<Number, Number> lineChart = GB.start();
				    
		    pane.getChildren().add(lineChart);
			Scene scene = new Scene(pane);
			graphPane.setScene(scene);
		}
	}
}
