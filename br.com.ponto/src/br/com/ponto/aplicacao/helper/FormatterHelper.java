package br.com.ponto.aplicacao.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatterHelper {

	public static final Locale BRASIL = new Locale("pt","BR");
	private static DecimalFormat bigDecimalDuasCasas;
	
	private FormatterHelper() {}
	
	/**
	 * Transforma o bigDecimal em uma string formatada
	 * @param value é o valor em bigDecimal que será transformada
	 * @return string com o valor formatado
	 */
	public static String formatBigDecimal(BigDecimal value) {
		return getBigDecimalDuasCasas().format(value);
	}
	
	/**
	 * Transforma a string em um objeto do tipo {@code BigDecimal}
	 * @param value é o valor que será transformado
	 * @return BigDecimal convertido
	 */
	public static BigDecimal parseBigDecimal(String value) {
		try {
			return (BigDecimal) getBigDecimalDuasCasas().parse(value);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Transforma a data em uma string formatada conforme o pattern informado
	 * @param date é a data que será transformada
	 * @param pattern é o pattern da formatação
	 * @return string com a data formatada
	 */
	public static String formatDatePattern(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * Transforma a string em um objeto do tipo {@code Date}
	 * @param value é o valor que será transformado
	 * @param pattern é o formato da data que ele veio
	 * @return date convertido
	 */
	public static Date parseDatePattern(String value, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(value);
		} catch (ParseException e) {
			return null;
		}
	}
	
	private static DecimalFormat getBigDecimalDuasCasas() {
		if(bigDecimalDuasCasas == null) {
			bigDecimalDuasCasas = (DecimalFormat) DecimalFormat.getInstance(BRASIL);
			bigDecimalDuasCasas.setMinimumFractionDigits(2);
			bigDecimalDuasCasas.setParseBigDecimal(true);
		}
		return bigDecimalDuasCasas;
	}
	
}
