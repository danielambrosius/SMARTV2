package gui;

//https://www.developer.com/java/data/working-with-javafx-chart-apis.html
//credits to: Manoj Debnath



import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import smrt2.SmartTableModel;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;



public class GraphBuilder{
	
	private Double[] time;
	private Double[] data;
	private String title;
	
	public GraphBuilder(Double[] time, Double[] data, String colName) {
		this.title = colName;
		this.data = data;
		this.time = time;
	}
	
//	public GraphBuilder(int xNumber, Double[][] inputData, String title) {
//		this.data = inputData;
//		this.xNumber = xNumber;
//		this.title = title;
//	}

	


public static XYChart.Series<Number, Number> ArraysToSeries(Double[] time, Double[] data){
	XYChart.Series<Number, Number> chartdata = new XYChart.Series<Number, Number>();
	for (int i=0; i < data.length; i++) {
		chartdata.getData().add(new XYChart.Data<Number, Number>(time[i], data[i]));
	}
	return chartdata;
}


public LineChart<Number, Number> start() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    //int xNumber = 1;
    lineChart.setTitle("Test");
    XYChart.Series<Number, Number> seriesData = ArraysToSeries(time, data);
    seriesData.setName(title);
    
    lineChart.getData().add(seriesData);
    lineChart.setTitle(title);
    
    return lineChart;
    
    //primaryStage.setTitle("LineChart example");
    
    //StackPane root = new StackPane();
    //root.getChildren().add(lineChart);
    //primaryStage.setScene(new Scene(root, 400, 250));
}
}
