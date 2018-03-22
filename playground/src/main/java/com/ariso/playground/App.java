package com.ariso.playground;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.ariso.playground.util.AESEncrpyt;
import com.ariso.playground.util.GZIP;
import com.ariso.playground.util.RC4Encrpyt;

 
/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try {

			Options opt = new OptionsBuilder()
					.include(RC4Encrpyt.class.getSimpleName())
					.include(AESEncrpyt.class.getSimpleName())
					.include(GZIP.class.getSimpleName())
					.warmupIterations(2)
					.measurementIterations(5)
					.forks(1)
					.shouldDoGC(true)
					.build();

			new Runner(opt).run();
		} catch (RunnerException e) {
			e.printStackTrace();
		}

	}
}
