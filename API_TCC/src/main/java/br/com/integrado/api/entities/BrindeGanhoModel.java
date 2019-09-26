package br.com.integrado.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.integrado.api.enums.TipoBrinde;

@Entity
@Table(name = "brinde_ganho")
public class BrindeGanhoModel {

	private Long id;
	private BrindeModel brinde;
	private TipoBrinde tipoBrinde;
	
	public BrindeGanhoModel() {
	}
			
	public BrindeGanhoModel (BrindeModel brinde, TipoBrinde tipoBrinde) {
		this.brinde = brinde;
		this.tipoBrinde = tipoBrinde;
	}	
	
	public BrindeGanhoModel(Long id, BrindeModel brinde, TipoBrinde tipoBrinde) {
		this.id = id;
		this.brinde = brinde;
		this.tipoBrinde = tipoBrinde;
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
	
	
	
}
