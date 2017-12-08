package br.com.ponto.banco.repositoryimpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.ponto.aplicacao.helper.StringHelper;
import br.com.ponto.banco.conexao.EclipselinkConnection;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repository.UsuarioRepository;

public class UsuarioRepositoryImpl extends EclipselinkConnection<Usuario> implements UsuarioRepository{

	@Override
	public boolean isUsuarioCadastrado() {
		try {
			Query q = getEntityManager().createQuery("select u from Usuario u ");
			q.setMaxResults(1);
			q.getSingleResult();
			return true; 
		}catch(NoResultException e) {
			return false;
		}
	}
	
	@Override
	public Usuario find(Long id) {
		Query q = getEntityManager().createQuery("select u from Usuario u where u.id = :id");
		q.setParameter("id", id);
		return (Usuario) q.getSingleResult();
	}
	
	@Override
	public Usuario findByUsuarioSenha(String usuario, String senha) {
		try{
			Query q = getEntityManager().createQuery("select u from Usuario u " +
					"where u.login = :usuario" +
					"  and (:senha = null or :senha = u.senha)");
			q.setParameter("usuario", usuario)
			.setParameter("senha", senha == null ? null : StringHelper.convertToMD5(senha));
			return (Usuario) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		Query q = getEntityManager().createQuery("select u from Usuario u");
		return q.getResultList();
	}

}
