package com.ariso.simlib;

import com.ariso.simlib.model.Model;
import com.ariso.simlib.model.Simulation;

/**
 * Event Handler
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public interface OnNextInterface {
 
	/**
	 * This is will be called when system running
	 * 
	 * @param model useless in this example
	 */
	void oneStep(Model model);
 

}
