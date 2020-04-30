package com.ariso.simlib.model;

import com.ariso.simlib.AbstractModelEntity;

/**
 * This class represents a flow. A flow can either be an input flow or an output
 * flow for a {@link Nodes}. It defines the changing rate of a {@link Nodes}.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Flow extends AbstractModelEntity {

	/**
	 * Constructor.
	 *
	 * @param name flow name.
	 */
	public Flow(String name) {
		super(name);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Flow flow = new Flow(this.getName());
		flow.setCurrentValue(this.getCurrentValue());
		flow.setInitialValue(getInitialValue());
		flow.setPreviousValue(this.getPreviousValue());
		return flow;
	}

}
