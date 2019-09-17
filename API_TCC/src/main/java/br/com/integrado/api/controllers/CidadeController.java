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

import br.com.integrado.api.dtos.CidadeDTO;
import br.com.integrado.api.entities.CidadeModel;
import br.com.integrado.api.entities.UFModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.CidadeService;
import br.com.integrado.api.service.UFService;

@RestController
@RequestMapping("api/cidade")
@CrossOrigin(origins = "*")
public class CidadeController {
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private UFService ufService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<CidadeDTO>> cadastrar(@Valid @RequestBody CidadeDTO cidadeDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<CidadeDTO> response = new Response<CidadeDTO>();
		if (!this.ufService.buscarPorID(cidadeDto.getIdUF()).isPresent()) {
			response.getErrors().add("UF não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		CidadeModel cidade = this.converterDtoParaCidade(cidadeDto);
		this.cidadeService.salvar(cidade);
		response.setData(this.converterCidadeParaDto(cidade));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<CidadeDTO>> alterar(@Valid @RequestBody CidadeDTO cidadeDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<CidadeDTO> response = new Response<CidadeDTO>();
		if (!this.ufService.buscarPorID(cidadeDto.getIdUF()).isPresent()) {
			response.getErrors().add("UF não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		CidadeModel cidade = this.converterDtoParaCidade(cidadeDto);
		cidade.setId(cidadeDto.getId());
		this.cidadeService.salvar(cidade);
		response.setData(this.converterCidadeParaDto(cidade));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Integer id){
		Response<String> response = new Response<String>();
		if (!this.cidadeService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Cidade não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		this.cidadeService.deletar(id);
		response.setData("Cidade removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<CidadeDTO>>buscarPorId(@PathVariable("id") Integer id){
		Response<CidadeDTO> response = new Response<CidadeDTO>();
		Optional<CidadeModel> cidade = this.cidadeService.buscarPorId(id);
		if (!cidade.isPresent()) {
			response.getErrors().add("Cidade não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterCidadeParaDto(cidade.get()));
		return ResponseEntity.ok(response);
	}

	private CidadeDTO converterCidadeParaDto(CidadeModel cidade) {
		return new CidadeDTO(cidade.getId(),cidade.getNome(),cidade.getUf().getId());
	}

	@GetMapping
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarTodos(@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarTodos(pageRequest);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/iduf/{id}")
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarPorIdUF(
			@PathVariable("id") Integer id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarPorIDUF(id, pageRequest);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/iduf/{id}/nome/{nome}")
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarPorIdUFENome(
			@PathVariable("id") Integer id,
			@PathVariable("nome") String nome,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarPorUFIdENome(pageRequest, id, nome);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/siglauf/{sigla}")
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarPorSiglaUF(
			@PathVariable("sigla") String sigla,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarPorSiglaUF(sigla.toUpperCase(), pageRequest);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarPorNome(
			@PathVariable("nome") String nome,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarPorNome(pageRequest, nome);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descuf/{desc}")
	public ResponseEntity<Response<Page<CidadeDTO>>> buscarPorDescUF(
			@PathVariable("desc") String desc,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<CidadeDTO>> response = new Response<Page<CidadeDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<CidadeModel> cidades = this.cidadeService.buscarPorDescUF(desc, pageRequest);
		Page<CidadeDTO> cidadesDTO = cidades.map(cidade -> this.converterCidadeParaDto(cidade));
		response.setData(cidadesDTO);				
		return ResponseEntity.ok(response);
	}
	
	private CidadeModel converterDtoParaCidade(@Valid CidadeDTO cidadeDto) {
		return new CidadeModel(cidadeDto.getNome(), this.ufService.buscarPorID(cidadeDto.getIdUF()).get());
	}
	
}
