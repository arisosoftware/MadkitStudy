package com.ariso.sdsim.lib;

import java.util.ArrayList;

public  class Entity {
	public String label;	
	public int steps=0;
	public double minValue =0;
	public double maxValue =1000;
	public CycleList valueHistory ;
	public ArrayList<AbstractFlow> flow;
 	
	public Entity(int historyLen,String aLabel)
	{
		valueHistory = new CycleList(historyLen);
		flow = new ArrayList<AbstractFlow>();
		label = aLabel;
	}
	
	public void addStepValue(double val)
	{
		valueHistory.enQueue(val);		
	}
	
	public double GetValue()
	{
		return valueHistory.getRear();
	}
	
	public double GetValue(int deep)
	{
		return valueHistory.getDeep(deep);
	} 


	public void applyFlowValues()
	{
		double delta = 0;
	 
		for(int i=0;i<this.flow.size();i++)
		{
			AbstractFlow flow = this.flow.get(i);			
			delta = delta + flow.value;		 
		}
		 
		double value = this.GetValue() + delta;
		
		if (value<minValue)
		{
			value = minValue;
		}
		if (value>maxValue)
		{
			value = maxValue;
		}
		//System.out.println(this.label + " " + value);
		
		this.addStepValue(value);
	}
} 