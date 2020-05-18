package com.ariso.sdsim.app1;

/**
 *  this class put all app level config
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class AppConfig  {
 
	public final static int HistoryLen = 1000;
	
	public final static String Wolf = "Wolf";
	public final static String Grass = "Grass";
	public final static String Sheep = "Sheep";
	
	// 一只狼要吃多少羊
	public static double sheepEatByWolf = 10;    
	//每只羊吃多少草 5
	public static double eatGrassPerSheep = 10;
	//每個草能有多少新草
	public static double newGrassRate = 10 ;	
	//羊繁殖率 
	public static double newSheepRate = 2;
	//狼繁殖率
	public static double newWolfRate = 1;
	
	//最多有多少草
	public static double maxGrassLimit = 100000;
	 
	public static double minSheep = 30;
	public static double minGrass = 3000;
	public static double minWolf = 2;
}
