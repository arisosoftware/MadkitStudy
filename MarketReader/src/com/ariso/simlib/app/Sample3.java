package com.ariso.simlib.app;

import java.util.HashMap;
import java.util.TreeMap;

import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.ComputeInterface;
import com.ariso.simlib.AppConfig;
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
	private Nodes totalInfected;

	private Flow newHospital;
	private Flow newDead;
	private Flow newFixed;

	private Flow newInfected;
	private Flow newSelfCure;
	private Variable infectRate;
//	private Variable infectRate;
//	private Variable hospitalRate;
//	private Variable icuRate;
//	private Variable deadRate;

	public static void main(String[] args) throws Exception {
		Sample3 sp = new Sample3();

		sp.letsgo();

	}

	public void letsgo() throws Exception {
		model = new Model(1, 12, 1, new EulerCauchyIntegration());

		// ==================================================
		normal = (Nodes) model.createModelEntity(ModelEntityType.NODE, "常人");
		normal.ifRequiredReport = false;
		infected = (Nodes) model.createModelEntity(ModelEntityType.NODE, "轻症数");
		hospital = (Nodes) model.createModelEntity(ModelEntityType.NODE, "住院数");
		died = (Nodes) model.createModelEntity(ModelEntityType.NODE, "死亡数");

		totalInfected = (Nodes) model.createModelEntity(ModelEntityType.NODE, "确诊数");
		// ==================================================
		newDead = (Flow) model.createModelEntity(ModelEntityType.FLOW, "不治");
		newFixed = (Flow) model.createModelEntity(ModelEntityType.FLOW, "治愈");
		newHospital = (Flow) model.createModelEntity(ModelEntityType.FLOW, "重症");
		newInfected = (Flow) model.createModelEntity(ModelEntityType.FLOW, "新症");
		newSelfCure = (Flow) model.createModelEntity(ModelEntityType.FLOW, "自愈");
		//
		infectRate = (Variable) model.createModelEntity(ModelEntityType.VARIABLE, "传播率");

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
//			Since the server is dedicated to running SQL

		normal.setChangeRateFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				double result = newFixed.theValue() + newSelfCure.theValue() - newDead.theValue() - newInfected.theValue();
				return result;
			}
		});

		infected.setChangeRateFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				double result = newInfected.theValue() - newHospital.theValue() - newSelfCure.theValue();

//					System.out.print(" newInfected: "+newInfected.theValue());
//					System.out.print(" newHospital: "+newHospital.theValue());
//					System.out.print(" newSelfCure: "+newSelfCure.theValue());
//					System.out.print(" result: "+result);
				return result;
			}
		});
		// (() -> newInfected.theValue() - newHospital.theValue() -
		// newSelfCure.theValue());

		totalInfected.setChangeRateFunction(() -> newInfected.theValue());
		hospital.setChangeRateFunction(() -> newHospital.theValue() - newFixed.theValue() - newDead.theValue());

		died.setChangeRateFunction(() -> newDead.theValue());

		// ================================================== Covert/rate
		// --正常人 转 新阳 = 假设 1个潜伏会导致2000个潜伏 每天20天 1：1 (target, inputs ...)
		model.createConverter(newInfected, infected, normal).setFunction(() -> {

			double avglen = 15;
			if (model.getCurrentStep() < avglen) {
				avglen = model.getCurrentStep();
			}
			// System.out.println("infected: "+infected.theValue());
			double newInfectedValue = infected.theValue() * 0.29;
			// (infected.theValue() / avglen) * 1;

			if (newInfectedValue > normal.theValue()) {
				newInfectedValue = normal.theValue();
			}

			return newInfectedValue;
		});

		// --潜伏 转 自愈 = 假设 100个潜伏 85个自愈 0.85 每天10天
		// --潜伏 转 重症 = 假设 100个潜伏 85个自愈 0.15 每天10天
		model.createConverter(newSelfCure, infected, hospital).setFunction(() -> {
			// infected.theValue() * ( (85 / 100.0) / 10.0));
			double avglen = 7;
			if (model.getCurrentStep() < avglen) {
				avglen = model.getCurrentStep();
			}
			// double newInfectedValue = (infected.theValue() / avglen) * 0.9;

			double newInfectedValue = (infected.theValue() + newSelfCure.theValue()) * 0.22;
			// Math.pow(infected.theValue(), 1.02)*0.22;

			if (newInfectedValue > normal.theValue()) {
				newInfectedValue = normal.theValue();
			}

			return newInfectedValue;
		});

//			model.createConverter(newHospital, infected).setFunction(() -> infected.theValue() * ((15 / 100.0) / 10.0));

		model.createConverter(newHospital, infected).setFunction(() -> {
			// infected.theValue() * ( (85 / 100.0) / 10.0));
			double avglen = 12;
			if (model.getCurrentStep() < avglen) {
				avglen = model.getCurrentStep();
			}
			double newInfectedValue = (infected.theValue() / avglen) * 0.3;

			if (newInfectedValue > normal.theValue()) {
				newInfectedValue = normal.theValue();
			}

			return newInfectedValue;
		});

		// --重症 转 康复 = 假设 100个重症 50个康复 0.5 每天20天
		// --重症 转 病危 = 假设 100个重症 50个病危 0.5 每天20天
		model.createConverter(newFixed, hospital).setFunction(() -> hospital.theValue() * ((190 / 100.0) / 20.0));
		model.createConverter(newDead, hospital).setFunction(() -> hospital.theValue() * ((70.1 / 100.0) / 20.0));

		// ================================================== Covert/rate
		Converter infectRateConvert = model.createConverter(newInfected, infectRate, infected, normal);
		infectRateConvert.setFunction(new ComputeInterface() {
			@Override
			public double calculateEntityValue() {
				// double result = birthRate.theValue() * population.theValue();

				return 1;
			}
		});

		normal.setInitialValue(1000000);

		// 14天前数据： 8961 - 4194 - 423 - 807 =3537
		// 潜伏， 自愈， 死亡 ，重症
		// 8961 - 4194 - 423 - 807 =3537 Apr 15
		// 10010 - 4875 - 514 - 828 =3793 April 17, 2020
		infected.setInitialValue(3537);
		hospital.setInitialValue(807);
		died.setInitialValue(423);

		// 3793 - 3537 =256 潜伏
		// 514 - 423 = 91 死亡
		// 828 - 807 = 21 ，重症
		// 4194-4875 681 自愈，

		newDead.setInitialValue(91); // "病危");
		newFixed.setInitialValue(550); // "治愈");
		newHospital.setInitialValue(807); // "重症");
		newInfected.setInitialValue(560); // "新阳");
		newSelfCure.setInitialValue(128); // "自愈");
		totalInfected.setInitialValue(8961);
		// 今天数据
//			infected.setInitialValue(3901);
//			hospital.setInitialValue(999);
//			died.setInitialValue(1082);
//			// 16187 - 10205 - 1082 - 999 =3901 April 30, 2020

		/*
		 * Numbers above were last updated on April 30, 2020, at 10:30 a.m. Status of
		 * cases
		 * 
		 * Total number of cases 16187 Resolved 10205 Deaths 1082 Hospitalized 999
		 * 
		 */

		Simulation simulation = new Simulation(model);
		model.setTotalStep(20);
		simulation.run();

		// model.reportConsole();

	}
//	
//   static void Define001(){
//		Converter birthsConverter = model.createConverter(births, birthRate, population);
//		birthsConverter.setFunction(() -> {
//			if (population.theValue() >= 10000)
//				return 0;
//			else {
//				double result = birthRate.theValue() * population.theValue();
//				return result;
//			}
//		});
//   }

}
