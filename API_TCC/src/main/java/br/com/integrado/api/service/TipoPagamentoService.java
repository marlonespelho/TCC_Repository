package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.TipoPagamentoModel;
import br.com.integrado.api.repositories.TipoPagamentoRepository;

@Service
public class TipoPagamentoService {
	@Autowired
	private TipoPagamentoRepository tpPagamentoRepository;
	
	public void deletar(Long id) {
		this.tpPagamentoRepository.deleteById(id);
	}
	
	public TipoPagamentoModel salvar(TipoPagamentoModel tpPagemento) {
		return this.tpPagamentoRepository.save(tpPagemento);
	}
	
	public Optional<TipoPagamentoModel> buscarPorId(Long id){
		return this.tpPagamentoRepository.findById(id);
	}
	
	public Page<TipoPagamentoModel> buscarTodos(PageRequest pageRequest){
		return this.tpPagamentoRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<TipoPagamentoModel> buscarInativos(PageRequest pageRequest){
		return this.tpPagamentoRepository.findByInAtivo(pageRequest, false);
	}
	
	public Page<TipoPagamentoModel> buscarPorDescricao(PageRequest pageRequest, String descricao){
		return this.tpPagamentoRepository.findByDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
}
