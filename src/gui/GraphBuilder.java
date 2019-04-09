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

public class GraphBuilder extends Application {

public static void main(String[] args) {
   launch(args);
}

@Override
public void start(Stage stage) throws Exception {
   stage.setTitle("JavaFX Chart Demo");
   StackPane pane = new StackPane();
   pane.getChildren().add(createLineChart());
   stage.setScene(new Scene(pane, 400, 200));
   stage.show();
}

public ObservableList<XYChart.Series<String, Double>>
      getDummyChartData() {
	ObservableList<XYChart.Series<String, Double>> data =
	         FXCollections.observableArrayList();
	      Series<String, Double> as = new Series<>();
	      Series<String, Double> bs = new Series<>();
	      Series<String, Double> cs = new Series<>();
	      Series<String, Double> ds = new Series<>();
	      Series<String, Double> es = new Series<>();
	      Series<String, Double> fs = new Series<>();
	      as.setName("A-Series");
	      bs.setName("B-Series");
	      cs.setName("C-Series");
	      ds.setName("D-Series");
	      es.setName("E-Series");
	      fs.setName("F-Series");

	      Random r = new Random();

	      for (int i = 1900; i < 2017; i += 10) {

	         as.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	         bs.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	         cs.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	         ds.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	         es.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	         fs.getData().add(new XYChart.Data<>
	         (Integer.toString(i), r.nextDouble()));
	      }
	      data.addAll(as, bs, cs, ds, es, fs);
	      return data;
	   }

public XYChart<CategoryAxis, NumberAxis> createLineChart() {
   CategoryAxis xAxis = new CategoryAxis();
   NumberAxis yAxis = new NumberAxis();
   LineChart lc = new LineChart<>(xAxis, yAxis);
   lc.setData(getDummyChartData());
   lc.setTitle("Line chart on random data");
   return lc;
}
}