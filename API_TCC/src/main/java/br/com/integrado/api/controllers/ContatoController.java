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

import br.com.integrado.api.dtos.ClienteDTO;
import br.com.integrado.api.dtos.ContatoDTO;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.entities.ContatoModel;
import br.com.integrado.api.entities.PessoaModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.ClienteService;
import br.com.integrado.api.service.ContatoService;
import br.com.integrado.api.service.PessoaService;

@RestController
@RequestMapping("api/contato")
@CrossOrigin(origins = "*")

public class ContatoController {
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private ContatoService contatoService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<ContatoDTO>> cadastrar(@Valid @RequestBody ContatoDTO contatoDTO,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ContatoDTO> response = new Response<ContatoDTO>();
		Optional<PessoaModel> pessoa = verificarPessoaCadastrada(contatoDTO.getIdPessoa(), result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		ContatoModel contato = this.converterDTOParaContato(contatoDTO, pessoa);
		this.contatoService.salvar(contato);
		
		response.setData(this.converterContatoParaDTO(contato));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<ContatoDTO>> alterar(@Valid @RequestBody ContatoDTO contatoDTO,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ContatoDTO> response = new Response<ContatoDTO>();
		verificarContatoExistente(contatoDTO, result);
		Optional<PessoaModel> pessoa = verificarPessoaCadastrada(contatoDTO.getIdPessoa(), result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		ContatoModel contato = this.converterDTOParaContato(contatoDTO, pessoa);
		contato.setId(contatoDTO.getId());
		this.contatoService.salvar(contato);
		response.setData(this.converterContatoParaDTO(contato));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.contatoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Contato não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		this.contatoService.deletar(id);
		response.setData("Contato removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ContatoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<ContatoDTO> response = new Response<ContatoDTO>();
		Optional<ContatoModel> contato = this.contatoService.buscarPorId(id);
		if (!contato.isPresent()) {
			response.getErrors().add("Contato não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterContatoParaDTO(contato.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}")
	public ResponseEntity<Response<Page<ContatoDTO>>> buscarTodosPorPessoa(
			@PathVariable("pessoaid") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ContatoDTO>> response = new Response<Page<ContatoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ContatoModel> contatos = this.contatoService.buscarPorIdPessoa(pageRequest, id);
		Page<ContatoDTO> contatosDTO = contatos.map(contato -> this.converterContatoParaDTO(contato));
		response.setData(contatosDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/pessoaid/{pessoaid}/telefone/{numtelefone}")
	public ResponseEntity<Response<Page<ContatoDTO>>> buscarTodosPorPessoaETelefone(
			@PathVariable("pessoaid") Long id,
			@PathVariable("numtelefone") String numtelefone,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ContatoDTO>> response = new Response<Page<ContatoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ContatoModel> contatos = this.contatoService.buscarPorNumTelefone(pageRequest, id, numtelefone);
		Page<ContatoDTO> contatosDTO = contatos.map(contato -> this.converterContatoParaDTO(contato));
		response.setData(contatosDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/pessoaid/{pessoaid}/email/{email}")
	public ResponseEntity<Response<Page<ContatoDTO>>> buscarTodosPorPessoaEEmail(
			@PathVariable("pessoaid") Long id,
			@PathVariable("email") String email,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<ContatoDTO>> response = new Response<Page<ContatoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ContatoModel> contatos = this.contatoService.buscarPorEmail(pageRequest, id, email);
		Page<ContatoDTO> contatosDTO = contatos.map(contato -> this.converterContatoParaDTO(contato));
		response.setData(contatosDTO);
		return ResponseEntity.ok(response);
	}
	private void verificarContatoExistente(@Valid ContatoDTO contatoDTO, BindingResult result) {
		Optional<ContatoModel> contato = this.contatoService.buscarPorId(contatoDTO.getIdPessoa(),contatoDTO.getId());
		if (!contato.isPresent()) {
			result.addError(new ObjectError("Contato", "Contato não existe ou não pertence a esta pessoa"));
		}
	}

	private Optional<PessoaModel> verificarPessoaCadastrada(Long id, BindingResult result) {
		Optional<PessoaModel> pessoa = this.pessoaService.buscarPessoaPorId(id);
		if (!pessoa.isPresent()){
			result.addError(new ObjectError("Cliente", "Cliente não cadastrado ou não informado"));
			return null;
		}
		return pessoa;
	}
	
	private ContatoModel converterDTOParaContato(@Valid ContatoDTO contatoDTO, Optional<PessoaModel> pessoa) {
		return new ContatoModel(contatoDTO.getNumTelefone(),contatoDTO.getEmail(), pessoa.get());
	}
	
	private ContatoDTO converterContatoParaDTO(ContatoModel contato) {
		return new ContatoDTO(contato.getId(), contato.getNumTelefone(), contato.getEmail(), contato.getPessoa().getId());
	}
}
