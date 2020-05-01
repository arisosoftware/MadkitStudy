package com.ariso.simlib;

import com.ariso.simlib.model.Model;
import com.ariso.simlib.model.Simulation;

/**
 * Event Handler
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public interface EventHandlerInterface {

	/**
	 * This is the first activated behavior in the life cycle, put init code in here
	 * 
	 * @param model useless in this example
	 */
	void open(Model model);

	/**
	 * This is will be called when system running
	 * 
	 * @param model useless in this example
	 */
	void execStep(Model model);

	/**
	 * This will be called when system closed
	 * 
	 * @param model useless in this example
	 */
	void close(Model model);

}
