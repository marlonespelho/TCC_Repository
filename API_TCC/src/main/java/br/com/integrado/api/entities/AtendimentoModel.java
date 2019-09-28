package br.com.integrado.api.entities;

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

import br.com.integrado.api.enums.StatusAtendimento;

@Entity
@Table(name = "atendimento")
public class AtendimentoModel implements Serializable{

	private static final long serialVersionUID = -1699431020145007374L;

	private Long id;
	private ClienteModel cliente;
	private Double valDesconto;
	private Date dataAtendimento;
	private Date dataTransacao;
	private StatusAtendimento situacao;
	private Date dataFinalPago;
	private Double valTotalProdutos;
	private Double valTotalServicos;
	
	
	public AtendimentoModel() {
	}
	
	public AtendimentoModel(Long id, ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			StatusAtendimento situacao, Date dataFinalPago, Double valTotalProdutos, Double valTotalServicos) {
		this.id = id;
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
	}
	
	public AtendimentoModel(Long id, ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			StatusAtendimento situacao, Double valTotalProdutos, Double valTotalServicos) {
		this.id = id;
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
	}
	
	public AtendimentoModel(ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			StatusAtendimento situacao, Double valTotalProdutos, Double valTotalServicos) {
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
	}
	
	public AtendimentoModel(ClienteModel cliente, Double valDesconto, Date dataAtendimento, Date dataTransacao,
			StatusAtendimento situacao, Date dataFinalPago, Double valTotalProdutos, Double valTotalServicos) {
		this.cliente = cliente;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.dataTransacao = dataTransacao;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
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
	public StatusAtendimento getSituacao() {
		return situacao;
	}
	public void setSituacao(StatusAtendimento situacao) {
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
	@Column(name = "total_produtos")
	public Double getValTotalProdutos() {
		return valTotalProdutos;
	}

	public void setValTotalProdutos(Double valTotalProdutos) {
		this.valTotalProdutos = valTotalProdutos;
	}
	@Column(name = "total_servicos")
	public Double getValTotalServicos() {
		return valTotalServicos;
	}

	public void setValTotalServicos(Double valTotalServicos) {
		this.valTotalServicos = valTotalServicos;
	}

	@Override
	public String toString() {
		return "AtendimentoModel [id=" + id + ", cliente=" + cliente + ", valDesconto=" + valDesconto
				+ ", dataAtendimento=" + dataAtendimento + ", dataTransacao=" + dataTransacao + ", situacao=" + situacao
				+ ", dataFinalPago=" + dataFinalPago + ", valTotalProdutos=" + valTotalProdutos + ", valTotalServicos="
				+ valTotalServicos + "]";
	}

}
