package com.ariso.playground;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.ariso.playground.util.HashBenchmark;
import com.ariso.playground.util.MD5Hash;

public class HashBenchmarkApp {

	public static void main(String[] args) {
		try {

			Options opt = new OptionsBuilder().include(HashBenchmark.class.getSimpleName())

					.warmupIterations(2).measurementIterations(5).forks(1).shouldDoGC(true).build();

			new Runner(opt).run();
		} catch (RunnerException e) {
			e.printStackTrace();
		}

	}

}
