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

public class Sample2 {
	//
	private Model model;
	
	private Nodes confirmed;
	private Nodes normalPPL;
	
	private Nodes confirmedAtHospital;
	private Nodes confirmedAtICU;
	private Nodes confirmedAtHome;
	private Nodes notAwareInfected;  
	private Flow newInfected;
	
	private Nodes dead;
	private Nodes recovered;

//	private Variable infectRate;
//	private Variable hospitalRate;
//	private Variable icuRate;
//	private Variable deadRate;

	public static void main(String[] args) throws Exception {
		Sample2 sp = new Sample2();

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
			confirmed = (Nodes) model.createModelEntity(ModelEntityType.NODE, "确诊");
			
			normalPPL = (Nodes) model.createModelEntity(ModelEntityType.NODE, "常人");
			
			confirmedAtHome = (Nodes) model.createModelEntity(ModelEntityType.NODE, "隔离");
			confirmedAtHospital = (Nodes) model.createModelEntity(ModelEntityType.NODE, "住院");
			confirmedAtICU = (Nodes) model.createModelEntity(ModelEntityType.NODE, "ICU");
			notAwareInfected = (Nodes) model.createModelEntity(ModelEntityType.NODE, "潜伏");
			
			dead = (Nodes) model.createModelEntity(ModelEntityType.NODE, "死亡");
			recovered  = (Nodes) model.createModelEntity(ModelEntityType.NODE, "康复");
			
			

			// -- 正常 转 潜伏 = 假设 1个确诊会导致20个潜伏 每天 20_00%，1个确诊20天要么住院要么康复，因此最多200个潜伏
			// -- 潜伏 转 正常 = 假设 100个 有95 个转正常 每天 4.75% 需要20天 全部转化完毕
			// -- 潜伏 转 确诊 = 假设 100个 有5 个转确诊 每天 0.25% 需要20天 全部转化完毕
			// -- 确诊 转 住院 = 假设 100个 有20 个转住院 每天 2% 需要10天,因此除10
			// -- 确诊 转 正常 = 假设 100个 有20 个转住院 每天 8% 需要10天,因此除10
			// -- 住院 转 正常 = 假设 100个 有 80 个转正常 每天 5% 需要15天治疗
			// -- 住院 转 ICU = 假设 1000个 有1 个转住院 每天 2% 需要15天治疗
			// -- ICU 转 死亡 = 假设 20%， 每天 1% 需要20天治疗
			// -- ICU 转 正常 = 假设 80%， 每天 4% 需要20天治疗


			// --潜伏 转 潜伏  = 假设 1个潜伏会导致20个潜伏 每天 20_00%，1个确诊20天要么住院要么康复，因此最多200个潜伏
			model.createConverter(notAwareInfected, notAwareInfected).setFunction(new ComputeInterface() {
				@Override
				public double calculateEntityValue() {
					double result = notAwareInfected.theValue() * 20;
					return result;
				}
			});
			
			// -- 潜伏 转 隔离 = 假设 1个潜伏会导致20个隔离 每天 20_00%，1个确诊20天要么住院要么康复，因此最多200个潜伏
			model.createConverter(confirmedAtHome, notAwareInfected).setFunction(new ComputeInterface() {
				@Override
				public double calculateEntityValue() {
					double result = notAwareInfected.theValue() * 20;
					return result;
				}
			});
			// -- 隔离 转 住院 = 假设 100个 有5 个转确诊 每天 0.25% 需要20天 全部转化完毕
			model.createConverter(confirmedAtHospital, confirmedAtHome).setFunction(new ComputeInterface() {
				@Override
				public double calculateEntityValue() {
					double result = confirmedAtHome.theValue() * 0.02;
					return result;
				}
			});

			
			
			
			
			
			
			
			
			
			
			
			Simulation simulation = new Simulation(model);
			model.setFinalTime(20);
			simulation.run();

			model.reportConsole();

		} catch (SimException e) {
			e.printStackTrace();
		}
	}

}
