package com.ariso.sdsim.app1.model;

import com.ariso.sdsim.lib.AbstractFlow;
import com.ariso.sdsim.lib.Entity;
import com.ariso.sdsim.app1.*;

public class WolfFlow extends AbstractFlow {
	public WolfFlow()
	{
		this.label = "WolfFlow";// this.getClass().getName();
	}
	@Override
	public void compute() {
		Entity sheep = this.model.getEntity(AppConfig.Sheep);
		Entity wolf = this.model.getEntity(AppConfig.Wolf);

		double newWolf = 0;

		double maxWolfLimit = (sheep.GetValue() - AppConfig.minSheep) / AppConfig.sheepEatByWolf;
		
		if (maxWolfLimit < AppConfig.minWolf)
		{
			maxWolfLimit = AppConfig.minWolf;
		}
		 
		double noFoodWolf =  wolf.GetValue() - maxWolfLimit ;
			
		if (noFoodWolf < 0)
		{
			noFoodWolf =0;
			newWolf = Math.round(wolf.GetValue() * AppConfig.newWolfRate);
		}
	 
		this.value = newWolf - noFoodWolf;
	}
	
}
