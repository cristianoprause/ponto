package br.com.ponto.tela.converters;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.Converter;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class BigDecimalToStringConvert extends Converter{

	public BigDecimalToStringConvert() {
		super(BigDecimal.class, String.class);
	}

	@Override
	public Object convert(Object arg0) {
		if(arg0 != null)
			return FormatterHelper.formatBigDecimal((BigDecimal) arg0);
		
		return null;
	}

}
