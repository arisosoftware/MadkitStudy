package com.ariso.zhihu.challenge;

import java.util.ArrayList;
import java.util.Random;

public class BoyOrGirlProbability {

	public static void main(String[] args) {

		BoyOrGirlProbability test1 = new BoyOrGirlProbability("Test case 1");
		BoyOrGirlProbability test2 = new BoyOrGirlProbability("Test case 2");
		BoyOrGirlProbability test3 = new BoyOrGirlProbability("Test case 3");

		for (int i = 0; i < 100000; i++) {
			test1.Test1();

			test2.Test2();
		}
		test1.PrintResult();
		test2.PrintResult();

		// added year condtion,
		/*
		 * 每年有10000对家庭生小孩。 生男生女概率各 50％ 如果第一胎是女孩可以再生一胎。 最多2个小孩。
		 * 
		 * 每个家庭一年之内生一个。
		 * 
		 * 求这个政策执行后每年的男女比例，和社会男女的比例。
		 */
		int testyear = 20;
		int sample = 10000;
		ArrayList<BoyOrGirlProbability> History = new ArrayList<BoyOrGirlProbability>(testyear);
		ArrayList<BoyOrGirlProbability> History2nd = new ArrayList<BoyOrGirlProbability>(testyear);

		long additionSample = 0;

		for (int theyear = 0; theyear < testyear; theyear++) {

			BoyOrGirlProbability thisYearProbaility = new BoyOrGirlProbability("Year " + (theyear + 1));

			History.add(thisYearProbaility);
			for (int x = 0; x < sample; x++) {
				thisYearProbaility.Test1();
			}
 
			BoyOrGirlProbability thisYear2ndProbaility = new BoyOrGirlProbability("Year 2nd " + (theyear + 1));

			History2nd.add(thisYear2ndProbaility);

			for (int x = 0; x < additionSample; x++) {
				thisYear2ndProbaility.Test1();
			}

			// additionSample
			additionSample = thisYearProbaility.SumOfGirl;
		}

		BoyOrGirlProbability summary = new BoyOrGirlProbability("Summary");
		for (int theyear = 0; theyear < testyear; theyear++) {
			History.get(theyear).PrintResult();
			History2nd.get(theyear).PrintResult();
			summary.SumOfBoy += History.get(theyear).SumOfBoy + History2nd.get(theyear).SumOfBoy;
			summary.SumOfGirl += History.get(theyear).SumOfGirl + History2nd.get(theyear).SumOfGirl;
			
		}
		
		summary.PrintResult();
	}

	public BoyOrGirlProbability(String testname) {
		Prefix = testname;
	}

	String Prefix;
	Random rnd = new Random();

	public static final byte Girl = 0;
	public static final byte Boy = 1;

	// 生男生女的概率都是50%
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

	public void PrintResult() {
		float total = SumOfBoy + SumOfGirl;
		float GirlsRate = SumOfGirl / total;
		float BoysRate = SumOfBoy / total;

		System.out.printf("%s Result: Girl = %d Boy = %d Rate: %.2f:%.2f  (Total %f)\n", Prefix, SumOfBoy, SumOfGirl,
				GirlsRate, BoysRate, total);
	}

	public int SumOfGirl = 0;
	public int SumOfBoy = 0;

}
