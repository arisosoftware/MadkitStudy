package com.ariso.sdsim.app1;

import com.ariso.sdsim.app1.model.GrassFlow;
import com.ariso.sdsim.app1.model.SheepFlow;
import com.ariso.sdsim.app1.model.WolfFlow;
import com.ariso.sdsim.lib.Entity;
import com.ariso.sdsim.lib.Model;

public class Sample001 {

	public static void main(String[] args) throws Exception {
		 Model model = new Model();
		 Entity grass = new Entity(10, AppConfig.Grass);
		 grass.flow.add(new GrassFlow());		 
		 model.addEntity(grass);
		 
		 Entity sheep = new Entity(10, AppConfig.Sheep);
		 sheep.flow.add(new SheepFlow());		 
		 model.addEntity(sheep);
		 
		 Entity wolf = new Entity(10, AppConfig.Wolf);
		 wolf.flow.add(new WolfFlow());		 
		 model.addEntity(wolf);
		  
		 grass.addStepValue(100);
		 sheep.addStepValue(10);
		 wolf.addStepValue(2);
		 
		 model.currentStep=0;
		 model.totalSteps = 50;
		 model.run();
		 
		 
	}


 
	 
	
	
}



//	 AbstractFlow flow = new AbstractFlow();


// sheep, grass 
// for each time （one tick means 1 year)
// each sheep need 10 grass unit per year
// sheep total 10, so the array sheep 1,2...10
// sheep [x] => sheep[x+1]
// sheep will die on 10 years
// each sheep which have food on age 3~7 will have new 2 sheep baby
// each grass will be 4 times then before. until reach limit (10000)
// if total sheep not have enough grass, 
//     a) age <3 and age>7 will no have food and put in die list until food go enough.

