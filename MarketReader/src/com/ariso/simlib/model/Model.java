package com.ariso.simlib.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.List;

import com.ariso.simlib.AbstractIntegration;
import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.AppConfig;
import com.ariso.simlib.func.EulerCauchyIntegration;

/**
 * This class represents a simulation model. It defines all {@link AbstractModelEntity} instances and their cause-effect relationships.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Model {

	private HashMap<String, AbstractModelEntity> modelMap;
	private ArrayList<Converter> converterList;
	private double initStep;
	private double totalStep;
	private double timeSteps;
	private double currentStep;
	private AbstractIntegration integration;

	public Model() {
		this.modelMap = new HashMap<String, AbstractModelEntity>();
		this.converterList = new ArrayList<Converter>();
		this.initStep = 0;
		this.currentStep = this.initStep;
		this.totalStep = 100;
		this.timeSteps = 1;
		this.integration = new EulerCauchyIntegration();
	}

	public Model(double initialTime, double finalTime, double timeSteps, AbstractIntegration integration) {
		this.modelMap = new HashMap<String, AbstractModelEntity>();
		this.converterList = new ArrayList<Converter>();
		this.initStep = initialTime;
		this.currentStep = initialTime;
		this.totalStep = finalTime;
		this.timeSteps = timeSteps;
		this.integration = integration;
	}

	public AbstractModelEntity createModelEntity(ModelEntityType entityType, String name) throws Exception {
		AbstractModelEntity modelEntity;
		switch (entityType) {
		case NODE:
			modelEntity = new Nodes(name);
			break;
		case FLOW:
			modelEntity = new Flow(name);
			break;
		case VARIABLE:
			modelEntity = new Variable(name);
			break;
		default:
			return null;
		}
		this.addModelEntity(modelEntity);
		return modelEntity;
	}

	public void addModelEntity(AbstractModelEntity modelEntity) throws Exception {
		if (!existsModelEntity(modelEntity)) {
			this.modelMap.put(modelEntity.getName(), modelEntity);
		} else {
			throw new Exception(AppConfig.DUPLICATE_MODEL_ENTITY_EXCEPTION);
		}
	}

	private boolean existsModelEntity(AbstractModelEntity modelEntity) {
		return this.modelMap.containsKey(modelEntity.getName());
	}

	public List<Nodes> getNodesList() {
		ArrayList<Nodes> stocks = new ArrayList<Nodes>();
		ArrayList<AbstractModelEntity> modelEntities = new ArrayList<AbstractModelEntity>(this.modelMap.values());
		modelEntities.forEach((modelEntity) -> {
			if (modelEntity instanceof Nodes) {
				stocks.add((Nodes) modelEntity);
			}

		});
		return stocks;
	}

	public Converter createConverter(AbstractModelEntity entity, AbstractModelEntity... inputs) {
		Converter converter = new Converter(entity, inputs);
		this.addConverter(converter);
		entity.setConverter(converter);
		return converter;
	}

	public void flowMoveTo(Nodes source, Nodes target, Flow flow) throws Exception {
		target.addInputFlows(flow);
		source.addOutputFlows(flow);
	}

	private void addConverter(Converter converter) {
		this.converterList.add(converter);
	}

	public HashMap<String, AbstractModelEntity> getModelEntities() {
		return this.modelMap;
	}

	public double getInitialTime() {
		return this.initStep;
	}

	public void setInitialTime(double initialTime) {
		this.initStep = initialTime;
	}


	public double getTotalStep() {
		return totalStep;
	}

	public void setTotalStep(double finalTime) {
		this.totalStep = finalTime;
	}

	public double getTimeSteps() {
		return timeSteps;
	}

	public void setTimeSteps(double timeSteps) {
		this.timeSteps = timeSteps;
	}

	public double getCurrentStep() {
		return this.currentStep;
	}

	protected void setCurrentStep(double currentTime) {
		this.currentStep = currentTime;
	}

	public ArrayList<Converter> getConverterList() {
		return this.converterList;
	}

	public List<String> getModelEntitiesKeys() {
		return new ArrayList<String>(this.modelMap.keySet());
	}

	public List<String> getModelEntitiesValues() {
		ArrayList<AbstractModelEntity> modelEntities = new ArrayList<AbstractModelEntity>(this.modelMap.values());
		ArrayList<String> modelEntitiesValues = new ArrayList<String>();
		modelEntities.forEach((modelEntity) -> {
			modelEntitiesValues.add(new DecimalFormat("#.######").format(modelEntity.theValue()));
		});
		return modelEntitiesValues;
	}

	public AbstractIntegration getIntegration() {
		return integration;
	}

	public void setIntegration(AbstractIntegration integration) {
		this.integration = integration;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb.append( new DecimalFormat("000").format(this.currentStep) );
		this.modelMap.values().forEach((modelEntity) -> {
					
			if (modelEntity instanceof Nodes)
			{
				if (((Nodes) modelEntity).ifRequiredReport)
				{
					sb.append("\t" + modelEntity.getName() + ":" + new DecimalFormat("#####.000").format(modelEntity.theValue()));	
				}					
			}
			else
			{
				sb2.append("\t" + modelEntity.getName() + ":" + new DecimalFormat("#####.000").format(modelEntity.theValue()));
			}
			
			// sb.append("\n");
		});
		return sb.toString()+"\t"+sb2.toString();
		// return "Model [modelEntities=" + this.modelEntities + "]";
	}

	public void reportConsole() {
		System.out.println("reportConsole");
		this.modelMap.values().forEach((modelEntity) -> {

			if (modelEntity instanceof Nodes) {
				Nodes theNode = (Nodes) modelEntity;
				System.out.println(theNode.getName() + ":" + new DecimalFormat("#.######").format(theNode.theValue()));
			}

		});
	}
}
