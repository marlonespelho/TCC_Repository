package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.MarcaModel;
import br.com.integrado.api.repositories.MarcaRepository;

@Service
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	public MarcaModel salvar(MarcaModel marca) {
		return this.marcaRepository.save(marca);
	}
	
	public void deletar(Long id) {
		this.marcaRepository.deleteById(id);
	}
	
	public Optional<MarcaModel> buscarPorId(Long id){
		return this.marcaRepository.findById(id);
	}
	
	public Page<MarcaModel> buscarPorDescricao(PageRequest pageRequest, String descricao){
		return this.marcaRepository.findByDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
	
	public Page<MarcaModel> buscarTodos(PageRequest pageRequest){
		return this.marcaRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<MarcaModel> buscarInativos(PageRequest pageRequest){
		return this.marcaRepository.findByInAtivo(pageRequest, false);
	}
	
}
