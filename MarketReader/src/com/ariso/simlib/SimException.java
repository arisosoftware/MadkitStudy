package com.ariso.simlib;

/**
 * This class extents the Exception class and offers static strings to describe
 * model exceptions.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class SimException extends Exception {

	private static final long serialVersionUID = -6888340651361373959L;

	public final static String DUPLICATE_MODEL_ENTITY_EXCEPTION = "Duplicate model entity.";

	public final static String DUPLICATE_FLOW_EXCEPTION = "Duplicate flow exception.";

	public final static String DUPLICATE_VARIABLE_EXCEPTION = "Duplicate variable exception.";

	public SimException(String message) {
		super(message);
	}
}
