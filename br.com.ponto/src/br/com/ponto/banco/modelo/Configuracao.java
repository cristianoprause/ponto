package br.com.ponto.banco.modelo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.ponto.aplicacao.helper.DateHelper;
import br.com.ponto.aplicacao.helper.UsuarioHelper;

@Entity
public class Configuracao implements IModelo{

	private static final long serialVersionUID = -1930755465983576144L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message="Informe o tempo de trabalho nos dias de semana")
	private Date tempoDiaSemana = DateHelper.criarData(1, 1, 1970);
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message="Informe o tempo de trabalho nos sábados")
	private Date tempoSabado = DateHelper.criarData(1, 1, 1970);
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date margemErro = DateHelper.criarData(1, 1, 1970);
	
	@Column(precision=14, scale=2)
	private BigDecimal salario = BigDecimal.ZERO;
	
	@ManyToOne
	private Usuario usuario = UsuarioHelper.getUsuarioLogado();
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getTempoDiaSemana() {
		return tempoDiaSemana;
	}

	public void setTempoDiaSemana(Date tempoDiaSemana) {
		this.tempoDiaSemana = tempoDiaSemana;
	}

	public Date getTempoSabado() {
		return tempoSabado;
	}

	public void setTempoSabado(Date tempoSabado) {
		this.tempoSabado = tempoSabado;
	}

	public Date getMargemErro() {
		return margemErro;
	}

	public void setMargemErro(Date margemErro) {
		this.margemErro = margemErro;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuracao other = (Configuracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
