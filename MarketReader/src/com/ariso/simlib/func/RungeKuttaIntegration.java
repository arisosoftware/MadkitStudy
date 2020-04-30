package com.ariso.simlib.func;

import com.ariso.simlib.AbstractIntegration;
import com.ariso.simlib.model.Converter;
import com.ariso.simlib.model.Nodes;

/**
 *
 * 
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class RungeKuttaIntegration extends AbstractIntegration {

	@Override
	public void integrate() {
		double[][] k = new double[this.getNodeList().size()][4];
		for (int i = 0; i <= 3; i++) {
			int j = 0;
			for (Nodes node : this.getNodeList()) {
				k[j][i] = node.getChangeRateFunction().calculateEntityValue() * this.getDt();
				if (i < 2) {
					node.setCurrentValue(node.getPreviousValue() + k[j][i] / 2);
				}

				else if (i == 2) {
					node.setCurrentValue(node.getPreviousValue() + k[j][i]);
				}

				else {
					double calculatedValue = node.getPreviousValue() + k[j][0] / 6 + k[j][1] / 3 + k[j][2] / 3
							+ k[j][3] / 6;
					node.setCurrentValue(calculatedValue);
				}
				node.setCurrentValueCalculated(true);
				j++;
			}
			for (Converter converter : this.getVariableConverter()) {
				converter.convert();
			}
			this.prepareValuesForStep();
		}
	}

	/**
	 * Method to prepare all values for the next step.
	 */
	private void prepareValuesForStep() {
		for (Converter converter : this.getVariableConverter()) {
			converter.getTargetEntity().setCurrentValueCalculated(false);
		}
	}

}
