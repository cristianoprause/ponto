package br.com.ponto.aplicacao.service;

import java.util.Date;
import java.util.List;

import br.com.ponto.aplicacao.helper.Helper;
import br.com.ponto.banco.modelo.BancoHora;
import br.com.ponto.banco.repository.BancoHoraRepository;

public class BancoHoraService extends ModeloService<BancoHora>{

	@Override
	public BancoHoraRepository getRepository() {
		return getInjector().getInstance(BancoHoraRepository.class);
	}
	
	/**
	 * Verifica se ja não existe um banco de horas registrado no mesmo período que o banco de horas verificado
	 * @param banco é o banco de horas verificado
	 * @return true caso o período deste banco de horas esteja livre e false caso não
	 */
	public boolean isBancoHoraLivre(BancoHora banco) {
		for(BancoHora bancoRegistrado : findBancoVigente(banco.getDataInicial(), banco.getDataFinal())){
			if(!Helper.isEquals(banco.getId(), bancoRegistrado.getId()))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Busca todos os bancos de hora do usuário que contenham ao menos 1 dia do período informado
	 * @param dataInicial é a data inicial do período buscado
	 * @param dataFinal é a data final do período buscado
	 * @return lista com os bancos de hora dentro do período
	 */
	public List<BancoHora> findBancoVigente(Date dataInicial, Date dataFinal){
		return getRepository().findBancoVigente(dataInicial, dataFinal);
	}

}
