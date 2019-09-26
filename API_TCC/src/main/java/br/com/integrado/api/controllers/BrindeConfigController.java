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
import br.com.integrado.api.dtos.BrindeConfigDTO;
import br.com.integrado.api.entities.BrindeConfigModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.BrindeConfigService;

@RestController
@RequestMapping("api/brinde-config")
@CrossOrigin(origins = "*")
public class BrindeConfigController {
	
	@Autowired
	private BrindeConfigService brindeConfigService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
		
	@PostMapping
	public ResponseEntity<Response<BrindeConfigDTO>>cadastrar(@Valid @RequestBody BrindeConfigDTO brindeConfigDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<BrindeConfigDTO> response = new Response<BrindeConfigDTO>();
		
		BrindeConfigModel brindeConfig = this.converterDTOEmBrindeConfig(brindeConfigDto);
		this.brindeConfigService.salvar(brindeConfig);
		response.setData(this.converterBrindeConfigEmDTO(brindeConfig));		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<BrindeConfigDTO>> alterar(@Valid @RequestBody BrindeConfigDTO brindeConfigDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<BrindeConfigDTO> response = new Response<BrindeConfigDTO>();
		if (!this.brindeConfigService.buscarPorId(brindeConfigDto.getId()).isPresent()) {
			response.getErrors().add("Configuração não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		BrindeConfigModel brindeConfig = this.converterDTOEmBrindeConfig(brindeConfigDto);
		brindeConfig.setId(brindeConfigDto.getId());
		this.brindeConfigService.salvar(brindeConfig);
		response.setData(this.converterBrindeConfigEmDTO(brindeConfig));		
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.brindeConfigService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Configuração não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		this.brindeConfigService.deletar(id);
		response.setData("Configuração removida da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<BrindeConfigDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<BrindeConfigDTO> response = new Response<BrindeConfigDTO>();
		Optional<BrindeConfigModel> brindeConfig = this.brindeConfigService.buscarPorId(id);
		if (!brindeConfig.isPresent()) {
			response.getErrors().add("Configuração não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterBrindeConfigEmDTO(brindeConfig.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<BrindeConfigDTO>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<BrindeConfigDTO>> response = new Response<Page<BrindeConfigDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<BrindeConfigModel> brindesConfig = this.brindeConfigService.buscarTodos(pageRequest);
		Page<BrindeConfigDTO> brindesConfigDto = brindesConfig.map(brindeConfig -> this.converterBrindeConfigEmDTO(brindeConfig));
		response.setData(brindesConfigDto);
		return ResponseEntity.ok(response);
	}
	
	private BrindeConfigDTO converterBrindeConfigEmDTO(BrindeConfigModel brindeConfig) {
		return new BrindeConfigDTO(brindeConfig.getId(), brindeConfig.getQuantContador(), 
				brindeConfig.getBrindeAniversario(), brindeConfig.getBrindeFidelidade());
	}

	private BrindeConfigModel converterDTOEmBrindeConfig(@Valid BrindeConfigDTO brindeConfigDto) {
		return new BrindeConfigModel(brindeConfigDto.getQuantContador(), brindeConfigDto.getBrindeAniversario(), brindeConfigDto.getBrindeFidelidade());
	}
}
