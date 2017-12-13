package br.com.ponto.tela.converters;

import java.util.Date;

import org.eclipse.core.databinding.conversion.Converter;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class DateToStringConvert extends Converter{

	private String pattern;
	
	public DateToStringConvert() {
		this("dd/MM/yyyy");
	}
	
	public DateToStringConvert(String pattern) {
		super(Date.class, String.class);
		this.pattern = pattern;
	}

	@Override
	public Object convert(Object arg0) {
		if(arg0 != null)
			return FormatterHelper.formatDatePattern((Date) arg0, pattern);
		
		return null;
	}

}
