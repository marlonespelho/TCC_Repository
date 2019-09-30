package br.com.integrado.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "parcela")
public class ParcelaAtendimentoModel implements Serializable{

	private static final long serialVersionUID = 8413479593247193523L;

	private Long id;
	private AtendimentoModel atendimento;
	private ParcelaAtendimentoModel parcPrincipal;
	private Date dtVencimento;
	private Double valParcela;
	private Double valPago;
	private Date dtPago;
	private FormaPagamentoModel fmPagamento;
	private TipoPagamentoModel tpPagamento;
	
	public ParcelaAtendimentoModel() {
	}

	public ParcelaAtendimentoModel(AtendimentoModel atendimento, ParcelaAtendimentoModel parcPrincipal, Date dtVencimento, Double valParcela, Double valPago, Date dtPago,
			FormaPagamentoModel fmPagamento, TipoPagamentoModel tpPagamento) {
		this.parcPrincipal = parcPrincipal;
		this.dtVencimento = dtVencimento;
		this.valParcela = valParcela;
		this.valPago = valPago;
		this.dtPago = dtPago;
		this.fmPagamento = fmPagamento;
		this.tpPagamento = tpPagamento;
		this.atendimento = atendimento;
	}
	
	public ParcelaAtendimentoModel(AtendimentoModel atendimento, Date dtVencimento, Double valParcela,
			Double valPago,	FormaPagamentoModel fmPagamento, TipoPagamentoModel tpPagamento) {
		this.dtVencimento = dtVencimento;
		this.valParcela = valParcela;
		this.valPago = valPago;
		this.fmPagamento = fmPagamento;
		this.tpPagamento = tpPagamento;
		this.atendimento = atendimento;
	}

	public ParcelaAtendimentoModel(Long id, AtendimentoModel atendimento, ParcelaAtendimentoModel parcPrincipal, Date dtVencimento, Double valParcela, Double valPago,
			Date dtPago, FormaPagamentoModel fmPagamento, TipoPagamentoModel tpPagamento) {
		this.id = id;
		this.parcPrincipal = parcPrincipal;
		this.dtVencimento = dtVencimento;
		this.valParcela = valParcela;
		this.valPago = valPago;
		this.dtPago = dtPago;
		this.fmPagamento = fmPagamento;
		this.tpPagamento = tpPagamento;
		this.atendimento = atendimento;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "parc_princ", nullable = true)
	public ParcelaAtendimentoModel getParcPrincipal() {
		return parcPrincipal;
	}
	public void setParcPrincipal(ParcelaAtendimentoModel parcPrincipal) {
		this.parcPrincipal = parcPrincipal;
	}

	@Column(name = "dt_vencimento", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDtVencimento() {
		return dtVencimento;
	}
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
	@Column(name = "val_parcela", nullable = false)
	public Double getValParcela() {
		return valParcela;
	}
	public void setValParcela(Double valParcela) {
		this.valParcela = valParcela;
	}

	@Column(name = "val_pago", nullable = true)
	public Double getValPago() {
		return valPago;
	}
	public void setValPago(Double valPago) {
		this.valPago = valPago;
	}

	@Column(name = "dt_pagamento", nullable = true)
	@Temporal(TemporalType.DATE)
	public Date getDtPago() {
		return dtPago;
	}
	public void setDtPago(Date dtPago) {
		this.dtPago = dtPago;
	}
	
	@ManyToOne
	@JoinColumn(name = "fm_pagamento", nullable = false)
	public FormaPagamentoModel getFmPagamento() {
		return fmPagamento;
	}
	public void setFmPagamento(FormaPagamentoModel fmPagamento) {
		this.fmPagamento = fmPagamento;
	}
	
	@ManyToOne
	@JoinColumn(name = "tp_pagamento", nullable = true)
	public TipoPagamentoModel getTpPagamento() {
		return tpPagamento;
	}
	public void setTpPagamento(TipoPagamentoModel tpPagamento) {
		this.tpPagamento = tpPagamento;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_atendimento", nullable = false)
	public AtendimentoModel getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(AtendimentoModel atendimento) {
		this.atendimento = atendimento;
	}

	@Override
	public String toString() {
		return "ParcelaModel [id=" + id + ", parcPrincipal=" + parcPrincipal + ", dtVencimento=" + dtVencimento
				+ ", valParcela=" + valParcela + ", valPago=" + valPago + ", dtPago=" + dtPago + ", fmPagamento="
				+ fmPagamento + ", tpPagamento=" + tpPagamento + "]";
	}	
		
}
