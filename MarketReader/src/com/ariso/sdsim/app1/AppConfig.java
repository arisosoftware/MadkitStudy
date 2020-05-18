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
	public static double newGrassRate = 2 ;	
	//羊繁殖率 
	public static double newSheepRate = 1.5;
	//狼繁殖率
	public static double newWolfRate = 0.2;
	
	//最多有多少草
	public static double maxGrassLimit = 1000000;
	public static double maxSheepLimit = 1000000;
	public static double maxWolfLimit = 5000;
	
	public static double minSheep = 20;
	public static double minGrass = 6000;
	public static double minWolf = 2;
	

	public static double initSheep = 20;
	public static double initGrass = 6000;
	public static double initWolf = 2;
	
	
	
	
}
