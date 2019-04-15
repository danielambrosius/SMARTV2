package gui;

//https://www.developer.com/java/data/working-with-javafx-chart-apis.html
//credits to: Manoj Debnath

import java.util.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class GraphBuilder{

public static void main(String[] args) {
   launch(args);
}

private static void launch(String[] args) {
	createLineChart(int xNumber, Double[][] inputdata)
}

//@Override
public static void start(Stage stage) throws Exception {
   stage.setTitle("Chart");
   StackPane pane = new StackPane();
   pane.getChildren().add(createLineChart(1, null));
   stage.setScene(new Scene(pane, 400, 200));
   stage.show();
}

public static Series<Double, Double> ArraysToSeries(int xNumber, Double[][] inputdata){
	Series<Double, Double> chartdata = new Series<Double, Double>();
	for (int i=0; i < inputdata.length; i++) {
		chartdata.getData().add(new XYChart.Data<Double,Double>(inputdata[i][xNumber], inputdata[i][0]));
	}
	return chartdata;
}



public static XYChart<CategoryAxis, NumberAxis> createLineChart(int xNumber, Double[][] inputdata) {
   LineChart lc = new LineChart<>(new NumberAxis(), new NumberAxis());   
   Series<Double, Double> chartData = ArraysToSeries(xNumber, inputdata);
   lc.getData().add(chartData);
   lc.setTitle("Line chart on random data");
   Stage stage = new Stage();
   stage.setTitle("Second Chart");
   stage.setScene(new Scene(lc, 500, 400));
   stage.show();
   return lc;
}
}