package com.ariso.simlib;

import com.ariso.simlib.model.Converter;

/**
 * Abstract class that represents an entity of the simulation model.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public abstract class AbstractModelEntity {

	private String name;
	private double currentValue;
	private double previousValue;
	private double initialValue;
	private boolean currentValueCalculated;
	private Converter converter;

	
	public double[] valueHistory = null;
	public int lastValuePoint =0;
	
	/**
	 * Constructor.
	 *
	 * @param name model entity name.
	 */
	protected AbstractModelEntity(String name) {
		super();
		this.name = name;
		this.valueHistory = new double[10000];
	}

	/**
	 * @return model entity name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name name to set.
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return current model entity value.
	 */
	public double theValue() {
		return this.currentValue;
	}

	/**
	 * @param currentValue current value to set.
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * @return previous model entity value.
	 */
	public double getPreviousValue() {
		return this.previousValue;
	}

	/**
	 * @param previousValue previous value to set.
	 */
	public void setPreviousValue(double previousValue) {
		this.previousValue = previousValue;
	}

	/**
	 * @param converter converter to set.
	 */
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	/**
	 * @return converter.
	 */
	public Converter getConverter() {
		return this.converter;
	}

	/**
	 * @param b true or false.
	 */
	public void setCurrentValueCalculated(boolean b) {
		this.currentValueCalculated = b;
	}

	/**
	 * @return true if value has already been calculated.
	 */
	public boolean isCurrentValueCalculated() {
		return this.currentValueCalculated;
	}

	/**
	 * @return intitial value.
	 */
	public double getInitialValue() {
		return initialValue;
	}

	/**
	 * @param initialValue initial value to set.
	 */
	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "AbstractModelEntity{" + "name=" + this.name + ", value=" + this.currentValue + ", previousValue="
				+ this.previousValue + '}';
	}

}
