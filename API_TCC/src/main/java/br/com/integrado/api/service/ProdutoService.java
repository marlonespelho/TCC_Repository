package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public ProdutoModel salvar(ProdutoModel produto) {
		return this.produtoRepository.save(produto);
	}
	
	public void deletar(Long id) {
		this.produtoRepository.deleteById(id);
	}
	
	public Optional<ProdutoModel> buscarPorId(Long id){
		return this.produtoRepository.findById(id);
	}
	
	public Page<ProdutoModel> buscarTodos(PageRequest pageRequest){
		return this.produtoRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<ProdutoModel> buscarInativos(PageRequest pageRequest){
		return this.produtoRepository.findByInAtivo(pageRequest, false);
	}
	
	public Page<ProdutoModel> buscarPorDescricao(PageRequest pageRequest, String descricao){
		return this.produtoRepository.findByDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
	
	public Page<ProdutoModel> buscarPorDescricaoMarca(PageRequest pageRequest, String descricao){
		return this.produtoRepository.findByMarcaDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
	
	public Optional<ProdutoModel> buscarPorCodigo(String codigo){
		return this.produtoRepository.findByCodBarrasContainingIgnoreCase(codigo);
	}
	
	public Optional<ProdutoModel> buscarPorDescricaoEMarca(String descricao,Long idMarca){
		return this.produtoRepository.findByDescricaoContainingIgnoreCaseAndMarcaId(descricao, idMarca);
	}
	
}
