package br.com.integrado.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.MovEstoqueModel;
import br.com.integrado.api.entities.ProdutoAtendimentoModel;
import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.enums.TipoMovimentacao;
import br.com.integrado.api.repositories.MovEstoqueRepository;

@Service
public class MovEstoqueService {
	@Autowired
	private MovEstoqueRepository movEstoqueRepository;
	@Autowired
	private ProdutoService produtoService;
	
	public List<MovEstoqueModel> movimentarAtendimento(List<ProdutoAtendimentoModel> produtosAtendimento) {
		if (produtosAtendimento != null) {
			List<MovEstoqueModel> movimentacoes = new ArrayList<MovEstoqueModel>();
			for (ProdutoAtendimentoModel produtoAtendimento : produtosAtendimento) {
				MovEstoqueModel movEstoque = new MovEstoqueModel(new Date(), "Baixa através do atendimento n° " + produtoAtendimento.getAtendimento().getId(), 
						produtoAtendimento, TipoMovimentacao.ATENDIMENTO);
				movimentacoes.add(this.salvar(movEstoque));
				this.movimentarEstoque(movEstoque);
			}
			return movimentacoes;
		}
		return null;
	}
	
	public void movimentarEstoque(MovEstoqueModel movEstoque) {
		if (movEstoque.getProdutoAtendimento() != null) {
			ProdutoModel produto = movEstoque.getProdutoAtendimento().getProduto();
			produto.setQuantidade(produto.getQuantidade() - movEstoque.getProdutoAtendimento().getQuantidade());
			this.produtoService.salvar(produto);
		}
		else {
			ProdutoModel produto = movEstoque.getProduto();
			produto.setQuantidade(produto.getQuantidade() - movEstoque.getQuantidade());
			this.produtoService.salvar(produto);
		}
	}
	
	public MovEstoqueModel salvar(MovEstoqueModel movEstoque) {
		this.movimentarEstoque(movEstoque);
		return this.movEstoqueRepository.save(movEstoque);
	}
	
}
