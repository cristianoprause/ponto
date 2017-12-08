package br.com.ponto.aplicacao.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class StringHelper {

	private StringHelper() {}
	
	public static String convertToMD5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(text.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16);
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(StringHelper.class).error("Erro ao converter String em MD5", e);
		}
		
		return null;
	}
	
}
