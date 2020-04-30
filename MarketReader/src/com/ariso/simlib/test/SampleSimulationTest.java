package com.ariso.simlib.test;

import org.junit.Assert;
import org.junit.Test;

import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.SimException;
import com.ariso.simlib.func.EulerCauchyIntegration;
import com.ariso.simlib.model.*;
import com.ariso.simlib.report.ChartPlotter;
import com.ariso.simlib.report.ChartViewer;
import com.ariso.simlib.report.ChartViewerApplication;
import com.ariso.simlib.report.CsvExporter;

import java.util.HashMap;

/**
 * Class to test the simulation.
 *
 * @author <a href="mailto:matthias.stein@hs-bochum.de">Matthias Stein</a>
 */
public class SampleSimulationTest {

	private final String POPULATION_PREY_KEY = "Population_Prey";
	private final String BIRTHS_PREY_KEY = "Births_Prey";
	private final String DEATHS_PREY_KEY = "Deaths_Prey";
	private final String BIRTH_RATE_PREY_KEY = "BirthRate_Prey";
	private final String DEATH_RATE_PREY_KEY = "DeathRate_Prey";

	private final String POPULATION_PREDATOR_KEY = "Population_Predator";
	private final String BIRTHS_PREDATOR_KEY = "Births_Predator";
	private final String DEATHS_PREDATOR_KEY = "Deaths_Predator";
	private final String BIRTH_RATE_PREDATOR_KEY = "BirthRate_Predator";
	private final String DEATH_RATE_PREDATOR_KEY = "DeathRate_Predator";

	private final String MEETINGS_KEY = "Meetings";

	/**
	 * Method to run the sample simulation test.
	 */
	@Test
	public void simulationRunTest() {

		// Create a model with the parameters:
		Model model = new Model(0, 10, 0.1, new EulerCauchyIntegration());
		try {
			// prey
			// Create prey population as Nodes
			Nodes populationPrey = (Nodes) model.createModelEntity(ModelEntityType.NODE, POPULATION_PREY_KEY);
			populationPrey.setInitialValue(100);
			// Create prey births and deaths as flows
			Flow birthsPrey = (Flow) model.createModelEntity(ModelEntityType.FLOW, BIRTHS_PREY_KEY);
			Flow deathsPrey = (Flow) model.createModelEntity(ModelEntityType.FLOW, DEATHS_PREY_KEY);
			// Add flows to prey population
			populationPrey.addInputFlows(birthsPrey);
			populationPrey.addOutputFlows(deathsPrey);
			// Create prey birthrate and deathrate as variable
			Variable expansionRatePrey = (Variable) model.createModelEntity(ModelEntityType.VARIABLE,
					BIRTH_RATE_PREY_KEY);
			expansionRatePrey.setInitialValue(0.1);
			Variable lossRatePrey = (Variable) model.createModelEntity(ModelEntityType.VARIABLE, DEATH_RATE_PREY_KEY);
			lossRatePrey.setInitialValue(0.01);

			// predator
			// Create predator population as Nodes
			Nodes populationPredator = (Nodes) model.createModelEntity(ModelEntityType.NODE, POPULATION_PREDATOR_KEY);
			populationPredator.setInitialValue(1);
			// Create prey births and deaths as flows
			Flow birthsPredator = (Flow) model.createModelEntity(ModelEntityType.FLOW, BIRTHS_PREDATOR_KEY);
			Flow deathsPredator = (Flow) model.createModelEntity(ModelEntityType.FLOW, DEATHS_PREDATOR_KEY);
			// Add flows to predator population
			populationPredator.addInputFlows(birthsPredator);
			populationPredator.addOutputFlows(deathsPredator);
			// Create prey birthrate and deathrate as variable
			Variable expansionRatePredator = (Variable) model.createModelEntity(ModelEntityType.VARIABLE,
					BIRTH_RATE_PREDATOR_KEY);
			expansionRatePredator.setInitialValue(0.01);
			Variable lossRatePredator = (Variable) model.createModelEntity(ModelEntityType.VARIABLE,
					DEATH_RATE_PREDATOR_KEY);
			lossRatePredator.setInitialValue(0.1);

			// Create meetings as variable
			Variable meetings = (Variable) model.createModelEntity(ModelEntityType.VARIABLE, MEETINGS_KEY);

			// Create converters
			Converter deathsPreyConverter = model.createConverter(deathsPrey, meetings, lossRatePrey);
			deathsPreyConverter.setFunction(() -> meetings.theValue() * lossRatePrey.theValue());

			Converter birthsPreyConverter = model.createConverter(birthsPrey, populationPrey, expansionRatePrey);
			birthsPreyConverter
					.setFunction(() -> populationPrey.theValue() * expansionRatePrey.theValue());

			Converter deathsPredatorConverter = model.createConverter(deathsPredator, populationPredator,
					lossRatePredator);
			deathsPredatorConverter
					.setFunction(() -> populationPredator.theValue() * lossRatePredator.theValue());

			// Approach for converting entity values by implementing IFunction
			// with an inner class
			Converter meetingsConverter = model.createConverter(meetings, populationPrey, populationPredator);
			meetingsConverter
					.setFunction(() -> populationPrey.theValue() * populationPredator.theValue());

			Converter birthsPredatorConverter = model.createConverter(birthsPredator, meetings, expansionRatePredator);
			birthsPredatorConverter
					.setFunction(() -> meetings.theValue() * expansionRatePredator.theValue());

			populationPrey.setChangeRateFunction(() -> birthsPrey.theValue() - deathsPrey.theValue());
			populationPredator
					.setChangeRateFunction(() -> birthsPredator.theValue() - deathsPredator.theValue());

		} catch (SimException e) {
			e.printStackTrace();
		}

		double error = 0.001;

		Simulation simulation = new Simulation(model);
		simulation.addSimulationEventListener(new CsvExporter("output.csv", ";"));
		simulation.addSimulationEventListener(new ChartViewer());
		// simulation.addSimulationEventListener(new ChartPlotter());

		model.setFinalTime(10);
		simulation.run();

		HashMap<String, AbstractModelEntity> entities = model.getModelEntities();
//
//        Assert.assertThat(entities.get(POPULATION_PREY_KEY).getCurrentValue(), Matchers.closeTo(0.2826, error));
//        Assert.assertThat(entities.get(BIRTH_RATE_PREY_KEY).getCurrentValue(), Matchers.equalTo(0.1));
//        Assert.assertThat(entities.get(DEATH_RATE_PREY_KEY).getCurrentValue(), Matchers.equalTo(0.01));
//        Assert.assertThat(entities.get(BIRTHS_PREY_KEY).getCurrentValue(), Matchers.closeTo(0.0283, error));
//        Assert.assertThat(entities.get(DEATHS_PREY_KEY).getCurrentValue(), Matchers.closeTo(0.2608, error));
//
//        Assert.assertThat(entities.get(POPULATION_PREDATOR_KEY).getCurrentValue(), Matchers.closeTo(92.2736, error));
//        Assert.assertThat(entities.get(BIRTH_RATE_PREDATOR_KEY).getCurrentValue(), Matchers.equalTo(0.01));
//        Assert.assertThat(entities.get(DEATH_RATE_PREDATOR_KEY).getCurrentValue(), Matchers.equalTo(0.1));
//        Assert.assertThat(entities.get(BIRTHS_PREDATOR_KEY).getCurrentValue(), Matchers.closeTo(0.2608, error));
//        Assert.assertThat(entities.get(DEATHS_PREDATOR_KEY).getCurrentValue(), Matchers.closeTo(9.2274, error));
//
//        Assert.assertThat(entities.get(MEETINGS_KEY).getCurrentValue(), Matchers.closeTo(26.0779, error));
	}

}
