package br.com.ponto.tela.filter;

import br.com.ponto.banco.modelo.Usuario;

public class UsuarioFilter extends ModeloFilter<Usuario>{

	@Override
	protected boolean verificar(Usuario element) {
		return element.getNome().toLowerCase().matches(search.toLowerCase()) ||
				element.getLogin().toLowerCase().matches(search.toLowerCase());
	}

}
