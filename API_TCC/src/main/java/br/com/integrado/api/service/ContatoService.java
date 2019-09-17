package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.ContatoModel;
import br.com.integrado.api.repositories.ContatoRepository;

@Service
public class ContatoService {
	@Autowired(required = true)
	private ContatoRepository contatoRepository;
	
	public ContatoModel salvar(ContatoModel contato) {
		return this.contatoRepository.save(contato);
	}
	
	public void deletar(Long id) {
		this.contatoRepository.deleteById(id);
	}
	
	public Optional<ContatoModel> buscarPorId(Long id, Long pessoaId){
		return this.contatoRepository.findByIdAndPessoaId(pessoaId, id);
	}
	
	public Optional<ContatoModel> buscarPorId(Long id){
		return this.contatoRepository.findById(id);
	}
	
	public Page<ContatoModel> buscarPorIdPessoa(PageRequest pageRequest, Long id){
		return this.contatoRepository.findByPessoaId(id, pageRequest);
	}
	
	public Page<ContatoModel> buscarPorNumTelefone(PageRequest pageRequest, Long id, String numero){
		return this.contatoRepository.findByPessoaIdAndNumTelefoneContaining(id, numero, pageRequest);
	}
	
	public Page<ContatoModel> buscarPorEmail(PageRequest pageRequest, Long id, String email){
		return this.contatoRepository.findByPessoaIdAndEmailContaining(id, email, pageRequest);
	}
	
}
