package com.ariso.simlib.app;

import java.util.HashMap;
import java.util.TreeMap;

import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.ComputeInterface;
import com.ariso.simlib.SimException;
import com.ariso.simlib.func.EulerCauchyIntegration;
import com.ariso.simlib.model.Converter;
import com.ariso.simlib.model.Flow;
import com.ariso.simlib.model.Model;
import com.ariso.simlib.model.ModelEntityType;
import com.ariso.simlib.model.Nodes;
import com.ariso.simlib.model.Simulation;
import com.ariso.simlib.model.Variable;

public class Sample01 {
	private final String POPULATION_KEY = "人口";
	private final String BIRTHS_KEY = "新生";
	private final String BIRTH_RATE_KEY = "出生率";
	private final String DEATHS_KEY = "死亡";

	private Model model;
	Nodes population;
	Variable birthRate;
	Flow births;
	Flow deaths;

	public static void main(String[] args) throws Exception {
		Sample01 sp01 = new Sample01();

		sp01.prepareValues();
		sp01.algebraicFunctionTest();
		// sp01.logicFunctionTest();
		// sp01.lookUpTableTest();

	}

	public void prepareValues() {
		model = new Model(1, 12, 1, new EulerCauchyIntegration());
		try {
			population = (Nodes) model.createModelEntity(ModelEntityType.NODE, POPULATION_KEY);
			birthRate = (Variable) model.createModelEntity(ModelEntityType.VARIABLE, BIRTH_RATE_KEY);
			births = (Flow) model.createModelEntity(ModelEntityType.FLOW, BIRTHS_KEY);
			deaths = (Flow) model.createModelEntity(ModelEntityType.FLOW, DEATHS_KEY);
			population.addInputFlows(births);
			population.addOutputFlows(deaths);
			population.setInitialValue(5000);
			population.setChangeRateFunction(() -> births.theValue() - deaths.theValue());

			deaths.setInitialValue(100);
			birthRate.setInitialValue(0.1);

		} catch (SimException e) {
			e.printStackTrace();
		}
	}

	public void algebraicFunctionTest() {
		Converter birthsConverter = model.createConverter(births, birthRate, population);
		birthsConverter.setFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				double result = birthRate.theValue() * population.theValue();
				return result;
			}
		});
		Simulation simulation = new Simulation(model);
		model.setFinalTime(20);
		simulation.run();

		model.reportConsole();

	}

	public void logicFunctionTest() {
		Converter birthsConverter = model.createConverter(births, birthRate, population);
		birthsConverter.setFunction(() -> {
			if (population.theValue() >= 10000)
				return 0;
			else {
				double result = birthRate.theValue() * population.theValue();
				return result;
			}
		});
		Simulation simulation = new Simulation(model);
		simulation.run();
		HashMap<String, AbstractModelEntity> entities = model.getModelEntities();

		model.reportConsole();

	}

	public void lookUpTableTest() {
		Converter birthsConverter = model.createConverter(births, birthRate, population);
		birthsConverter.setFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				double result = birthRate.theValue() * population.theValue();
				return result;
			}
		});
		Converter birthsRateConverter = model.createConverter(birthRate);
		TreeMap<Integer, Double> lookupTable = new TreeMap<Integer, Double>();
		lookupTable.put(1, 0.1);
		lookupTable.put(2, 0.15);
		lookupTable.put(3, 0.2);
		lookupTable.put(4, 0.25);
		lookupTable.put(5, 0.3);
		lookupTable.put(6, 0.35);
		lookupTable.put(7, 0.4);
		lookupTable.put(8, 0.45);
		lookupTable.put(9, 0.4);
		lookupTable.put(10, 0.35);
		lookupTable.put(11, 0.3);
		lookupTable.put(12, 0.25);

		birthsRateConverter.setFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				double currentTime = model.getCurrentTime();
				double floorKey = lookupTable.floorKey((int) currentTime);
				double floor = lookupTable.floorEntry((int) currentTime).getValue();
				double ceilingKey = lookupTable.ceilingKey((int) currentTime);
				double ceiling = lookupTable.ceilingEntry((int) currentTime).getValue();
				if (floorKey - ceilingKey < 0.000000001)
					return floor;
				else {
					double result = floor + (ceiling - floor) / (ceilingKey - floorKey) * (currentTime - ceilingKey);
					return result;
				}
			}
		});
		Simulation simulation = new Simulation(model);
		simulation.run();

	}
}
