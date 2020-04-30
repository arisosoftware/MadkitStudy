package com.ariso.simlib;

import java.util.List;

import com.ariso.simlib.model.Converter;
import com.ariso.simlib.model.Nodes;

/**
 * Abstract class that describes an integration method.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public abstract class AbstractIntegration {

	private double dt;
	private List<Nodes> nodeList;
	private List<Converter> variableConverter;

	protected List<Nodes> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Nodes> stocks) {
		this.nodeList = stocks;
	}

	protected List<Converter> getVariableConverter() {
		return variableConverter;
	}

	public void setVariableConverter(List<Converter> variableConverter) {
		this.variableConverter = variableConverter;
	}

	protected double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	/**
	 * integrate interface method
	 **/
	public abstract void integrate();

}
