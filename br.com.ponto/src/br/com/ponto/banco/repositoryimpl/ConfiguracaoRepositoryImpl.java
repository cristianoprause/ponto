package br.com.ponto.banco.repositoryimpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.ponto.banco.conexao.EclipselinkConnection;
import br.com.ponto.banco.modelo.Configuracao;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.banco.repository.ConfiguracaoRepository;

public class ConfiguracaoRepositoryImpl extends EclipselinkConnection<Configuracao> implements ConfiguracaoRepository{

	@Override
	public Configuracao find(Long id) {
		Query q = getEntityManager().createQuery("select c from Configuracao c where c.id = :id");
		q.setParameter("id", id);
		return (Configuracao) q.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Configuracao> findAll() {
		Query q = getEntityManager().createQuery("select c from Configuracao c");
		return q.getResultList();
	}

	@Override
	public Configuracao findByUsuario(Usuario usuario) {
		try {
			Query q = getEntityManager().createQuery("select c from Configuracao c where c.usuario = :usuario");
			q.setParameter("usuario", usuario);
			return (Configuracao) q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

}
