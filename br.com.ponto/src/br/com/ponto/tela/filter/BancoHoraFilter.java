package br.com.ponto.tela.filter;

import br.com.ponto.aplicacao.helper.FormatterHelper;
import br.com.ponto.banco.modelo.BancoHora;

public class BancoHoraFilter extends ModeloFilter<BancoHora>{

	@Override
	protected boolean verificar(BancoHora element) {
		return FormatterHelper.formatDate(element.getDataInicial()).matches(search) ||
				FormatterHelper.formatDate(element.getDataFinal()).matches(search);
	}

}
