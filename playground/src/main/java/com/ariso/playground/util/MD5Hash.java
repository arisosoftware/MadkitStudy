package com.ariso.playground.util;

import java.security.MessageDigest;

import org.openjdk.jmh.annotations.Benchmark;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
	@Benchmark
	public void HashTest() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		String result = encodeMd5(toEncrypt.getBytes());
	}

	public static String encrypt(String source) {
		return encodeMd5(source.getBytes());
	}

	private static String encodeMd5(byte[] source) {
		try {
			return encodeHex(MessageDigest.getInstance("MD5").digest(source));
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
