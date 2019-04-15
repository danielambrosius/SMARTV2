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


public class GraphBuilder extends Application{


public static XYChart.Series<Number, Number> ArraysToSeries(int xNumber, Double[][] inputdata){
	XYChart.Series<Number, Number> chartdata = new XYChart.Series<Number, Number>();
	for (int i=0; i < inputdata.length; i++) {
		chartdata.getData().add(new XYChart.Data<Number, Number>(inputdata[i][0], inputdata[i][xNumber]));
	}
	return chartdata;
}


public static void main(String[] args) {
    launch(args);
}

@Override
public void start(Stage primaryStage) {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    //TEST
    Double[][] testData = {{0.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {2.0, 2.0, 2.0}, {3.0, 3.0, 6.0}, {4.0, 4.0, 12.0}, {5.0, 5.0, 20.0}, {6.0, 6.0, 30.0},
			{7.0, 7.0, 42.0}, {8.0, 8.0, 56.0}, {9.0, 9.0, 72.0}, {10.0, 10.0, 90.0}};

    lineChart.setTitle("Test");
    XYChart.Series<Number, Number> seriesData = ArraysToSeries(2, testData);
    seriesData.setName("testSeries");
    
    Scene scene = new Scene(lineChart, 700, 800);
    lineChart.getData().add(seriesData);
    lineChart.setTitle("Chart");
    primaryStage.setTitle("LineChart example");

    StackPane root = new StackPane();
    root.getChildren().add(lineChart);
    primaryStage.setScene(new Scene(root, 400, 250));
    primaryStage.show();
}
}
