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

public class Sample3 {
	//
	private Model model;

	private Nodes normal;
	private Nodes infected;
	private Nodes hospital;
	private Nodes died;

	private Flow newHospital;
	private Flow newDead;
	private Flow newFixed;

	private Flow newInfected;
	private Flow newSelfCure;

//	private Variable infectRate;
//	private Variable hospitalRate;
//	private Variable icuRate;
//	private Variable deadRate;

	public static void main(String[] args) throws Exception {
		Sample3 sp = new Sample3();

		sp.letsgo();

	}

	public void letsgo() {
		model = new Model(1, 12, 1, new EulerCauchyIntegration());
		try {
//			population = (Nodes) model.createModelEntity(ModelEntityType.NODE, POPULATION_KEY);
//			birthRate = (Variable) model.createModelEntity(ModelEntityType.VARIABLE, BIRTH_RATE_KEY);
//			births = (Flow) model.createModelEntity(ModelEntityType.FLOW, BIRTHS_KEY);
//			deaths = (Flow) model.createModelEntity(ModelEntityType.FLOW, DEATHS_KEY);
//			population.addInputFlows(births);
//			population.addOutputFlows(deaths);
//			population.setInitialValue(5000);
//			population.setChangeRateFunction(() -> births.getCurrentValue() - deaths.getCurrentValue());
//
//			deaths.setInitialValue(100);
//			birthRate.setInitialValue(0.1);
			// ==================================================
			normal = (Nodes) model.createModelEntity(ModelEntityType.NODE, "常人");
			infected = (Nodes) model.createModelEntity(ModelEntityType.NODE, "潜伏");
			hospital = (Nodes) model.createModelEntity(ModelEntityType.NODE, "住院");
			died = (Nodes) model.createModelEntity(ModelEntityType.NODE, "死亡");
			// ==================================================
			newDead = (Flow) model.createModelEntity(ModelEntityType.FLOW, "病危");
			newFixed = (Flow) model.createModelEntity(ModelEntityType.FLOW, "治愈");
			newHospital = (Flow) model.createModelEntity(ModelEntityType.FLOW, "重症");
			newInfected = (Flow) model.createModelEntity(ModelEntityType.FLOW, "新阳");
			newSelfCure = (Flow) model.createModelEntity(ModelEntityType.FLOW, "自愈");

			// ================================================== data flow
			model.flowMoveTo(normal, infected, newInfected);

			model.flowMoveTo(infected, normal, newSelfCure);
			model.flowMoveTo(infected, hospital, newHospital);

			model.flowMoveTo(hospital, normal, newFixed);
			model.flowMoveTo(hospital, died, newDead);
			// ================================================== nodes data count calc
//			normal.setChangeRateFunction(() ->  {
//			
//				newRecovered.theValue() + newCure.theValue() - newDead.theValue() - newInfected.theValue()
//				return 0;
//			}
//					);
//			
			
			normal.setChangeRateFunction(new ComputeInterface() {
				@Override
				public double calculateEntityValue() {
					double result = newFixed.theValue() * newSelfCure.theValue() - newDead.theValue() - newInfected.theValue();
					return result;
				}
			});
			
			
			normal.setInitialValue(50000);
			

			infected.setChangeRateFunction(() -> newInfected.theValue() - newHospital.theValue() - newSelfCure.theValue());
			infected.setInitialValue(100);

			hospital.setChangeRateFunction(() -> hospital.theValue() + newHospital.theValue() - newFixed.theValue() - newDead.theValue());
			hospital.setInitialValue(0);

			died.setChangeRateFunction(() -> died.theValue() + newDead.theValue());
			died.setInitialValue(0);
			// ================================================== Covert/rate
			// --正常人 转 新阳 = 假设 1个潜伏会导致2000个潜伏 每天20天 1：1 (target, inputs ...)
			model.createConverter(newInfected, infected,normal).setFunction(() -> {
				double newInfectedValue = (infected.theValue()/ model.getCurrentTime()) * 1.0;
				
				if (newInfectedValue> normal.theValue())
				{
					newInfectedValue = normal.theValue();
				}
				
				return newInfectedValue;
			});
			// --潜伏 转 自愈 = 假设 100个潜伏 85个自愈 0.85 每天10天
			model.createConverter(newSelfCure, infected).setFunction(() -> infected.theValue() * ( (85 / 100.0) / 10.0));
			// --潜伏 转 重症 = 假设 100个潜伏 85个自愈 0.15 每天10天
			model.createConverter(newHospital, infected).setFunction(() -> infected.theValue() * ((15 / 100.0) / 10.0));
			// --重症 转 康复 = 假设 100个重症 50个康复 0.5 每天20天
			model.createConverter(newFixed, hospital).setFunction(() -> hospital.theValue() * ((50 / 100.0) / 20.0));
			// --重症 转 病危 = 假设 100个重症 50个病危 0.5 每天20天
			model.createConverter(newDead, hospital).setFunction(() -> hospital.theValue() * ((50 / 100.0) / 20.0));

			Simulation simulation = new Simulation(model);
			model.setFinalTime(20);
			simulation.run();

			model.reportConsole();

		} catch (SimException e) {
			e.printStackTrace();
		}
	}

}
