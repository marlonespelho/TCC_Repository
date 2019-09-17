package br.com.integrado.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "parcela_atendimento")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ParcelaAtendimentoModel extends ParcelaModel{

	private static final long serialVersionUID = 7096234249409259943L;
	private AtendimentoModel atendimento;
	private Double valJuros;
	
	public ParcelaAtendimentoModel() {
	}
	
	public ParcelaAtendimentoModel(Long id, ParcelaModel parcPrincipal, Date dtVencimento, Double valParcela,
			Double valPago, Date dtPago, FormaPagamentoModel fmPagamento, TipoPagamentoModel tpPagamento,
			AtendimentoModel atendimento, Double valJuros) {
		super(id, parcPrincipal, dtVencimento, valParcela, valPago, dtPago, fmPagamento, tpPagamento);
		this.atendimento = atendimento;
		this.valJuros = valJuros;
	}

	public ParcelaAtendimentoModel(ParcelaModel parcPrincipal, Date dtVencimento, Double valParcela, Double valPago,
			Date dtPago, FormaPagamentoModel fmPagamento, TipoPagamentoModel tpPagamento, AtendimentoModel atendimento,
			Double valJuros) {
		super(parcPrincipal, dtVencimento, valParcela, valPago, dtPago, fmPagamento, tpPagamento);
		this.atendimento = atendimento;
		this.valJuros = valJuros;
	}

	@ManyToOne
	@JoinColumn(name = "id_atendimento")
	public AtendimentoModel getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(AtendimentoModel atendimento) {
		this.atendimento = atendimento;
	}
	
	@Column(name = "val_juros", nullable = true)
	public Double getValJuros() {
		return valJuros;
	}
	public void setValJuros(Double valJuros) {
		this.valJuros = valJuros;
	}
	@Override
	public String toString() {
		return "ParcelaAtendimentoModel [atendimento=" + atendimento + ", valJuros=" + valJuros + "]";
	}
	
}
