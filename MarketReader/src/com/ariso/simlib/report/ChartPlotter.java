package com.ariso.simlib.report;

import com.ariso.simlib.EventHandler;
import com.ariso.simlib.model.Model;

/**
 * Class that implements the {@link EventHandler} interface and controls the
 * chart plotting.
 * 
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class ChartPlotter implements EventHandler {
	@Override
	public void open(Model model) {
		ChartPlotterApplication.addSeries(model.getModelEntitiesKeys());
	}

	@Override
	public void execStep(Model model) {
		ChartPlotterApplication.addValues(model.getModelEntitiesValues(), model.getCurrentTime());
	}

	@Override
	public void close(Model model) {
		ChartViewerApplication.launch(ChartViewerApplication.class);
	}
}
