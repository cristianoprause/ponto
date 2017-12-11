package br.com.ponto.aplicacao.service;

import br.com.ponto.banco.modelo.Configuracao;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repository.ConfiguracaoRepository;

public class ConfiguracaoService extends ModeloService<Configuracao>{

	@Override
	public ConfiguracaoRepository getRepository() {
		return getInjector().getInstance(ConfiguracaoRepository.class);
	}
	
	public Configuracao findByUsuario(Usuario usuario) {
		return getRepository().findByUsuario(usuario);
	}

}
