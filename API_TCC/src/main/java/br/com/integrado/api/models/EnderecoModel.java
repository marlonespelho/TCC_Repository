package br.com.integrado.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "endereco")
public class EnderecoModel implements Serializable{

	private static final long serialVersionUID = 3239665244376994543L;
	
	private Long id;
	private PessoaModel pessoa;
	private CidadeModel cidade;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String complemento;
	
	public EnderecoModel() {
	}

	public EnderecoModel(Long id, PessoaModel pessoa, CidadeModel cidade, String logradouro, String numero,
			String bairro, String cep, String complemento) {
		this.id = id;
		this.pessoa = pessoa;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.complemento = complemento;
	}

	public EnderecoModel(PessoaModel pessoa, CidadeModel cidade, String logradouro, String numero, String bairro,
			String cep, String complemento) {
		this.pessoa = pessoa;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.complemento = complemento;
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
	@JoinColumn(name = "id_pessoa")
	public PessoaModel getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaModel pessoa) {
		this.pessoa = pessoa;
	}

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	public CidadeModel getCidade() {
		return cidade;
	}
	public void setCidade(CidadeModel cidade) {
		this.cidade = cidade;
	}

	@Column(nullable = false)
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(nullable = false)
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(nullable = false)
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(nullable = true)
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(nullable = true)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "EnderecoModel [id=" + id + ", pessoa=" + pessoa + ", cidade=" + cidade + ", logradouro=" + logradouro
				+ ", numero=" + numero + ", bairro=" + bairro + ", cep=" + cep + ", complemento=" + complemento + "]";
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
		EnderecoModel other = (EnderecoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
