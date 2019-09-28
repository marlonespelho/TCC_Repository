package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.integrado.api.dtos.AtendimentoDTO;
import br.com.integrado.api.dtos.ProdutoDTO;
import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.MarcaService;
import br.com.integrado.api.service.ProdutoService;

@RestController
@RequestMapping("api/produto")
@CrossOrigin(origins = "*")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private MarcaService marcaService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO produtoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		this.verificarProdutoCadastrado(produtoDto, result);
		this.verificarMarcaCadastrada(produtoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		ProdutoModel produto = this.converterDtoParaProduto(produtoDto);
		this.produtoService.salvar(produto);
		response.setData(this.converterProdutoParaDto(produto));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<ProdutoDTO>> alterar(@Valid @RequestBody ProdutoDTO produtoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		this.verificarMarcaCadastrada(produtoDto, result);
		this.verificarProdutoCadastrado(produtoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		ProdutoModel produto = this.converterDtoParaProduto(produtoDto);
		produto.setId(produtoDto.getId());
		this.produtoService.salvar(produto);
		response.setData(this.converterProdutoParaDto(produto));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.produtoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Produto não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.produtoService.deletar(id);
		response.setData("Produto removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Response<ProdutoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		Optional<ProdutoModel> produto = this.produtoService.buscarPorId(id);
		if (!produto.isPresent()) {
			response.getErrors().add("Produto não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterProdutoParaDto(produto.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<ProdutoDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ProdutoDTO>> response = new Response<Page<ProdutoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ProdutoModel> produtos = this.produtoService.buscarTodos(pageRequest);
		Page<ProdutoDTO> produtosDto = produtos.map(produto -> this.converterProdutoParaDto(produto));
		response.setData(produtosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<ProdutoDTO>>> buscarInativos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ProdutoDTO>> response = new Response<Page<ProdutoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ProdutoModel> produtos = this.produtoService.buscarInativos(pageRequest);
		Page<ProdutoDTO> produtosDto = produtos.map(produto -> this.converterProdutoParaDto(produto));
		response.setData(produtosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descricao/{descricao}")
	public ResponseEntity<Response<Page<ProdutoDTO>>> buscarPorDescricao(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ProdutoDTO>> response = new Response<Page<ProdutoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ProdutoModel> produtos = this.produtoService.buscarPorDescricao(pageRequest, descricao);
		Page<ProdutoDTO> produtosDto = produtos.map(produto -> this.converterProdutoParaDto(produto));
		response.setData(produtosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/marcadesc/{descricao}")
	public ResponseEntity<Response<Page<ProdutoDTO>>> buscarPorDescricaoMarca(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ProdutoDTO>> response = new Response<Page<ProdutoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ProdutoModel> produtos = this.produtoService.buscarPorDescricaoMarca(pageRequest, descricao);
		Page<ProdutoDTO> produtosDto = produtos.map(produto -> this.converterProdutoParaDto(produto));
		response.setData(produtosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/codbarras/{codigo}")
	public ResponseEntity<Response<ProdutoDTO>> buscarPorCodigoBarras(
			@PathVariable("codigo") String codigo){
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		Optional<ProdutoModel> produto = this.produtoService.buscarPorCodigo(codigo);
		if (!produto.isPresent()) {
			response.getErrors().add("Produto não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterProdutoParaDto(produto.get()));
		return ResponseEntity.ok(response);
	}
	
	private ProdutoDTO converterProdutoParaDto(ProdutoModel produto) {
		return new ProdutoDTO(produto.getId(), produto.getDescricao(), produto.getQuantidade(),
				produto.getQuantMin(), produto.getValVendido(), produto.getMarca().getId(),
				produto.getCodBarras(), produto.getInAtivo());
	}

	private ProdutoModel converterDtoParaProduto(@Valid ProdutoDTO produtoDto) {
		return new ProdutoModel(produtoDto.getDescricao(), produtoDto.getQuantidade(), 
				produtoDto.getQuantMin(), produtoDto.getValVendido(), 
				this.marcaService.buscarPorId(produtoDto.getIdMarca()).get(), produtoDto.getCodBarras(),
				produtoDto.getInAtivo());
	}

	private void verificarMarcaCadastrada(@Valid ProdutoDTO produtoDto, BindingResult result) {
		if (!this.marcaService.buscarPorId(produtoDto.getIdMarca()).isPresent()) {
			result.addError(new ObjectError("Marca", "Marca não encontrada"));
		}
	}

	private void verificarProdutoCadastrado(@Valid ProdutoDTO produtoDto, BindingResult result) {
		Optional<ProdutoModel> produto = this.produtoService.buscarPorDescricaoEMarca(produtoDto.getDescricao(), produtoDto.getIdMarca());
		if (produto.isPresent() && produto.get().getId() != produtoDto.getId()) {
			result.addError(new ObjectError("Produto", "Já existe um produto com a mesma descrição e marca informada"));
		}
		if (this.produtoService.buscarPorCodigo(produtoDto.getCodBarras()).isPresent()) {
			result.addError(new ObjectError("Produto", "O código de barras deve ser único"));
		}
		if (produtoDto.getId() != null) {
			produto = this.produtoService.buscarPorCodigo(produtoDto.getCodBarras());
			if (produto.isPresent() && produto.get().getId() != produtoDto.getId() && produto.get().getCodBarras().equalsIgnoreCase(produtoDto.getCodBarras())) {
				
			}
		}
	}
	
}
