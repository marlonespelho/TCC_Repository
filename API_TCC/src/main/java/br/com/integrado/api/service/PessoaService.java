package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.PessoaModel;
import br.com.integrado.api.repositories.PessoaRepository;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Optional<PessoaModel> buscarPessoaPorId(Long id){
		return this.pessoaRepository.findById(id);
	}
}
