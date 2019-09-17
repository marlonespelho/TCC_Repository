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

import br.com.integrado.api.dtos.UFDTO;
import br.com.integrado.api.entities.UFModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.UFService;

@RestController
@RequestMapping("api/uf")
@CrossOrigin(origins = "*")
public class UFController {

	@Autowired(required = true)
	private UFService ufService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<UFDTO>> cadastrar(@Valid @RequestBody UFDTO ufDTO,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<UFDTO> response = new Response<UFDTO>();
		this.verificarUFCadastrado(ufDTO, result);
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		UFModel ufModel = this.converterDTOParaUF(ufDTO);
		this.ufService.salvar(ufModel);
		response.setData(this.converterUFParaDTO(ufModel));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<UFDTO>> alterar(@Valid @RequestBody UFDTO ufDTO, 
			BindingResult result) throws NoSuchAlgorithmException{
		Response<UFDTO> response = new Response<UFDTO>();
		UFModel uf = this.converterDTOParaUF(ufDTO);
		uf.setId(ufDTO.getId());
		this.ufService.salvar(uf);
		response.setData(this.converterUFParaDTO(uf));
		return ResponseEntity.ok(response);
	} 
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Integer id){
		Response<String> response = new Response<String>();
		if (!this.ufService.buscarPorID(id).isPresent()) {
			response.getErrors().add("UF não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.ufService.deletar(id);
		response.setData("UF removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UFDTO>> buscarPorId(@PathVariable("id") Integer id){
		Response<UFDTO> response = new Response<UFDTO>();
		Optional<UFModel> uf = this.ufService.buscarPorID(id);
		if (!uf.isPresent()) {
			response.getErrors().add("UF não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterUFParaDTO(uf.get()));
		return ResponseEntity.ok(response);
	}
	
	
	public ResponseEntity<Response<Page<UFDTO>>> buscarTodos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<UFDTO>> response = new Response<Page<UFDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<UFModel> ufs = this.ufService.buscarTodos(pageRequest);
		Page<UFDTO> ufsDTO = ufs.map(uf -> this.converterUFParaDTO(uf));
		response.setData(ufsDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/sigla/{sigla}")
	public ResponseEntity<Response<UFDTO>> buscarPorSigla(
		@PathVariable("sigla") String sigla){
		Response<UFDTO> response = new Response<UFDTO>();
		Optional<UFModel> uf = this.ufService.buscarPorSigla(sigla);
		if (!uf.isPresent()) {
			response.getErrors().add("UF não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterUFParaDTO(uf.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/descricao/{desc}")
	public ResponseEntity<Response<Page<UFDTO>>> buscarDescricao(
			@PathVariable("desc") String desc,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<UFDTO>> response = new Response<Page<UFDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<UFModel> ufs = this.ufService.buscarPorDescricaoUF(pageRequest, desc);
		Page<UFDTO> ufsDTO = ufs.map(uf -> this.converterUFParaDTO(uf));
		response.setData(ufsDTO);
		return ResponseEntity.ok(response);
	}

	private UFDTO converterUFParaDTO(UFModel ufModel) {
		return new UFDTO(ufModel.getId(), ufModel.getDescUF(), ufModel.getSiglaUF());
	}

	private UFModel converterDTOParaUF(@Valid UFDTO ufDTO) {
		return new UFModel(ufDTO.getDescUF(), ufDTO.getSiglaUF());
	}

	private void verificarUFCadastrado(@Valid UFDTO ufDTO, BindingResult result) {
		if (this.ufService.buscarPorSigla(ufDTO.getSiglaUF()).isPresent()) {
			result.addError(new ObjectError("UF", "Sigla UF já cadastrada"));
		}
	}
}
