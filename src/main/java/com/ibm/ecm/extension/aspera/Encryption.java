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
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

/**
 * Utility methods used for encryption.
 */
final class Encryption {

	private static final String TRANSFORAMTION = "AES/CBC/PKCS5Padding";
	private static Key secureKey;

	private Encryption() {
	}

	/**
	 * Encrypts the specified text then encodes to a string value.
	 *
	 * @param text The text to encrypt
	 * @param iv   The initialization vector
	 * @return The encoded string value of the encrypted text
	 * @throws AsperaPluginRuntimeException if an error occurs while encrypting or encoding
	 */
	static String encrypt(final String text, final byte[] iv) throws AsperaPluginRuntimeException {
		if (text == null) {
			return null;
		}

		try {
			final Cipher cipher = Cipher.getInstance(TRANSFORAMTION);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv));

			return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
		} catch (final GeneralSecurityException | UnsupportedEncodingException e) {
			throw new AsperaPluginRuntimeException("Failed to encrypt the text", e);
		}
	}

	/**
	 * Decodes the specified text then decrypts to a string value.
	 *
	 * @param text The text to decrypt
	 * @param iv   The initialization vector
	 * @return The decoded string value of the decrypted text
	 * @throws AsperaPluginRuntimeException if an error occurs while decoding or decrypting
	 */
	static String decrypt(final String text, final byte[] iv) throws AsperaPluginRuntimeException {
		try {
			final Cipher cipher = Cipher.getInstance(TRANSFORAMTION);
			cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv));
			final byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));

			return new String(decryptedBytes, "UTF-8");
		} catch (final GeneralSecurityException | UnsupportedEncodingException e) {
			throw new AsperaPluginRuntimeException("Failed to decrypt the text", e);
		}
	}

	/**
	 * Loads the secret key used to encrypt and decrypt.
	 *
	 * @throws AsperaPluginException if an error occurs while loading the key
	 */
	static void loadSecretKey() throws AsperaPluginException {
		try {
			final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			secureKey = new SecretKeySpec(keyGen.generateKey().getEncoded(), "AES");
		} catch (final GeneralSecurityException e) {
			throw new AsperaPluginException("Failed to load the secret key.", e);
		}
	}
}
