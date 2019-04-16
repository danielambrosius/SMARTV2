package gui;

//https://www.developer.com/java/data/working-with-javafx-chart-apis.html
//credits to: Manoj Debnath



import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;



public class GraphBuilder{
	
	private Double[][] data;
	private int xNumber;
	private String title;
	
	public GraphBuilder(int xNumber, Double[][] inputData, String title) {
		this.data = inputData;
		this.xNumber = xNumber;
		this.title = title;
	}


public static XYChart.Series<Number, Number> ArraysToSeries(int xNumber, Double[][] inputdata){
	XYChart.Series<Number, Number> chartdata = new XYChart.Series<Number, Number>();
	for (int i=0; i < inputdata.length; i++) {
		chartdata.getData().add(new XYChart.Data<Number, Number>(inputdata[i][0], inputdata[i][xNumber]));
	}
	return chartdata;
}


public LineChart<Number, Number> start() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    //int xNumber = 1;
    lineChart.setTitle("Test");
    XYChart.Series<Number, Number> seriesData = ArraysToSeries(xNumber, data);
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
