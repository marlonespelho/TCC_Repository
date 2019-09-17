package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.FormaPagamentoModel;
import br.com.integrado.api.repositories.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	@Autowired
	private FormaPagamentoRepository fmPagamentoRepository;
	
	public FormaPagamentoModel salvar(FormaPagamentoModel fmPagamento) {
		return this.fmPagamentoRepository.save(fmPagamento);
	}
	
	public void deletar(Long id) {
		this.fmPagamentoRepository.deleteById(id);
	}
	
	public Optional<FormaPagamentoModel> buscarPorId(Long id){
		return this.fmPagamentoRepository.findById(id);
	}
	
	public Page<FormaPagamentoModel> buscarTodos(PageRequest pageRequest){
		return this.fmPagamentoRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<FormaPagamentoModel> buscarInativos(PageRequest pageRequest){
		return this.fmPagamentoRepository.findByInAtivo(pageRequest, false);
	}
	
	public Page<FormaPagamentoModel> buscarPorDescricao(PageRequest pageRequest, String descricao){
		return this.fmPagamentoRepository.findByDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
	
	public Page<FormaPagamentoModel> buscarPorqntParcelas(PageRequest pageRequest, Integer qntParcelas){
		return this.fmPagamentoRepository.findByQntParcelas(pageRequest, qntParcelas);
	}
	
	
}
