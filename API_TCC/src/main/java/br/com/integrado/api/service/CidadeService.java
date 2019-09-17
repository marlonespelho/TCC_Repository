package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.CidadeModel;
import br.com.integrado.api.repositories.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public CidadeModel salvar(CidadeModel cidade) {
		return this.cidadeRepository.save(cidade);
	}
	
	public void deletar(Integer id) {
		this.cidadeRepository.deleteById(id);
	}
	
	public Page<CidadeModel> buscarTodos(PageRequest pageRequest){
		return this.cidadeRepository.findAll(pageRequest);
	}
	
	public Page<CidadeModel> buscarPorNome(PageRequest pageRequest, String nome){
		return this.cidadeRepository.findByNomeContainingIgnoreCase(pageRequest, nome);
	}
	
	public Optional<CidadeModel> buscarPorId(Integer id){
		return this.cidadeRepository.findById(id);
	}
	
	public Page<CidadeModel> buscarPorUFIdENome(PageRequest pageRequest, Integer idUF, String nome){
		return this.cidadeRepository.findByUfIdAndNomeContainingIgnoreCase(pageRequest, idUF, nome);
	}
	
	public Page<CidadeModel> buscarPorIDUF(Integer id, PageRequest pageRequest){
		return this.cidadeRepository.findByUfId(id, pageRequest);
	}
	
	public Page<CidadeModel> buscarPorSiglaUF(String sigla, PageRequest pageRequest){
		return this.cidadeRepository.findByUfSiglaUF(sigla, pageRequest);
	}

	public Page<CidadeModel> buscarPorDescUF(String desc, PageRequest pageRequest){
		return this.cidadeRepository.findByUfDescUF(desc, pageRequest);
	}
	
}
