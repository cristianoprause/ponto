package br.com.ponto.banco.repository;

import java.util.Date;
import java.util.List;

import com.google.inject.ImplementedBy;

import br.com.ponto.banco.modelo.BancoHora;
import br.com.ponto.banco.repositoryimpl.BancoHoraRepositoryImpl;

@ImplementedBy(BancoHoraRepositoryImpl.class)
public interface BancoHoraRepository extends ModeloRepository<BancoHora>{

	List<BancoHora> findBancoVigente(Date dtInicial, Date dtFinal);
	
}
