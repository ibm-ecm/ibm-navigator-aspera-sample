/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Utility methods used for encryption.
 */
class Encryption {

	private static final String TRANSFORAMTION = "AES/CBC/PKCS5Padding";
	private static Key secureKey = null;

	/**
	 * Encrypts the specified text then encodes to a string value.
	 *
	 * @param text The text to encrypt
	 * @param iv   The initialization vector
	 * @return The encoded string value of the encrypted text
	 * @throws Exception if an error occurs while encrypting or encoding
	 */
	static String encrypt(String text, byte[] iv) throws Exception {
		if (text == null)
			return null;

		Cipher cipher = Cipher.getInstance(TRANSFORAMTION);
		cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv));
		byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));

		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * Decodes the specified text then decrypts to a string value.
	 *
	 * @param text The text to decrypt
	 * @param iv   The initialization vector
	 * @return The decoded string value of the decrypted text
	 * @throws Exception if an error occurs while decoding or decrypting
	 */
	static String decrypt(String text, byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORAMTION);
		cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv));
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));

		return new String(decryptedBytes, "UTF-8");
	}

	/**
	 * Loads the secret key used to encrypt and decrypt.
	 *
	 * @throws Exception if an error occurs while loading the key
	 */
	static synchronized void loadSecretKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		secureKey = new SecretKeySpec(keyGen.generateKey().getEncoded(), "AES");
	}
}
