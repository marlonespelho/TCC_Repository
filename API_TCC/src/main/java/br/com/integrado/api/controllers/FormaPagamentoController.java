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
import br.com.integrado.api.dtos.FormaPagamentoDTO;
import br.com.integrado.api.entities.FormaPagamentoModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.FormaPagamentoService;

@RestController
@RequestMapping("api/forma-pagamento")
@CrossOrigin(origins = "*")
public class FormaPagamentoController {
	@Autowired
	private FormaPagamentoService fmPagamentoService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<Response<FormaPagamentoDTO>> cadastrar(@Valid @RequestBody FormaPagamentoDTO fmPagamentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<FormaPagamentoDTO> response = new Response<FormaPagamentoDTO>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		FormaPagamentoModel fmPagamento = this.converterDtoParaFmPagamento(fmPagamentoDto);
		this.fmPagamentoService.salvar(fmPagamento);
		response.setData(this.converterFmPagamentoParaDto(fmPagamento));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<FormaPagamentoDTO>> alterar(@Valid @RequestBody FormaPagamentoDTO fmPagamentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<FormaPagamentoDTO> response = new Response<FormaPagamentoDTO>();
		if (!this.fmPagamentoService.buscarPorId(fmPagamentoDto.getId()).isPresent()) {
			response.getErrors().add("Forma de Pagamento não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		FormaPagamentoModel fmPagamento = this.converterDtoParaFmPagamento(fmPagamentoDto);
		fmPagamento.setId(fmPagamentoDto.getId());
		this.fmPagamentoService.salvar(fmPagamento);
		response.setData(this.converterFmPagamentoParaDto(fmPagamento));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/id/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.fmPagamentoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Forma de Pagamento não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		this.fmPagamentoService.deletar(id);
		response.setData("Forma de Pagamento removida da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Response<FormaPagamentoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<FormaPagamentoDTO> response = new Response<FormaPagamentoDTO>();
		Optional<FormaPagamentoModel> fmPagamento = this.fmPagamentoService.buscarPorId(id);
		if (!fmPagamento.isPresent()) {
			response.getErrors().add("Forma de Pagamento não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterFmPagamentoParaDto(fmPagamento.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<FormaPagamentoDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FormaPagamentoDTO>> response = new Response<Page<FormaPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FormaPagamentoModel> fmsPagamento = this.fmPagamentoService.buscarTodos(pageRequest);
		Page<FormaPagamentoDTO> fmsPagamentoDto = fmsPagamento.map(fmPagamento -> this.converterFmPagamentoParaDto(fmPagamento));
		response.setData(fmsPagamentoDto);		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<FormaPagamentoDTO>>> buscarInativos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FormaPagamentoDTO>> response = new Response<Page<FormaPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FormaPagamentoModel> fmsPagamento = this.fmPagamentoService.buscarInativos(pageRequest);
		Page<FormaPagamentoDTO> fmsPagamentoDto = fmsPagamento.map(fmPagamento -> this.converterFmPagamentoParaDto(fmPagamento));
		response.setData(fmsPagamentoDto);		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descricao/{descricao}")
	public ResponseEntity<Response<Page<FormaPagamentoDTO>>> buscarPorDescricao(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FormaPagamentoDTO>> response = new Response<Page<FormaPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FormaPagamentoModel> fmsPagamento = this.fmPagamentoService.buscarPorDescricao(pageRequest, descricao);
		Page<FormaPagamentoDTO> fmsPagamentoDto = fmsPagamento.map(fmPagamento -> this.converterFmPagamentoParaDto(fmPagamento));
		response.setData(fmsPagamentoDto);		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/qntParcelas/{qntParcelas}")
	public ResponseEntity<Response<Page<FormaPagamentoDTO>>> buscarPorQntParcelas(
			@PathVariable("qntParcelas") Integer qntParcelas,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FormaPagamentoDTO>> response = new Response<Page<FormaPagamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FormaPagamentoModel> fmsPagamento = this.fmPagamentoService.buscarPorqntParcelas(pageRequest, qntParcelas);
		Page<FormaPagamentoDTO> fmsPagamentoDto = fmsPagamento.map(fmPagamento -> this.converterFmPagamentoParaDto(fmPagamento));
		response.setData(fmsPagamentoDto);		
		return ResponseEntity.ok(response);
	}
	
	private FormaPagamentoDTO converterFmPagamentoParaDto(FormaPagamentoModel fmPagamento) {
		return new FormaPagamentoDTO(fmPagamento.getId(), fmPagamento.getDescricao(),
				fmPagamento.getQntParcelas(),fmPagamento.getQntDias(), fmPagamento.getEntrada(),
				fmPagamento.getInAtivo());
	}

	private FormaPagamentoModel converterDtoParaFmPagamento(@Valid FormaPagamentoDTO fmPagamentoDto) {
		return new FormaPagamentoModel(fmPagamentoDto.getDescricao(), fmPagamentoDto.getQntParcelas(), 
				fmPagamentoDto.getQntDias(), fmPagamentoDto.getEntrada(), fmPagamentoDto.getInAtivo());
	}
	
	
}
