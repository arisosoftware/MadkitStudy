package com.ariso.simlib.model;

import com.ariso.simlib.AbstractModelEntity;

/**
 * This class represents a variable. If a variable ha no converter it represents
 * an input variable that won't be affected by other entities.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Variable extends AbstractModelEntity {

	/**
	 * Constructor.
	 *
	 * @param name the name of the variable.
	 */
	public Variable(String name) {
		super(name);
	}
 

}
