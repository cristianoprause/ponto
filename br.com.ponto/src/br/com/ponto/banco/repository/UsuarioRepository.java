package br.com.ponto.banco.repository;

import com.google.inject.ImplementedBy;

import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repositoryimpl.UsuarioRepositoryImpl;

@ImplementedBy(UsuarioRepositoryImpl.class)
public interface UsuarioRepository extends ModeloRepository<Usuario>{
	
	/**
	 * Diz se existe algum usuário cadastrado no sistema
	 * @return true caso exista ao menos um usuário cadastrado e false caso não
	 */
	boolean isUsuarioCadastrado();
	Usuario findByUsuarioSenha(String usuario, String senha);
	
}
