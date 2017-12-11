package br.com.ponto.banco.repository;

import com.google.inject.ImplementedBy;

import br.com.ponto.banco.modelo.Configuracao;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repositoryimpl.ConfiguracaoRepositoryImpl;

@ImplementedBy(ConfiguracaoRepositoryImpl.class)
public interface ConfiguracaoRepository extends ModeloRepository<Configuracao>{

	/**
	 * Busca a configuração que pertence ao usuário
	 * @param usuario é o usuário cuja configuração esta sendo buscada
	 * @return configuração do usuário
	 */
	Configuracao findByUsuario(Usuario usuario);
	
}
