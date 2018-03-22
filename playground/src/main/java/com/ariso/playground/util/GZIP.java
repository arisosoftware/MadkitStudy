package com.ariso.playground.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import com.alibaba.fastjson.JSON;
import com.ariso.straws.StrawConfig;

public class GZIP {
	@Benchmark
	public void EncryptTest() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		byte[] encrypted = encryptAndCompress(toEncrypt, "password");

		String decrypted = decryptAndDeCompress(encrypted, "password");

		if (!toEncrypt.equals(decrypted))
			throw new Exception("Fail");
	}

	@Benchmark
	public void TestFastJson() {
		StrawConfig config = new StrawConfig();
		config.localIp = "12.1.12.1";
		config.localPort = 1111;
		config.serverPort = 2222;
		config.serverIp = "kkasjdfla";
		String output = JSON.toJSONString(config);

	}
	
	@Benchmark
	public void FastEncrytTest() throws Throwable {

		String toEncrypt = "The shorter you live, the longer you're dead!";

		byte[] encrypted = Pre_Encrypt(toEncrypt, "password");

		String decrypted = Pre_decrypt(encrypted, "password");

		if (!toEncrypt.equals(decrypted))
			throw new Exception("Fail");
	}

	private static String algorithm = "AES";
	private static Cipher cipher_encrypt;
	private static Cipher cipher_decrypt;
	private static String theKey;

	public Cipher GetEncrpytCipher(String Key, String encryptAlgorithm) throws Exception {
		if (cipher_encrypt == null || (theKey != null && !theKey.equals(Key))) {
			InitCipher(Key, encryptAlgorithm);
		}

		return cipher_encrypt;

	}

	public Cipher GetDecrpytCipher(String Key, String encryptAlgorithm) throws Exception {
		if (cipher_decrypt == null || (theKey != null && !theKey.equals(Key))) {
			InitCipher(Key, encryptAlgorithm);
		}

		return cipher_decrypt;

	}

	public void InitCipher(String Key, String encryptAlgorithm) throws Exception {
       
		SecureRandom sr = new SecureRandom(Key.getBytes());
		KeyGenerator kg = KeyGenerator.getInstance(encryptAlgorithm);
		kg.init(sr);
		SecretKey sk = kg.generateKey();
		theKey = Key;

		cipher_encrypt = Cipher.getInstance(algorithm);
		cipher_encrypt.init(Cipher.ENCRYPT_MODE, sk);
		cipher_decrypt = Cipher.getInstance(algorithm);
		cipher_decrypt.init(Cipher.DECRYPT_MODE, sk);

	}

	public byte[] Pre_Encrypt(String toEncrypt, String key) throws Exception {
		Cipher cipher = GetEncrpytCipher(key, algorithm);
		byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
		return encrypted;
	}

	public String Pre_decrypt(byte[] toDecrypt, String key) throws Exception {
		Cipher cipher = GetDecrpytCipher(key, algorithm);
		byte[] decrypted = cipher.doFinal(toDecrypt);

		return  new String(decrypted);
	}

	public static byte[] encryptAndCompress(String toEncrypt, String key) throws Exception {
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

		byte[] encrypted = cipher.doFinal(compress(toEncrypt));

		return encrypted;
	}

	public static byte[] compress(String data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}

	public static String decryptAndDeCompress(byte[] toDecrypt, String key) throws Exception {
		// create a binary key from the argument key (seed)
		SecureRandom sr = new SecureRandom(key.getBytes());
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(sr);
		SecretKey sk = kg.generateKey();

		// do the decryption with that key
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] decrypted = cipher.doFinal(toDecrypt);

		return decompress(decrypted);
	}

	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
	}
}
