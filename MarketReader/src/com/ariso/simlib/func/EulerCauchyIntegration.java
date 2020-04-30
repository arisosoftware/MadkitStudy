package com.ariso.simlib.func;

import com.ariso.simlib.AbstractIntegration;
import com.ariso.simlib.model.Nodes;

/**
 * This class extends the {@link AbstractIntegration} interface and represents
 * the Euler-Cauchy method.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class EulerCauchyIntegration extends AbstractIntegration {

	@Override
	public void integrate() {
		for (Nodes stock : this.getNodeList()) {
			double calculatedValue = stock.theValue()
					+ stock.getChangeRateFunction().calculateEntityValue() * this.getDt();
			stock.setCurrentValue(calculatedValue);
			stock.setCurrentValueCalculated(true);
		}
	}

}
