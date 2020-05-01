package com.ariso.simlib.model;

import java.util.ArrayList;

import com.ariso.simlib.EventHandlerInterface;

/**
 * This class represents a system dynamics simulation and controls the
 * simulation execution.
 * 
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Simulation {

	private Model model;
	private ArrayList<EventHandlerInterface> eventHandlerList;

	/**
	 * Constructor.
	 *
	 * @param model the model object that describes the system dynamic model.
	 */
	public Simulation(Model model) {
		this.model = model;
		this.eventHandlerList = new ArrayList<>();
	}

	/**
	 * This method runs the simulation.
	 */
	public void run() {
		this.prepareInitialValues();

		this.prepareValuesForFirstTimestep();

		this.fireSimulationInitializedEvent(this.model);

		this.executeConverters();

		this.fireTimeStepCalculatedEvent(this.model);

		while (this.finalTimeReached()) {
			this.updateCurrentTime();
			this.prepareValuesForTimestep();
			this.model.getIntegration().integrate();
			this.executeConverters();
			System.out.println(this.model);
			this.fireTimeStepCalculatedEvent(model);
		}

		this.fireSimulationFinishedEvent(model);
	}

	/**
	 * Prepare all initial model values for running the simulation.
	 */
	private void prepareInitialValues() {
		this.model.setCurrentStep(this.model.getInitialTime());
		this.model.getModelEntities().forEach((k, v) -> {
			v.setCurrentValue(v.getInitialValue());
			v.setCurrentValueCalculated(false);
		});
		this.model.getIntegration().setNodeList(model.getNodesList());
		this.model.getIntegration().setVariableConverter(model.getConverterList());
		this.model.getIntegration().setDt(this.model.getTimeSteps());
	}

	/**
	 * Method to prepare all Stocks whose current value is already calculated for
	 * the first timestep.
	 */
	private void prepareValuesForFirstTimestep() {
		this.model.getModelEntities().forEach((k, v) -> {
			if (v instanceof Nodes && this.model.getCurrentStep() == this.model.getInitialTime()) {
				v.setCurrentValueCalculated(true);
			}
		});

	}

	/**
	 * Method to prepare all values for the next timestep.
	 */
	private void prepareValuesForTimestep() {
		this.model.getModelEntities().forEach((k, v) -> {
			v.setPreviousValue(v.theValue());
			v.setCurrentValueCalculated(false);
		});
	}

	/**
	 * Method to update the current time by adding one time step.
	 */
	private void updateCurrentTime() {
		this.model.setCurrentStep(this.model.getCurrentStep() + this.model.getTimeSteps());
	}

	/**
	 * Method that controls if the final time has been reached.
	 *
	 * @return <tt>true</tt> only if the final time has been reached.
	 */
	public boolean finalTimeReached() {
		return this.model.getCurrentStep() < this.model.getTotalStep();
	}

	/**
	 * Method to execute the converters.
	 */
	private void executeConverters() {
		for (Converter converter : this.model.getConverterList()) {
			if (converter != null && !converter.getTargetEntity().isCurrentValueCalculated()) {
				converter.convert();
			}
		}

	}

	/**
	 * Adds an listener that handles simulation events.
	 *
	 * @param listener {@link EventHandlerInterface}
	 */
	public void addSimulationEventListener(EventHandlerInterface listener) {
		this.eventHandlerList.add(listener);
	}

	/**
	 * Removes a {@link EventHandlerInterface}.
	 *
	 * @param listener {@link EventHandlerInterface}
	 */
	public void removeSimulationEventListener(EventHandlerInterface listener) {
		this.eventHandlerList.remove(listener);
	}

	/**
	 * Fires an event for the initialization of the simulation.
	 *
	 * @param model {@link Model} for the simulation
	 */
	private void fireSimulationInitializedEvent(Model model) {
		this.eventHandlerList.forEach(listener -> listener.open(model));
	}

	/**
	 * Fires an event for a finished calculation of a time step.
	 *
	 * @param model {@link Model} for the simulation
	 */
	private void fireTimeStepCalculatedEvent(Model model) {
		this.eventHandlerList.forEach(listener -> listener.execStep(model));
	}

	/**
	 * Fires an event for a finished simulation.
	 *
	 * @param model {@link Model} for the simulation
	 */
	private void fireSimulationFinishedEvent(Model model) {
		this.eventHandlerList.forEach(listener -> listener.close(model));
	}

}
