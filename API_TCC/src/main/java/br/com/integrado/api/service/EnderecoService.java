package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.EnderecoModel;
import br.com.integrado.api.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public EnderecoModel salvar(EnderecoModel endereco) {
		return this.enderecoRepository.save(endereco);
	}
	
	public void deletar(Long id){
		this.enderecoRepository.deleteById(id);
	}
	
	public Optional<EnderecoModel> buscarPorId(Long id){
		return this.enderecoRepository.findById(id);
	}
	
	public Page<EnderecoModel> buscarPorPessoaId(PageRequest pageRequest, Long idPessoa) {
		return this.enderecoRepository.findByPessoaId(pageRequest, idPessoa);
	}
	
	public Page<EnderecoModel> buscarPorPessoaIdECEP(PageRequest pageRequest, Long idPessoa, String CEP) {
		return this.enderecoRepository.findByPessoaIdAndCep(pageRequest, idPessoa, CEP);
	}
	
	public Page<EnderecoModel> buscarPorPessoaIdENomeCidade(PageRequest pageRequest, Long idPessoa, String nomeCidade) {
		return this.enderecoRepository.findByPessoaIdAndCidadeNome(pageRequest, idPessoa, nomeCidade);
	}
	public Page<EnderecoModel> buscarPorPessoaIdESiglaUF(PageRequest pageRequest, Long idPessoa, String siglaUF) {
		return this.enderecoRepository.findByPessoaIdAndCidadeUfSiglaUF(pageRequest, idPessoa, siglaUF);
	}
}
