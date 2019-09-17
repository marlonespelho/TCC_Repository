package br.com.integrado.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "brinde")
public class BrindeModel implements Serializable{
	
	private static final long serialVersionUID = -1132037416855882091L;
	
	private Long id;
	private ClienteModel cliente;
	private ServicoModel servico;
	private Integer contadorBrinde;
	private Date dataBrindeAniversario;
	
	public BrindeModel() {

	}
	
	public BrindeModel(ClienteModel cliente, ServicoModel servico, Integer contadorBrinde, Date dataBrindeAniversario) {
		this.cliente = cliente;
		this.servico = servico;
		this.contadorBrinde = contadorBrinde;
		this.dataBrindeAniversario = dataBrindeAniversario;
	}

	
	public BrindeModel(Long id, ClienteModel cliente, ServicoModel servico, Integer contadorBrinde,
			Date dataBrindeAniversario) {
		this.id = id;
		this.cliente = cliente;
		this.servico = servico;
		this.contadorBrinde = contadorBrinde;
		this.dataBrindeAniversario = dataBrindeAniversario;
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
	
	@ManyToOne
	@JoinColumn(name = "id_servico")
	public ServicoModel getServico() {
		return servico;
	}
	public void setServico(ServicoModel servico) {
		this.servico = servico;
	}
	
	@Column(name = "cont_brinde")
	public Integer getContadorBrinde() {
		return contadorBrinde;
	}
	public void setContadorBrinde(Integer contadorBrinde) {
		this.contadorBrinde = contadorBrinde;
	}
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	public Date getDataBrindeAniversario() {
		return dataBrindeAniversario;
	}
	public void setDataBrindeAniversario(Date dataBrindeAniversario) {
		this.dataBrindeAniversario = dataBrindeAniversario;
	}
	
	@Override
	public String toString() {
		return "BrindeModel [id=" + id + ", cliente=" + cliente + ", servico=" + servico + ", contadorBrinde="
				+ contadorBrinde + ", dataBrindeAniversario=" + dataBrindeAniversario + "]";
	}

}
