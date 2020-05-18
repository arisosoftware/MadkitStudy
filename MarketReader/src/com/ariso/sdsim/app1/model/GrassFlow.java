package com.ariso.sdsim.app1.model;

import com.ariso.sdsim.lib.AbstractFlow;
import com.ariso.sdsim.lib.Entity;
import com.ariso.sdsim.app1.*;

public class GrassFlow extends AbstractFlow {
	
	public GrassFlow()
	{
		this.label = "GrassFlow";//this.getClass().getName();
	}
	
	@Override
	public void compute() {
		Entity grass = this.model.getEntity("Grass");
		Entity sheep = this.model.getEntity(AppConfig.Sheep);
		
		double newGrass = grass.GetValue() * AppConfig.newGrassRate;
		
		double eatBySheep = sheep.GetValue() * AppConfig.eatGrassPerSheep ;
		
		this.value = newGrass - eatBySheep;
		 
	}
	
}
