package br.com.ponto.tela.text;

import java.math.BigDecimal;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.NumberFormatter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import br.com.ponto.aplicacao.helper.FormatterHelper;

public class NumberText extends FormattedText{

	public NumberText(Composite parent) {
		super(parent, SWT.BORDER);
		setFormatter(new NumberFormatter("#,###,###,###,###,###,###.##", FormatterHelper.BRASIL));
	}
	
	public BigDecimal getValor() {
		return new BigDecimal(getValue().toString());
	}
	
	public void setValor(BigDecimal valor) {
		setValue(valor);
	}

}
