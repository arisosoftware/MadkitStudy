package com.ariso.playground.util;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

public class RC4Encrpyt {
	@Benchmark
 
	public void EncryptTest() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		byte[] encrypted = RC4Encrpyt.encrypt(toEncrypt, "password");

		String decrypted = RC4Encrpyt.decrypt(encrypted, "password");
		if (!toEncrypt.equals(decrypted))
			throw new Exception("Fail");
	}

	private static String algorithm = "RC4";

	public static byte[] encrypt(String toEncrypt, String key) throws Exception {
		// create a binary key from the argument key (seed)
		SecureRandom sr = new SecureRandom(key.getBytes());
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(sr);
		SecretKey sk = kg.generateKey();

		// create an instance of cipher
		Cipher cipher = Cipher.getInstance(algorithm);

		// initialize the cipher with the key
		cipher.init(Cipher.ENCRYPT_MODE, sk);

		// enctypt!
		byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

		return encrypted;
	}

	public static String decrypt(byte[] toDecrypt, String key) throws Exception {
		// create a binary key from the argument key (seed)
		SecureRandom sr = new SecureRandom(key.getBytes());
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(sr);
		SecretKey sk = kg.generateKey();

		// do the decryption with that key
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] decrypted = cipher.doFinal(toDecrypt);

		return new String(decrypted);
	}
}
