package br.com.integrado.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.ProdutoAtendimentoModel;
import br.com.integrado.api.repositories.ProdutoAtendimentoRepository;

@Service
public class ProdutoAtendimentoService {
	
	@Autowired
	private ProdutoAtendimentoRepository produtoAtendimentoRepository;
	
	public ProdutoAtendimentoModel salvar(ProdutoAtendimentoModel produto) {
		return this.produtoAtendimentoRepository.save(produto);
	}
	
	public List<ProdutoAtendimentoModel> salvar(List<ProdutoAtendimentoModel> produtos) {
		List<ProdutoAtendimentoModel> produtosAtendimento = new ArrayList<ProdutoAtendimentoModel>();
		for (ProdutoAtendimentoModel produtoAtendimento : produtos) {
			produtosAtendimento.add(this.produtoAtendimentoRepository.save(produtoAtendimento)); 
		}
		return produtosAtendimento;
	}
	
	public void deletar(ProdutoAtendimentoModel produto) {
		this.produtoAtendimentoRepository.delete(produto);
	}
	
	public void deletar(Long id) {
		this.produtoAtendimentoRepository.deleteById(id);
	}
	
	public Optional<ProdutoAtendimentoModel> buscarPorId(Long id) {
		return this.produtoAtendimentoRepository.findById(id);
	}
	
	public Page<ProdutoAtendimentoModel> buscarPorAtendimentoId(PageRequest pageRequest, Long id){
		return this.produtoAtendimentoRepository.findByAtendimentoId(pageRequest, id);
	}
	
}
