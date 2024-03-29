package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.integrado.api.enums.TipoBrinde;

/**
 * @author marlo
 *
 */
@Entity
@Table(name = "servico_atendimento")
public class ServicoAtendimentoModel implements Serializable{

	private static final long serialVersionUID = 7188757502036336410L;
	
	private Long id;
	private ServicoModel servico;
	private Integer quantidade;
	private Double val_servico;
	private AtendimentoModel atendimento;
	
	public ServicoAtendimentoModel(Long id, ServicoModel servico, Integer quantidade, Double val_servico,
			AtendimentoModel atendimento) {
		this.id = id;
		this.servico = servico;
		this.quantidade = quantidade;
		this.val_servico = val_servico;
		this.atendimento = atendimento;
	}
	public ServicoAtendimentoModel(ServicoModel servico, Integer quantidade, Double val_servico, 
			AtendimentoModel atendimento) {
		this.servico = servico;
		this.quantidade = quantidade;
		this.val_servico = val_servico;
		this.atendimento = atendimento;
	}
	
	public ServicoAtendimentoModel() {
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
	@JoinColumn(name = "id_servico", nullable = false)
	public ServicoModel getServico() {
		return servico;
	}
	public void setServico(ServicoModel servico) {
		this.servico = servico;
	}
	
	@Column(nullable = false)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(nullable = false)
	public Double getVal_servico() {
		return val_servico;
	}
	public void setVal_servico(Double val_servico) {
		this.val_servico = val_servico;
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
		return "ServicoAtendimentoModel [id=" + id + ", servico=" + servico + ", quantidade=" + quantidade
				+ ", val_servico=" + val_servico + ",  atendimento=" + atendimento
				+ "]";
	}
	
}
