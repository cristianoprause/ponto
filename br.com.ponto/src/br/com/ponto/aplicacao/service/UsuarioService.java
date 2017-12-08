package br.com.ponto.aplicacao.service;

import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repository.UsuarioRepository;

public class UsuarioService extends ModeloService<Usuario>{

	public boolean isUsuarioCadastrado() {
		return getRepository().isUsuarioCadastrado();
	}
	
	@Override
	public UsuarioRepository getRepository() {
		return getInjector().getInstance(UsuarioRepository.class);
	}
	
	public Usuario findByUsuario(String usuario){
		return findByUsuarioSenha(usuario, null);
	}
	
	public Usuario findByUsuarioSenha(String usuario, String senha){
		return getRepository().findByUsuarioSenha(usuario, senha);
	}

}
