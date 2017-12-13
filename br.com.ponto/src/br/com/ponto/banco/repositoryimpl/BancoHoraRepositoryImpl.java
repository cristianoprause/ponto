package br.com.ponto.banco.repositoryimpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.banco.conexao.EclipselinkConnection;
import br.com.ponto.banco.modelo.BancoHora;
import br.com.ponto.banco.repository.BancoHoraRepository;

public class BancoHoraRepositoryImpl extends EclipselinkConnection<BancoHora> implements BancoHoraRepository{

	@Override
	public BancoHora find(Long id) {
		Query q = getEntityManager().createQuery("select b from BancoHora b where b.id = :id");
		q.setParameter("id", id);
		return (BancoHora) q.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BancoHora> findBancoVigente(Date dtInicial, Date dtFinal) {
		Query q = getEntityManager().createQuery("select b from BancoHora b "
				+ "where b.usuario = :usuario "
				+ "  and (b.dataInicial <= :dataFinal and b.dataFinal >= :dataInicial) ");
		q.setParameter("usuario", UsuarioHelper.getUsuarioLogado())
		.setParameter("dataInicial", dtInicial)
		.setParameter("dataFinal", dtFinal);
		return q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BancoHora> findAll() {
		Query q = getEntityManager().createQuery("select b from BancoHora b where b.usuario = :usuario");
		q.setParameter("usuario", UsuarioHelper.getUsuarioLogado());
		return q.getResultList();
	}
}
