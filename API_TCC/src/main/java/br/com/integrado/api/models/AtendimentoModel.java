package br.com.integrado.api.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.integrado.api.enums.SituacaoAtendimento;

@Entity
@Table(name = "atendimento")
public class AtendimentoModel implements Serializable{

	private static final long serialVersionUID = -1699431020145007374L;

	private Long id;
	private ClienteModel cliente;
	private Double valDesconto;
	private Date dataAtendimento;
	private Date dataTransacao;
	private SituacaoAtendimento situacao;
	private Date dataFinalPago;
	private Double valTotal;
	
	
	public AtendimentoModel() {
	}
	
	public AtendimentoModel(Long id, ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			SituacaoAtendimento situacao, Date dataFinalPago, Double valTotal) {
		this.id = id;
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotal = valTotal;
	}
	
	public AtendimentoModel(ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			SituacaoAtendimento situacao, Date dataFinalPago, Double valTotal) {
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotal = valTotal;
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
	@JoinColumn(name = "id_cliente")
	public ClienteModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}
	
	@Column(name = "val_desconto", nullable = true)
	public Double getValDesconto() {
		return valDesconto;
	}
	public void setValDesconto(Double valDesconto) {
		this.valDesconto = valDesconto;
	}
	
	@Column(name = "dt_atendimento")
	@Temporal(TemporalType.DATE)
	public Date getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	
	@Column(name = "dt_transacao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	public SituacaoAtendimento getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoAtendimento situacao) {
		this.situacao = situacao;
	}
	
	@Column(name = "dt_final_pago")
	@Temporal(TemporalType.DATE)
	public Date getDataFinalPago() {
		return dataFinalPago;
	}
	public void setDataFinalPago(Date dataFinalPago) {
		this.dataFinalPago = dataFinalPago;
	}
	
	@Column(name = "val_total")
	public Double getValTotal() {
		return valTotal;
	}
	public void setValTotal(Double valTotal) {
		this.valTotal = valTotal;
	}
	
	@Override
	public String toString() {
		return "AtendimentoModel [id=" + id + ", cliente=" + cliente + ", valDesconto=" + valDesconto
				+ ", dataAtendimento=" + dataAtendimento + ", dataTransacao=" + dataTransacao + ", situacao=" + situacao
				+ ", dataFinalPago=" + dataFinalPago + ", valTotal=" + valTotal + "]";
	}
	
}
