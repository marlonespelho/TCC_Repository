package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.UFModel;
import br.com.integrado.api.repositories.UFRepository;

@Service
public class UFService {
	@Autowired(required = true)
	UFRepository ufRepository;
	
	public UFModel salvar(UFModel uf) {
		return this.ufRepository.save(uf);
	}
	
	public void deletar(Integer id) {
		this.ufRepository.deleteById(id);
	}
	
	public Optional<UFModel> buscarPorID(Integer id){
		return this.ufRepository.findById(id);
	}
	
	public Optional<UFModel> buscarPorSigla(String sigla){
		return this.ufRepository.findBySiglaUF(sigla);
	}
	
	public Page<UFModel> buscarPorDescricaoUF(PageRequest pageRequest, String desc){
		return this.ufRepository.findByDescUFContainingIgnoreCase(pageRequest, desc);
	}
	
	public Page<UFModel> buscarTodos(PageRequest pageRequest){
		return this.ufRepository.findAll(pageRequest);
	}
}
