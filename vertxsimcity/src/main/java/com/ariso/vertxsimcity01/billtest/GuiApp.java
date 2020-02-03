//https://stackoverflow.com/questions/53573747/dynamic-bar-chart-in-java-fx

package com.ariso.vertxsimcity01.billtest;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GuiApp extends Application {
	final static String austria = "Austria";
	final static String brazil = "Brazil";
	final static String france = "France";
	final static String italy = "Italy";
	final static String usa = "USA";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {
		stage.setTitle("Bar Chart Sample");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("RMB player");
		xAxis.setLabel("Country");
		yAxis.setLabel("Value");
		Random rnd = new Random();
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("2003");
		ArrayList<XYChart.Data<Integer, Integer>> bardata = new ArrayList<XYChart.Data<Integer, Integer>>();
		for (int i = 0; i < 100; i++) {
			series1.getData().add(new XYChart.Data(("100" + i).substring(2), rnd.nextInt(100)));
		}

		Scene scene = new Scene(bc, 800, 600);
		bc.getData().addAll(series1);
		stage.setScene(scene);
		stage.show();
		

		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
