package com.ariso.playground.util;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;

public class StringAndStringBuffer {
	static Random rnd = new Random();

	@Benchmark
	public String StringAddTest() throws Throwable {

		String Result = "AAA" + rnd.nextInt(1001) + "." + rnd.nextInt(1002) + "." + rnd.nextInt(1003) + "."
				+ rnd.nextInt(1004) + "." + rnd.nextInt(1005) + "." + rnd.nextInt(1006) + "." + rnd.nextInt(1007) + "."
				+ rnd.nextInt(1008) + "." + rnd.nextInt(1009) + "." + rnd.nextInt(1010) + "." + rnd.nextInt(1011);
		return Result;
	}

	@Benchmark
	public String StringBuffAddTest() throws Throwable {

		StringBuilder sb = new StringBuilder();
		sb.append("AAA");
		sb.append('.');
		sb.append(rnd.nextInt(1001));
		sb.append('.');
		sb.append(rnd.nextInt(1002));
		sb.append('.');
		sb.append(rnd.nextInt(1003));
		sb.append('.');
		sb.append(rnd.nextInt(1004));
		sb.append('.');
		sb.append(rnd.nextInt(1005));
		sb.append('.');
		sb.append(rnd.nextInt(1006));
		sb.append('.');
		sb.append(rnd.nextInt(1007));
		sb.append('.');
		sb.append(rnd.nextInt(1008));
		sb.append('.');
		sb.append(rnd.nextInt(1009));
		sb.append('.');
		sb.append(rnd.nextInt(1010));
		sb.append('.');
		sb.append(rnd.nextInt(1011));

		return sb.toString();
	}

}
