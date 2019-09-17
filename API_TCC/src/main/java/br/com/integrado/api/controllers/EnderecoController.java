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

import br.com.integrado.api.dtos.EnderecoDTO;
import br.com.integrado.api.entities.EnderecoModel;
import br.com.integrado.api.entities.PessoaModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.CidadeService;
import br.com.integrado.api.service.EnderecoService;
import br.com.integrado.api.service.PessoaService;

@RestController
@RequestMapping("api/endereco")
@CrossOrigin(origins = "*")
public class EnderecoController {
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private CidadeService cidadeService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<EnderecoDTO>> cadastrar(@Valid @RequestBody EnderecoDTO enderecoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<EnderecoDTO> response = new Response<EnderecoDTO>();
		this.verificarPessoaExistente(enderecoDto, result);
		this.verificarCidadeExistente(enderecoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		EnderecoModel endereco = this.converterDtoParaEndereco(enderecoDto);
		this.enderecoService.salvar(endereco);
		response.setData(this.converterEnderecoParaDto(endereco));
		return ResponseEntity.ok(response);		
	}
	
	@PutMapping
	public ResponseEntity<Response<EnderecoDTO>> alterar(@Valid @RequestBody EnderecoDTO enderecoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<EnderecoDTO> response = new Response<EnderecoDTO>();
		this.verificarPessoaNoUpdate(enderecoDto, result);
		this.verificarCidadeExistente(enderecoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		EnderecoModel endereco = this.converterDtoParaEndereco(enderecoDto);
		endereco.setId(enderecoDto.getId());
		this.enderecoService.salvar(endereco);
		response.setData(this.converterEnderecoParaDto(endereco));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.enderecoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Endereço não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.enderecoService.deletar(id);
		response.setData("Endereço removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<EnderecoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<EnderecoDTO> response = new Response<EnderecoDTO>();
		Optional<EnderecoModel> endereco = this.enderecoService.buscarPorId(id);
		if (!endereco.isPresent()) {
			response.getErrors().add("Endereco não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterEnderecoParaDto(endereco.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}")
	public ResponseEntity<Response<Page<EnderecoDTO>>> buscarPorPessoaId (@PathVariable("pessoaid") Long pessoaId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<EnderecoDTO>> response = new  Response<Page<EnderecoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<EnderecoModel> enderecos = this.enderecoService.buscarPorPessoaId(pageRequest, pessoaId);
		Page<EnderecoDTO> enderecosDto = enderecos.map(endereco -> this.converterEnderecoParaDto(endereco));
		response.setData(enderecosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}/cep/{cep}")
	public ResponseEntity<Response<Page<EnderecoDTO>>> buscarPorPessoaIdECep (@PathVariable("pessoaid") Long pessoaId,
			@PathVariable("cep") String cep,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<EnderecoDTO>> response = new  Response<Page<EnderecoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<EnderecoModel> enderecos = this.enderecoService.buscarPorPessoaIdECEP(pageRequest, pessoaId, cep);
		Page<EnderecoDTO> enderecosDto = enderecos.map(endereco -> this.converterEnderecoParaDto(endereco));
		response.setData(enderecosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}/nomecidade/{nomecidade}")
	public ResponseEntity<Response<Page<EnderecoDTO>>> buscarPorPessoaIdENomeCidade (@PathVariable("pessoaid") Long pessoaId,
			@PathVariable("nomecidade") String nomeCidade,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<EnderecoDTO>> response = new  Response<Page<EnderecoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<EnderecoModel> enderecos = this.enderecoService.buscarPorPessoaIdENomeCidade(pageRequest, pessoaId, nomeCidade);
		Page<EnderecoDTO> enderecosDto = enderecos.map(endereco -> this.converterEnderecoParaDto(endereco));
		response.setData(enderecosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}/siglauf/{siglauf}")
	public ResponseEntity<Response<Page<EnderecoDTO>>> buscarPorPessoaIdESiglaUF(@PathVariable("pessoaid") Long pessoaId,
			@PathVariable("siglauf") String siglaUF,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<EnderecoDTO>> response = new  Response<Page<EnderecoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<EnderecoModel> enderecos = this.enderecoService.buscarPorPessoaIdESiglaUF(pageRequest, pessoaId, siglaUF);
		Page<EnderecoDTO> enderecosDto = enderecos.map(endereco -> this.converterEnderecoParaDto(endereco));
		response.setData(enderecosDto);
		return ResponseEntity.ok(response);
	}
	
	private void verificarPessoaNoUpdate(@Valid EnderecoDTO enderecoDto, BindingResult result) {
		Optional<PessoaModel> pessoa = this.pessoaService.buscarPessoaPorId(enderecoDto.getPessoaId());
		if (!pessoa.isPresent() || pessoa.get().getId() != enderecoDto.getPessoaId()) {
			result.addError(new ObjectError("Pessoa", "Pessoa não encontrada ou este endereco não pertence a esta pessoa"));
		}
		
	}

	private void verificarCidadeExistente(@Valid EnderecoDTO enderecoDto, BindingResult result) {
		if (!this.cidadeService.buscarPorId(enderecoDto.getCidadeId()).isPresent()) {
			result.addError(new ObjectError("Cidade", "Cidade não encontrada"));
		}
	}

	private void verificarPessoaExistente(@Valid EnderecoDTO enderecoDto, BindingResult result) {
		if (!this.pessoaService.buscarPessoaPorId(enderecoDto.getPessoaId()).isPresent()) {
			result.addError(new ObjectError("Pessoa", "Pessoa não encontrada"));
		}
	}

	private EnderecoDTO converterEnderecoParaDto(EnderecoModel endereco) {
		return new EnderecoDTO(endereco.getId(), endereco.getPessoa().getId(), endereco.getCidade().getId(),
				endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(),
				endereco.getCep(), endereco.getComplemento());
	}

	private EnderecoModel converterDtoParaEndereco(@Valid EnderecoDTO enderecoDto) {
		return new EnderecoModel(this.pessoaService.buscarPessoaPorId(enderecoDto.getPessoaId()).get(),
				this.cidadeService.buscarPorId(enderecoDto.getCidadeId()).get(), enderecoDto.getLogradouro(), 
				enderecoDto.getNumero(), enderecoDto.getBairro(), enderecoDto.getCep(), enderecoDto.getComplemento());
	}
	
}
