package br.com.ponto.tela.text;

import java.util.Date;

import org.eclipse.nebula.widgets.formattedtext.DateTimeFormatter;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class DateText extends FormattedText{

	public DateText(Composite parent) {
		this(parent,"dd/MM/yyyy");
	}
	
	public DateText(Composite parent, String pattern) {
		super(parent, SWT.BORDER);
		
		setFormatter(new DateTimeFormatter(pattern, FormatterHelper.BRASIL));
	}
	
	public Date getDate() {
		return (Date) getValue();
	}
	
	public void setDate(Date data) {
		setValue(data);
	}

}
