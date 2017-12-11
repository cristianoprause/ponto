package br.com.ponto.aplicacao.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	private DateHelper() {}
	
	/**
	 * Instância um {@code Date} com os dados informados
	 * @param dia é o dia que a data conterá
	 * @param mes é o mês que a data conterá
	 * @param ano é o ano que a data conterá
	 * @return data com os dados informados
	 */
	public static Date criarData(Integer dia, Integer mes, Integer ano) {
		Calendar c = Calendar.getInstance();
		c.set(ano, mes, dia, 0, 0, 0);
		return c.getTime();
	}
	
}
