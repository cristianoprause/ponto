package br.com.ponto.aplicacao.helper;

import br.com.ponto.banco.modelo.Usuario;

public class UsuarioHelper {

	private UsuarioHelper() {}
	
	private static Usuario usuarioLogado;
	
	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public static void setUsuarioLogado(Usuario usuarioLogado) {
		UsuarioHelper.usuarioLogado = usuarioLogado;
	}
}
