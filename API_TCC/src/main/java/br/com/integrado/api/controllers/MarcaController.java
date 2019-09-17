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

import br.com.integrado.api.dtos.MarcaDTO;
import br.com.integrado.api.entities.MarcaModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.MarcaService;

@RestController
@RequestMapping("api/marca")
@CrossOrigin(origins = "*")
public class MarcaController {
	@Autowired
	private MarcaService marcaService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<MarcaDTO>> cadastrar(@Valid @RequestBody MarcaDTO marcaDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<MarcaDTO> response = new Response<MarcaDTO>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		MarcaModel marca = this.converterDTOParaMarca(marcaDto);
		this.marcaService.salvar(marca);
		response.setData(this.converterMarcaParaDTO(marca));
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Response<MarcaDTO>> alterar(@Valid @RequestBody MarcaDTO marcaDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<MarcaDTO> response = new Response<MarcaDTO>();
		this.verificarMarcaCadastrada(marcaDto, result);
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		MarcaModel marca = this.converterDTOParaMarca(marcaDto);
		marca.setId(marcaDto.getId());
		this.marcaService.salvar(marca);
		response.setData(this.converterMarcaParaDTO(marca));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.marcaService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Marca não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		this.marcaService.deletar(id);
		response.setData("Marca removida da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<MarcaDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<MarcaDTO> response = new Response<MarcaDTO>();
		Optional<MarcaModel> marca = this.marcaService.buscarPorId(id);
		if (!marca.isPresent()) {
			response.getErrors().add("Marca não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterMarcaParaDTO(marca.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<MarcaDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<MarcaDTO>> response = new Response<Page<MarcaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<MarcaModel> marcas = this.marcaService.buscarTodos(pageRequest);
		Page<MarcaDTO> marcasDto = marcas.map(marca -> this.converterMarcaParaDTO(marca));
		response.setData(marcasDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<MarcaDTO>>> buscarInativos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<MarcaDTO>> response = new Response<Page<MarcaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<MarcaModel> marcas = this.marcaService.buscarInativos(pageRequest);
		Page<MarcaDTO> marcasDto = marcas.map(marca -> this.converterMarcaParaDTO(marca));
		response.setData(marcasDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descricao/{descricao}")
	public ResponseEntity<Response<Page<MarcaDTO>>> buscarPorDescricao(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<MarcaDTO>> response = new Response<Page<MarcaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<MarcaModel> marcas = this.marcaService.buscarPorDescricao(pageRequest, descricao);
		Page<MarcaDTO> marcasDto = marcas.map(marca -> this.converterMarcaParaDTO(marca));
		response.setData(marcasDto);
		return ResponseEntity.ok(response);
	}
	
	private void verificarMarcaCadastrada(@Valid MarcaDTO marcaDto, BindingResult result) {
		if(!this.marcaService.buscarPorId(marcaDto.getId()).isPresent()) {
			result.addError(new ObjectError("Marca", "Marca não cadastrado"));
		}
	}

	private MarcaDTO converterMarcaParaDTO(MarcaModel marca) {
		return new MarcaDTO(marca.getId(), marca.getDescricao(), marca.getInAtivo());
	}

	private MarcaModel converterDTOParaMarca(@Valid MarcaDTO marcaDto) {
		return new MarcaModel(marcaDto.getDescricao(), marcaDto.getInAtivo());
	}
	

}