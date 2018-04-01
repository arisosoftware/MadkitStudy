package com.ariso.zhihu.challenge;

import java.util.Random;

public class BoyOrGirlProbability {

	public static void main(String[] args) {

		BoyOrGirlProbability test1 = new BoyOrGirlProbability();
		BoyOrGirlProbability test2 = new BoyOrGirlProbability();

		for (int i = 0; i < 100000; i++) {
			test1.Test1();
			test2.Test2();
		}
		test1.PrintResult("R1");
		test2.PrintResult("R2");
	
	}

	Random rnd = new Random();

	public static final byte Girl = 0;
	public static final byte Boy = 1;
	public static float BoyOrGirlProbaility = 0.5f;

	public byte BoyOrGirl() {
		if (rnd.nextFloat() < BoyOrGirlProbaility)
			return Girl;
		else
			return Boy;
	}

	public void Test1() {
		if (BoyOrGirl() == Boy) {
			SumOfBoy++;
		} else {
			SumOfGirl++;		 
		}

	}
	
	public void Test2() {
		if (BoyOrGirl() == Boy) {
			SumOfBoy++;
		} else {
			SumOfGirl++;
			// rule #2, if first baby is girl then will try next baby 
			if (BoyOrGirl() == Boy) {
				SumOfBoy++;
			} else {
				SumOfGirl++;
			}
		}

	}

	public void PrintResult(String Prefix)
	{
		float total = SumOfBoy+SumOfGirl;
		float GirlsRate = SumOfGirl/ total;
		float BoysRate = SumOfBoy/ total;
		
		System.out.printf("%s Result: Girl = %d Boy = %d Rate: %.2f:%.2f  (Total %f)\n"
				 ,Prefix , SumOfBoy, SumOfGirl  
				, GirlsRate,BoysRate,total
			 );
	}
	
	
	public long SumOfGirl = 0;
	public long SumOfBoy = 0;

}
