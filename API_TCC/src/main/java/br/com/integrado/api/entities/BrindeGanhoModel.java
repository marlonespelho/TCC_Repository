package br.com.integrado.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.integrado.api.enums.TipoBrinde;

@Entity
@Table(name = "brinde_ganho")
public class BrindeGanhoModel {

	private Long id;
	private BrindeModel brinde;
	private TipoBrinde tipoBrinde;
	private ServicoAtendimentoModel servicoAtendimento;
	private Integer quantidade;
	
	public BrindeGanhoModel() {
	}
			
	public BrindeGanhoModel(BrindeModel brinde, TipoBrinde tipoBrinde,
			ServicoAtendimentoModel servicoAtendimento, Integer quantidade) {
		this.brinde = brinde;
		this.tipoBrinde = tipoBrinde;
		this.servicoAtendimento = servicoAtendimento;
		this.quantidade = quantidade;
	}
	
	public BrindeGanhoModel(Long id, BrindeModel brinde, TipoBrinde tipoBrinde,
			ServicoAtendimentoModel servicoAtendimento, Integer quantidade) {
		this.id = id;
		this.brinde = brinde;
		this.tipoBrinde = tipoBrinde;
		this.servicoAtendimento = servicoAtendimento;
		this.quantidade = quantidade;
	}

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "brinde", nullable = false)
	public BrindeModel getBrinde() {
		return brinde;
	}
	public void setBrinde(BrindeModel brinde) {
		this.brinde = brinde;
	}
	
	@Column(name = "tipo_brinde", nullable = false)
	public TipoBrinde getTipoBrinde() {
		return tipoBrinde;
	}
	public void setTipoBrinde(TipoBrinde tipoBrinde) {
		this.tipoBrinde = tipoBrinde;
	}

	@OneToOne
	@JoinColumn(name = "serv_atend_id")
	public ServicoAtendimentoModel getServicoAtendimento() {
		return servicoAtendimento;
	}

	public void setServicoAtendimento(ServicoAtendimentoModel servicoAtendimento) {
		this.servicoAtendimento = servicoAtendimento;
	}

	@Column
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
}
