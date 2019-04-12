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

//public static void main(String[] args) {
//   launch(args);
//}

//@Override
//public void start(Stage stage) throws Exception {
//	stage.setTitle("Chart");
//   StackPane pane = new StackPane();
//   pane.getChildren().add(createLineChart());
//   stage.setScene(new Scene(pane, 400, 200));
//   stage.show();
//}

private Series<Double, Double> ArraysToSeries(int xNumber, Double[][] inputdata){
	Series<Double, Double> chartdata = new Series<Double, Double>();
	for (int i=0; i<=inputdata[0].length; i++) {
	chartdata.getData().add(new XYChart.Data(inputdata[xNumber][i], inputdata[0][i]));
	}
	return chartdata;
}



public XYChart<CategoryAxis, NumberAxis> createLineChart(int xNumber, Double[][] inputdata) {
   NumberAxis xAxis = new NumberAxis();
   NumberAxis yAxis = new NumberAxis();
   LineChart lc = new LineChart<>(xAxis, yAxis);
   ObservableList<XYChart.Series<Double, Double>> data =FXCollections.observableArrayList();
   Series<Double, Double> chartData = ArraysToSeries(xNumber, inputdata);
   data.add(chartData);
   lc.setData(data);
   lc.setTitle("Line chart on random data");
   return lc;
}
}