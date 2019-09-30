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
		if (produtos != null) {
			if (produtos.get(0).getAtendimento() != null) {
				this.deletarProdutosForaDaLista(produtos);
			}
			List<ProdutoAtendimentoModel> produtosAtendimento = new ArrayList<ProdutoAtendimentoModel>();
			for (ProdutoAtendimentoModel produtoAtendimento : produtos) {
				produtosAtendimento.add(this.produtoAtendimentoRepository.save(produtoAtendimento)); 
			}
			return produtosAtendimento;
		}
		return null;
	}
	
	private void deletarProdutosForaDaLista(List<ProdutoAtendimentoModel> produtos) {
		List<ProdutoAtendimentoModel> produtosSalvos = this.buscarPorAtendimentoId(produtos.get(0).getId());
		for (ProdutoAtendimentoModel produtoSalvo : produtosSalvos) {
			Boolean validador = false;
			int cont = 1;
			for (ProdutoAtendimentoModel produto : produtos) {
				if (produto.getId() == produtoSalvo.getId()) {
					validador = true;
					break;
				}
				if (cont == produtosSalvos.size() && !validador) {
					this.deletar(produtoSalvo);
					break;
				}
				cont++;
			}
		}
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
	
	public List<ProdutoAtendimentoModel> buscarPorAtendimentoId(Long id){
		return this.produtoAtendimentoRepository.findByAtendimentoId(id);
	}
	
}
