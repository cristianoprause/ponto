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
	private static SimpleDateFormat dateFormat;
	
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
	 * Faz o mesmo que em {@link #formatDatePattern(Date, String)} mas com o pattern fixo de dd/MM/yyyy
	 * @param date é a data que será transformada
	 * @return string com a data formatada
	 */
	public static String formatDate(Date date) {
		return getDateFormat().format(date);
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
	
	
	/**
	 * Cria o decimal format para valores em {@code BigDecimal} com 2 casas após a virgula
	 * @return Decimal format
	 */
	private static DecimalFormat getBigDecimalDuasCasas() {
		if(bigDecimalDuasCasas == null) {
			bigDecimalDuasCasas = (DecimalFormat) DecimalFormat.getInstance(BRASIL);
			bigDecimalDuasCasas.setMinimumFractionDigits(2);
			bigDecimalDuasCasas.setParseBigDecimal(true);
		}
		return bigDecimalDuasCasas;
	}
	
	/**
	 * Cria o date format para datas no formato dd/MM/yyyy
	 * @return date format
	 */
	private static SimpleDateFormat getDateFormat() {
		if(dateFormat == null)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat;
	}
	
}
