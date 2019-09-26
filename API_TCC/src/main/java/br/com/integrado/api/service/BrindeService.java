package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.repositories.BrindeRepository;

@Service
public class BrindeService {

	private BrindeRepository brindeRepository;
	
	public BrindeModel salvar(BrindeModel brinde) {
		return this.brindeRepository.save(brinde);
	}
	
	public void delete(Long id) {
		this.brindeRepository.deleteById(id);
	}
	
	public Optional<BrindeModel> buscarPorId(Long id){
		return this.brindeRepository.findById(id);
	}
	
	public Page<BrindeModel> buscarTodos(PageRequest pageRequest){
		return this.brindeRepository.findAll(pageRequest);
	}
	
	public Page<BrindeModel> buscarPorCliente(PageRequest pageRequest, Long id){
		return this.brindeRepository.findByClienteId(pageRequest, id);
	}
	
	
	
}
