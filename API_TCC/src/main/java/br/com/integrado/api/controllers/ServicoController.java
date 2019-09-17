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

import br.com.integrado.api.dtos.ServicoDTO;
import br.com.integrado.api.entities.ServicoModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.ServicoService;

@RestController
@RequestMapping("api/servico")
@CrossOrigin(origins = "*")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<ServicoDTO>> cadastrar(@Valid @RequestBody ServicoDTO servicoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ServicoDTO> response = new Response<ServicoDTO>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		ServicoModel servico = this.converterDtoParaServico(servicoDto);
		this.servicoService.salvar(servico);
		response.setData(this.converterServicoParaDto(servico));
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Response<ServicoDTO>> alterar(@Valid @RequestBody ServicoDTO servicoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ServicoDTO> response = new Response<ServicoDTO>();
		this.verificarServicoCadastrado(result,servicoDto);
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		ServicoModel servico = this.converterDtoParaServico(servicoDto);
		servico.setId(servicoDto.getId());
		this.servicoService.salvar(servico);
		response.setData(this.converterServicoParaDto(servico));
		return ResponseEntity.ok(response);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.servicoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Servico não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.servicoService.deletar(id);
		response.setData("Servico removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ServicoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<ServicoDTO> response = new Response<ServicoDTO>();
		Optional<ServicoModel> servico = this.servicoService.buscarPorId(id);
		if (!servico.isPresent()) {
			response.getErrors().add("Serviço não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterServicoParaDto(servico.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<ServicoDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ServicoDTO>> response = new Response<Page<ServicoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ServicoModel> servicos = this.servicoService.buscarTodos(pageRequest);
		Page<ServicoDTO> servicosDto = servicos.map(servico -> this.converterServicoParaDto(servico));
		response.setData(servicosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<ServicoDTO>>> buscarInativos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ServicoDTO>> response = new Response<Page<ServicoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ServicoModel> servicos = this.servicoService.buscarInativos(pageRequest);
		Page<ServicoDTO> servicosDto = servicos.map(servico -> this.converterServicoParaDto(servico));
		response.setData(servicosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descricao/{descricao}")
	public ResponseEntity<Response<Page<ServicoDTO>>> buscarPorDescricao(
			@PathVariable("descricao") String descricao,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ServicoDTO>> response = new Response<Page<ServicoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ServicoModel> servicos = this.servicoService.buscarPorDescricao(pageRequest, descricao);
		Page<ServicoDTO> servicosDto = servicos.map(servico -> this.converterServicoParaDto(servico));
		response.setData(servicosDto);
		return ResponseEntity.ok(response);
	}
	
	private void verificarServicoCadastrado(BindingResult result, @Valid ServicoDTO servicoDto) {
		if (!this.servicoService.buscarPorId(servicoDto.getId()).isPresent()) {
			result.addError(new ObjectError("Servico", "Servico não encontrado"));
		}
		
	}

	private ServicoDTO converterServicoParaDto(ServicoModel servico) {
		return new ServicoDTO(servico.getId(), servico.getDescricao(),
				servico.getValor(), servico.getInAtivo());
	}

	private ServicoModel converterDtoParaServico(@Valid ServicoDTO servicoDto) {
		return new ServicoModel(servicoDto.getDescricao(), servicoDto.getValor(), 
				servicoDto.getInAtivo());
	}
	
	
}
