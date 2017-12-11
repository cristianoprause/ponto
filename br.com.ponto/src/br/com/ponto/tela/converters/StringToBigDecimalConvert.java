package br.com.ponto.tela.converters;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.Converter;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class StringToBigDecimalConvert extends Converter{

	public StringToBigDecimalConvert() {
		super(String.class, BigDecimal.class);
	}

	@Override
	public Object convert(Object arg0) {
		if(arg0 != null && !((String)arg0).isEmpty())
			return FormatterHelper.parseBigDecimal((String) arg0);
		
		return null;
	}

}
