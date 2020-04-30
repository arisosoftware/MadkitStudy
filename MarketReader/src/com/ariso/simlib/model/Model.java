package com.ariso.simlib.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.List;

import com.ariso.simlib.AbstractIntegration;
import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.SimException;
import com.ariso.simlib.func.EulerCauchyIntegration;

/**
 * This class represents a simulation model. It defines all
 * {@link AbstractModelEntity} instances and their cause-effect relationships.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Model {

	private HashMap<String, AbstractModelEntity> modelEntities;
	private ArrayList<Converter> converterList;
	private double initialTime;
	private double finalTime;
	private double timeSteps;
	private double currentTime;
	private AbstractIntegration integration;

	/**
	 * Constructor.
	 */
	public Model() {
		this.modelEntities = new HashMap<String, AbstractModelEntity>();
		this.converterList = new ArrayList<Converter>();
		this.initialTime = 0;
		this.currentTime = this.initialTime;
		this.finalTime = 100;
		this.timeSteps = 1;
		this.integration = new EulerCauchyIntegration();
	}

	/**
	 * Constructor.
	 *
	 * @param initialTime initial time.
	 * @param finalTime   final time.
	 * @param timeSteps   length of a time step.
	 */
	public Model(double initialTime, double finalTime, double timeSteps, AbstractIntegration integration) {
		this.modelEntities = new HashMap<String, AbstractModelEntity>();
		this.converterList = new ArrayList<Converter>();
		this.initialTime = initialTime;
		this.currentTime = initialTime;
		this.finalTime = finalTime;
		this.timeSteps = timeSteps;
		this.integration = integration;
	}

	/**
	 * Method to create a new model entity.
	 *
	 * @param entityType entity type.
	 * @param name       entity name.
	 * @return the created model entity.
	 * @throws SimException model exception.
	 */
	public AbstractModelEntity createModelEntity(ModelEntityType entityType, String name) throws SimException {
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

	/**
	 * Method to add model entity to model.
	 *
	 * @param modelEntity model entity to add.
	 * @throws SimException model exception.
	 */
	private void addModelEntity(AbstractModelEntity modelEntity) throws SimException {
		if (!existsModelEntity(modelEntity)) {
			this.modelEntities.put(modelEntity.getName(), modelEntity);
		} else {
			throw new SimException(SimException.DUPLICATE_MODEL_ENTITY_EXCEPTION);
		}
	}

	/**
	 * Method to control whether model already contains a model entity or not.
	 *
	 * @param modelEntity model entity.
	 * @return true if model already exists.
	 */
	private boolean existsModelEntity(AbstractModelEntity modelEntity) {
		return this.modelEntities.containsKey(modelEntity.getName());
	}

	/**
	 * @return list of stocks.
	 */
	public List<Nodes> getNodesList() {
		ArrayList<Nodes> stocks = new ArrayList<Nodes>();
		ArrayList<AbstractModelEntity> modelEntities = new ArrayList<AbstractModelEntity>(this.modelEntities.values());
		modelEntities.forEach((modelEntity) -> {
			if (modelEntity instanceof Nodes) {
				stocks.add((Nodes) modelEntity);
			}

		});
		return stocks;
	}

	/**
	 * Method to create a new converter.
	 *
	 * @param entity model entity.
	 * @param inputs input model entities.
	 * @return the created converter.
	 */
	public Converter createConverter(AbstractModelEntity entity, AbstractModelEntity... inputs) {
		Converter converter = new Converter(entity, inputs);
		this.addConverter(converter);
		entity.setConverter(converter);
		return converter;
	}

	public void flowMoveTo(Nodes source, Nodes target, Flow flow) throws SimException {
		target.addInputFlows(flow);
		source.addOutputFlows(flow);
	}

	/**
	 * @param converter converter to add.
	 */
	private void addConverter(Converter converter) {
		this.converterList.add(converter);
	}

	/**
	 * @return model entities.
	 */
	public HashMap<String, AbstractModelEntity> getModelEntities() {
		return this.modelEntities;
	}

	/**
	 * @return initial time.
	 */
	public double getInitialTime() {
		return this.initialTime;
	}

	/**
	 * @param initialTime initial time to set.
	 */
	public void setInitialTime(double initialTime) {
		this.initialTime = initialTime;
	}

	/**
	 * @return final time.
	 */
	public double getFinalTime() {
		return finalTime;
	}

	/**
	 * @param finalTime final time to set.
	 */
	public void setFinalTime(double finalTime) {
		this.finalTime = finalTime;
	}

	/**
	 * @return time step length.
	 */
	public double getTimeSteps() {
		return timeSteps;
	}

	/**
	 * @param timeSteps time step length to set.
	 */
	public void setTimeSteps(double timeSteps) {
		this.timeSteps = timeSteps;
	}

	/**
	 * @return current time.
	 */
	public double getCurrentTime() {
		return this.currentTime;
	}

	/**
	 * @param currentTime current time to set.
	 */
	protected void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * @return list of converters.
	 */
	public ArrayList<Converter> getConverterList() {
		return this.converterList;
	}

	/**
	 * @return list of model entities keys.
	 */
	public List<String> getModelEntitiesKeys() {
		return new ArrayList<String>(this.modelEntities.keySet());
	}

	/**
	 * @return list of model entity values.
	 */
	public List<String> getModelEntitiesValues() {
		ArrayList<AbstractModelEntity> modelEntities = new ArrayList<AbstractModelEntity>(this.modelEntities.values());
		ArrayList<String> modelEntitiesValues = new ArrayList<String>();
		modelEntities.forEach((modelEntity) -> {
			modelEntitiesValues.add(new DecimalFormat("#.######").format(modelEntity.theValue()));
		});
		return modelEntitiesValues;
	}

	/**
	 * @return integration.
	 */
	public AbstractIntegration getIntegration() {
		return integration;
	}

	/**
	 * @param integration integration to set.
	 */
	public void setIntegration(AbstractIntegration integration) {
		this.integration = integration;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb.append( new DecimalFormat("000").format(this.currentTime) );
		this.modelEntities.values().forEach((modelEntity) -> {
					
			if (modelEntity instanceof Nodes)
			{
				sb.append("\t" + modelEntity.getName() + ":" + new DecimalFormat("#####.000").format(modelEntity.theValue()));	
			}
			else
			{
				sb2.append("\t" + modelEntity.getName() + ":" + new DecimalFormat("#####.000").format(modelEntity.theValue()));
			}
			
			// sb.append("\n");
		});
		return sb.toString()+"\t\t\t"+sb2.toString();
		// return "Model [modelEntities=" + this.modelEntities + "]";
	}

	/**
	 * @param integration integration to set.
	 */
	public void reportConsole() {
		System.out.println("reportConsole");
		this.modelEntities.values().forEach((modelEntity) -> {

			if (modelEntity instanceof Nodes) {
				Nodes theNode = (Nodes) modelEntity;
				System.out.println(theNode.getName() + ":" + new DecimalFormat("#.######").format(theNode.theValue()));
			}

		});

	}
}
