package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.BrindeConfigModel;
import br.com.integrado.api.repositories.BrindeConfigRepository;

@Service
public class BrindeConfigService {
	@Autowired
	private BrindeConfigRepository brindeConfigRepository;
	
	public BrindeConfigModel salvar(BrindeConfigModel brindeConfig) {
		return this.brindeConfigRepository.save(brindeConfig);
	}
	
	public void deletar(Long id) {
		this.brindeConfigRepository.deleteById(id);
	}
	
	public void deletar(BrindeConfigModel brindeConfig) {
		this.brindeConfigRepository.delete(brindeConfig);
	}
	
	public Optional<BrindeConfigModel> buscarPorId(Long id){
		return this.brindeConfigRepository.findById(id);
	}
	
	public Page<BrindeConfigModel> buscarTodos(PageRequest pageRequest){
		return this.brindeConfigRepository.findAll(pageRequest);
	}
	
	
}
