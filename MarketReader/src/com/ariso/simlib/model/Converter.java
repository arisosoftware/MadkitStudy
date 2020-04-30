package com.ariso.simlib.model;

import java.util.ArrayList;
import java.util.Collections;

import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.ComputeInterface;
import com.ariso.simlib.SimException;

/**
 * This class represents a converter that calculates the current value of a
 * target {@link AbstractModelEntity} in a time step. It represents the
 * cause-effect relationships between {@link AbstractModelEntity} instances. The
 * calculation depends on the function that has been delivered to the converter.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Converter {

	private ComputeInterface function;

	private AbstractModelEntity targetEntity;
	ArrayList<AbstractModelEntity> inputs;
	private Double minLimitValue;
	private Double maxLimitValue;

	/**
	 * Constructor.
	 *
	 * @param entity target model entity.
	 * @param inputs input model entities.
	 */
	protected Converter(AbstractModelEntity entity, AbstractModelEntity... inputs) {
		this.targetEntity = entity;
		this.inputs = new ArrayList<AbstractModelEntity>();
		Collections.addAll(this.inputs, inputs);
	}

	/**
	 * Adds a function for calculating the current value of the target ModelEntity.
	 *
	 * @param function function for the target ModelEntity's current value.
	 */
	public void setFunction(ComputeInterface function) {
		this.function = function;
	}

	/**
	 * @return function for the target ModelEntity's current value.
	 */
	public ComputeInterface getFunction() {
		return function;
	}

	/**
	 * Method to convert the target model entity value.
	 */
	public void convert() {
		for (AbstractModelEntity input : this.inputs) {
			if (!input.isCurrentValueCalculated() && input.getConverter() != null) {
				input.getConverter().convert();
			}
		}
		double calculatedValue = function.calculateEntityValue();

		// Check if there are any limits for the value that has to be converted.
		if (minLimitValue != null && minLimitValue < calculatedValue) {
			this.targetEntity.setCurrentValue(this.minLimitValue);
		} else if (maxLimitValue != null && calculatedValue > maxLimitValue) {
			this.targetEntity.setCurrentValue(maxLimitValue);
		} else {
			this.targetEntity.setCurrentValue(calculatedValue);
		}
		this.targetEntity.setCurrentValueCalculated(true);

	}

	/**
	 * Method to add multiple input model entities.
	 *
	 * @param inputs model entities.
	 * @throws SimException model exception.
	 */
	protected void addInputs(AbstractModelEntity... inputs) throws SimException {
		for (AbstractModelEntity f : inputs) {
			this.addInput(f);
		}
	}

	/**
	 * Method to add an input for the target entity.
	 *
	 * @param input input entity.
	 * @throws SimException model exception.
	 */
	private void addInput(AbstractModelEntity input) throws SimException {
		if (inputAlreadyAdded(input)) {
			throw new SimException(SimException.DUPLICATE_VARIABLE_EXCEPTION);
		} else {
			this.inputs.add(input);
		}
	}

	/**
	 * Method to determine if a ModelEntity has been already added to the Converter.
	 *
	 * @param variable ModelEntity to check.
	 * @return true if the ModelEntity already exists.
	 */
	private boolean inputAlreadyAdded(AbstractModelEntity variable) {
		return inputs.contains(variable);
	}

	/**
	 * @return target entity.
	 */
	public AbstractModelEntity getTargetEntity() {
		return this.targetEntity;
	}

	/**
	 * @return minimum limit that will be calculated by the converter.
	 */
	public double getMinLimitValue() {
		return minLimitValue;
	}

	/**
	 * @param minLimitValue minimum limit to set.
	 */
	public void setMinLimitValue(double minLimitValue) {
		this.minLimitValue = minLimitValue;
	}

	/**
	 * @return maximum limit that will be calculated by the converter.
	 */
	public double getMaxLimitValue() {
		return maxLimitValue;
	}

	/**
	 * @param maxLimitValue maximum limit to set.
	 */
	public void setMaxLimitValue(double maxLimitValue) {
		this.maxLimitValue = maxLimitValue;
	}

}
