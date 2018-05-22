package com.ariso.playground;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.ariso.playground.util.AESEncrpyt;
import com.ariso.playground.util.GZIP_and_AES;
import com.ariso.playground.util.RC4Encrpyt;
import com.ariso.playground.util.StringAndStringBuffer;

/**
 * Hello world!
 *
 */
public class BenchmarkApp {
	public static void main(String[] args) {
		TestStringAndStringBuilder();

	}

	static void TestEncrypt_AES_Gzip() {
		try {

			Options opt = new OptionsBuilder().include(RC4Encrpyt.class.getSimpleName())
					.include(AESEncrpyt.class.getSimpleName()).include(GZIP_and_AES.class.getSimpleName())
					.warmupIterations(2).measurementIterations(5).forks(1).shouldDoGC(true).build();

			new Runner(opt).run();
		} catch (RunnerException e) {
			e.printStackTrace();
		}
	}

	static void TestStringAndStringBuilder() {
		try {

			Options opt = new OptionsBuilder().include(StringAndStringBuffer.class.getSimpleName())

					.warmupIterations(2)
					.measurementIterations(15)
					.forks(1).shouldDoGC(true).build();

			new Runner(opt).run();
		} catch (RunnerException e) {
			e.printStackTrace();
		}
	}
}
