package com.ariso.playground;

import com.alibaba.fastjson.JSON;
import com.ariso.playground.util.AESEncrpyt;
import com.ariso.playground.util.GZIP_and_AES;
import com.ariso.playground.util.RC4Encrpyt;
import com.ariso.straws.StrawConfig;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * @throws Exception
	 * 
	 */
	public void testAES() throws Exception {

		byte[] encrypted = AESEncrpyt.encrypt(toEncrypt, "password");

		String decrypted = AESEncrpyt.decrypt(encrypted, "password");

		assertTrue(toEncrypt.equals(decrypted));
	}

	public void testGZIPEncrpyt() throws Exception {

		byte[] encrypted = GZIP_and_AES.encryptAndCompress(toEncrypt, "password");

		String decrypted = GZIP_and_AES.decryptAndDeCompress(encrypted, "password");

		System.out.println(decrypted);

		assertTrue(toEncrypt.equals(decrypted));
	}

	static String toEncrypt = "The shorter you live, the longer you're dead!";

	public void testFastEncrypt() throws Exception {

		GZIP_and_AES gzip = new GZIP_and_AES();

		byte[] encrypted = gzip.Pre_Encrypt(toEncrypt, "password");

		String decrypted = gzip.Pre_decrypt(encrypted, "password");

		if (!toEncrypt.equals(decrypted))
			throw new Exception("Fail");
	}

	public void testRC4() throws Exception {

		byte[] encrypted = RC4Encrpyt.encrypt(toEncrypt, "password");

		String decrypted = RC4Encrpyt.decrypt(encrypted, "password");

		assertTrue(toEncrypt.equals(decrypted));
	}

	public void testFastJson() throws Exception {
		StrawConfig config = new StrawConfig();
		config.localIp = "12.1.12.1";
		config.localPort = 1111;
		config.serverPort = 2222;
		config.serverIp = "kkasjdfla";

		String output = JSON.toJSONString(config);

		assertTrue(output.equals(
				"{\"localIp\":\"12.1.12.1\",\"localPort\":1111,\"serverIp\":\"kkasjdfla\",\"serverPort\":2222}"));

	}

}
