package br.com.ponto.tela.converters;

import java.util.Date;

import org.eclipse.core.databinding.conversion.Converter;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class StringToDateConvert extends Converter{

	private String pattern;
	
	public StringToDateConvert() {
		this("dd/MM/yyyy");
	}
	
	public StringToDateConvert(String pattern) {
		super(String.class, Date.class);
		this.pattern = pattern;
	}

	@Override
	public Object convert(Object arg0) {
		if(arg0 != null && !((String)arg0).isEmpty())
			return FormatterHelper.parseDatePattern((String) arg0, pattern);
		
		return null;
	}

}
