package com.ariso.sdsim.lib;

import java.util.ArrayList;
import java.util.HashMap;
import com.ariso.simlib.AbstractModelEntity;

/**
 * This class represents a flow. A flow can either be an input flow or an output
 * flow for a {@link Nodes}. It defines the changing rate of a {@link Nodes}.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public abstract class AbstractFlow  {

	public String label;
	public double value;	 
	public Model model;
	/**
	 * compute interface method, input is model/entitys and update is value;
	 **/
	public abstract void compute();

}
 