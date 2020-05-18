package com.ariso.sdsim.app1.model;

import com.ariso.sdsim.app1.AppConfig;
import com.ariso.sdsim.lib.*;

public class SheepFlow extends AbstractFlow {
	public SheepFlow()
	{
		this.label = "SheepFlow";//this.getClass().getName();
	}
	@Override
	public void compute() {
	 
		 
				
		Entity grass = this.model.getEntity(AppConfig.Grass);
		Entity sheep = this.model.getEntity(AppConfig.Sheep);
		Entity wolf = this.model.getEntity(AppConfig.Wolf);

		double newSheep =  0;

		double maxSheepLimitByGrass = (grass.GetValue() - AppConfig.minGrass) / AppConfig.eatGrassPerSheep;
		
		if (maxSheepLimitByGrass < AppConfig.minSheep)
		{
			maxSheepLimitByGrass = AppConfig.minSheep;
		}
		
		double eatByWolf = wolf.GetValue() * AppConfig.sheepEatByWolf;
		
		double noFoodSheep =   sheep.GetValue() - maxSheepLimitByGrass ;
	 	
		if (noFoodSheep < 0)
		{		
			newSheep = sheep.GetValue() * AppConfig.newSheepRate;
			noFoodSheep = 0;		
		}
			 
		this.value = newSheep - eatByWolf - noFoodSheep;
		 
		
	}

}
