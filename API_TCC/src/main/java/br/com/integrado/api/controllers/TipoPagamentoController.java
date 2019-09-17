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

import br.com.integrado.api.dtos.TipoPagamentoDTO;
import br.com.integrado.api.entities.TipoPagamentoModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.TipoPagamentoService;

@RestController
@RequestMapping("api/tipo-pagamento")
@CrossOrigin(origins = "*")
public class TipoPagamentoController {
	@Autowired
	private TipoPagamentoService tpPagamentoService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<TipoPagamentoDTO>> cadastrar(@Valid @RequestBody TipoPagamentoDTO tpPagementoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<TipoPagamentoDTO> response = new Response<TipoPagamentoDTO>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		TipoPagamentoModel tpPagamento = this.converterDtoParaTpPagamento(tpPagementoDto);
		this.tpPagamentoService.salvar(tpPagamento);
		response.setData(this.converterTpPagamentoParaDto(tpPagamento));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<TipoPagamentoDTO>> alterar(@Valid @RequestBody TipoPagamentoDTO tpPagementoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<TipoPagamentoDTO> response = new Response<TipoPagamentoDTO>();
		if (!this.tpPagamentoService.buscarPorId(tpPagementoDto.getId()).isPresent()) {
			response.getErrors().add("Tipo de Pagamento não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		TipoPagamentoModel tpPagamento = this.converterDtoParaTpPagamento(tpPagementoDto);
		tpPagamento.setId(tpPagementoDto.getId());
		this.tpPagamentoService.salvar(tpPagamento);
		response.setData(this.converterTpPagamentoParaDto(tpPagamento));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.tpPagamentoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Tipo de Pagamento não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.tpPagamentoService.deletar(id);
		response.setData("Tipo de Pagamento removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<TipoPagamentoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<TipoPagamentoDTO> response = new Response<TipoPagamentoDTO>();
		Optional<TipoPagamentoModel> tpPagamento = this.tpPagamentoService.buscarPorId(id);
		if (!tpPagamento.isPresent()) {
			response.getErrors().add("Tipo de Pagamento não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterTpPagamentoParaDto(tpPagamento.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<TipoPagamentoDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<TipoPagamentoDTO>> response = new Response<Page<TipoPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<TipoPagamentoModel> tpsPagamento = this.tpPagamentoService.buscarTodos(pageRequest);
		Page<TipoPagamentoDTO> tpsPagamentoDTO = tpsPagamento.map(tpPagamento -> this.converterTpPagamentoParaDto(tpPagamento));
		response.setData(tpsPagamentoDTO);		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<TipoPagamentoDTO>>> buscarInativos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<TipoPagamentoDTO>> response = new Response<Page<TipoPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<TipoPagamentoModel> tpsPagamento = this.tpPagamentoService.buscarInativos(pageRequest);
		Page<TipoPagamentoDTO> tpsPagamentoDTO = tpsPagamento.map(tpPagamento -> this.converterTpPagamentoParaDto(tpPagamento));
		response.setData(tpsPagamentoDTO);		
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/descricao/{descricao}")
	public ResponseEntity<Response<Page<TipoPagamentoDTO>>> buscarPorDescricao(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<TipoPagamentoDTO>> response = new Response<Page<TipoPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<TipoPagamentoModel> tpsPagamento = this.tpPagamentoService.buscarPorDescricao(pageRequest, descricao);
		Page<TipoPagamentoDTO> tpsPagamentoDTO = tpsPagamento.map(tpPagamento -> this.converterTpPagamentoParaDto(tpPagamento));
		response.setData(tpsPagamentoDTO);		
		return ResponseEntity.ok(response);
	}
	
	private TipoPagamentoDTO converterTpPagamentoParaDto(TipoPagamentoModel tpPagamento) {
		return new TipoPagamentoDTO(tpPagamento.getId(), tpPagamento.getDescricao(), tpPagamento.getInAtivo());
	}

	private TipoPagamentoModel converterDtoParaTpPagamento(@Valid TipoPagamentoDTO tpPagementoDto) {
		return new TipoPagamentoModel(tpPagementoDto.getDescricao(), tpPagementoDto.getInAtivo()); 
	}
}
