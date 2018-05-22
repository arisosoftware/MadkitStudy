package com.ariso.playground.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.openjdk.jmh.annotations.Benchmark;

public class HashBenchmark {
	@Benchmark
	public void HashTest() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		int encrypted = Murmur3.hash32(toEncrypt.getBytes());

		// String result = new Base64Encoder().encode(data);
	}

	@Benchmark
	public void MD5TtestWithPreset() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		byte[] data = GetMd5withPreset(toEncrypt.getBytes());
	}

	@Benchmark
	public void MD5TtestWithoutPreset() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		byte[] data = GetMd5withoutPreset(toEncrypt.getBytes());
	}

	public static String encrypt(String source) {
		return encodeMd5(source.getBytes());
	}

	static MessageDigest md5 = null;

	byte[] GetMd5withPreset(byte[] source) {
		try {
			if (md5 == null) {
				md5 = MessageDigest.getInstance("MD5");
			}

			return md5.digest(source);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	byte[] GetMd5withoutPreset(byte[] source) {
		try {

			return MessageDigest.getInstance("MD5").digest(source);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private static String encodeMd5(byte[] source) {
		try {
			if (md5 == null) {
				md5 = MessageDigest.getInstance("MD5");
			}

			return encodeHex(md5.digest(source));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private static String encodeHex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10)
				buffer.append("0");
			buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buffer.toString();
	}

}
