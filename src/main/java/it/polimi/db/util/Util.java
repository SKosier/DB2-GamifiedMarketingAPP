package it.polimi.db.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Helper methods
 *
 */
public class Util {

	public static final String DELIMITER = "|||";

	/**
	 * Metoda koja racuna sha-1 od unesene lozinke
	 * 
	 * @param password User's password
	 * @return string prikaz hex zapisa sha-1 digesta.
	 * @author Sanja
	 */
	public static String calculateHash(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] byteArrayDigest = digest.digest();
		return bytetohex(byteArrayDigest);
	}

	/**
	 * Method for converting to byte array into hex number.
	 * 
	 * @param bytearray Array of bytes that will be converted to hex number.
	 * @return Converted input into string representation of hex number.
	 * @author Sanja
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();

		for (byte b : bytearray) {
			sb.append(String.format("%02X", b));
		}

		return sb.toString().toLowerCase();
	}
}
