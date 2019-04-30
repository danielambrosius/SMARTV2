package gui;

//https://www.developer.com/java/data/working-with-javafx-chart-apis.html
//credits to: Manoj Debnath

import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;



public class GraphBuilder{
	
	private Series<Number, Number> seriesData;
	
	public GraphBuilder(Double[] xData, Double[] yData, String title) {
		this.seriesData = ArraysToSeries(xData, yData);
		this.seriesData.setName(title);
	}

	
public static XYChart.Series<Number, Number> ArraysToSeries(Double[] xData, Double[] yData){
	XYChart.Series<Number, Number> chartdata = new XYChart.Series<Number, Number>();
	for (int i=0; i < yData.length; i++) {
		chartdata.getData().add(new XYChart.Data<Number, Number>(xData[i], yData[i]));
	}
	return chartdata;
}


public LineChart<Number, Number> start() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
   
	lineChart.setAxisSortingPolicy(SortingPolicy.NONE);
    
    lineChart.getData().add(seriesData);
    lineChart.setTitle(seriesData.getName());
    
    return lineChart;

}
}
