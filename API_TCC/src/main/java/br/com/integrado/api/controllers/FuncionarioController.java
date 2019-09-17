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

import br.com.integrado.api.dtos.FuncionarioDTO;
import br.com.integrado.api.dtos.FuncionarioDTO;
import br.com.integrado.api.entities.CPFModel;
import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.enums.PerfilEnum;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.FuncionarioService;
import br.com.integrado.api.utils.SenhaUtils;

@RestController
@RequestMapping("api/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	@Autowired(required = true)
	private FuncionarioService funcionarioService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	public ResponseEntity<Response<FuncionarioDTO>> cadastrar(@Valid @RequestBody FuncionarioDTO funcionarioDto, 
			BindingResult result) throws NoSuchAlgorithmException{
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		this.verificarCPFExistente(funcionarioDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		FuncionarioModel funcionario = this.converterDtoParaFuncionario(funcionarioDto);
		this.funcionarioService.salvar(funcionario);
		
		response.setData(this.converterFuncionarioParaDTO(funcionario));
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<FuncionarioDTO>> alterar(@Valid @RequestBody FuncionarioDTO funcionarioDto, 
			BindingResult result) throws NoSuchAlgorithmException{
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		this.verificarCPFExistenteUpdate(funcionarioDto, result);
		if (!this.funcionarioService.buscarPorId(funcionarioDto.getId()).isPresent()) {
			response.getErrors().add("Funcionario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		FuncionarioModel funcionario = this.converterDtoParaFuncionario(funcionarioDto);
		funcionario.setId(funcionarioDto.getId());
		this.funcionarioService.salvar(funcionario);
		
		response.setData(this.converterFuncionarioParaDTO(funcionario));
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		
		if (!this.funcionarioService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Funcionario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.deletar(id);
		response.setData("Funcionario removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		Optional<FuncionarioModel> funcionario = this.funcionarioService.buscarPorId(id);
		
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterFuncionarioParaDTO(funcionario.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Response<FuncionarioDTO>> buscarPorCPF(@PathVariable("cpf") String cpf){
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		Optional<FuncionarioModel> funcionario = this.funcionarioService.buscarPorCPF(cpf);
		
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterFuncionarioParaDTO(funcionario.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/usuario/{usuario}")
	public ResponseEntity<Response<FuncionarioDTO>> buscarPorUsuario(@PathVariable("usuario") String usuario){
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		Optional<FuncionarioModel> funcionario = this.funcionarioService.buscarPorUsuario(usuario);
		
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionario não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterFuncionarioParaDTO(funcionario.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<FuncionarioDTO>>> buscarTodos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FuncionarioDTO>> response = new Response<Page<FuncionarioDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FuncionarioModel> funcionarios = this.funcionarioService.buscarTodos(pageRequest);
		Page<FuncionarioDTO> funcionariosDTO = funcionarios.map(funcionario -> this.converterFuncionarioParaDTO(funcionario));
		
		response.setData(funcionariosDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/inativos")
	public ResponseEntity<Response<Page<FuncionarioDTO>>> buscarInativos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<FuncionarioDTO>> response = new Response<Page<FuncionarioDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FuncionarioModel> funcionarios = this.funcionarioService.buscarInativos(pageRequest);
		Page<FuncionarioDTO> funcionariosDTO = funcionarios.map(funcionario -> this.converterFuncionarioParaDTO(funcionario));
		
		response.setData(funcionariosDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<Page<FuncionarioDTO>>> buscarPorNome(@PathVariable("nome") String nome,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<FuncionarioDTO>> response = new Response<Page<FuncionarioDTO>>();
		
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FuncionarioModel> funcionarios = this.funcionarioService.buscarPorNome(pageRequest, nome);
		Page<FuncionarioDTO> funcionariosDTO = funcionarios.map(funcionario -> this.converterFuncionarioParaDTO(funcionario));
		
		response.setData(funcionariosDTO);
		return ResponseEntity.ok(response);
	}
	

	

	private FuncionarioDTO converterFuncionarioParaDTO(FuncionarioModel funcionario) {
		return new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getCpf().getCPF(), 
									funcionario.getUsuario(), funcionario.getSenha(), 
									funcionario.getInAtivo(), funcionario.getPerfil().getPerfilEnum());
	}

	private FuncionarioModel converterDtoParaFuncionario(@Valid FuncionarioDTO funcionarioDto) {
		return new FuncionarioModel(funcionarioDto.getNome(), funcionarioDto.getInAtivo(), 
									PerfilEnum.obterPerfilPeloId(funcionarioDto.getPerfil()), 
									funcionarioDto.getUsuario(), SenhaUtils.gerarBCrypt(funcionarioDto.getSenha()), 
									new CPFModel(funcionarioDto.getCpf()));
	}

	private void verificarCPFExistente(@Valid FuncionarioDTO funcionarioDto, BindingResult result) {
		if (this.funcionarioService.buscarPorCPF(funcionarioDto.getCpf()).isPresent()){
			result.addError(new ObjectError("Funcionario", "CPF já cadastrado"));
		};
		
	}

	private void verificarCPFExistenteUpdate(@Valid FuncionarioDTO funcionarioDto, BindingResult result) {
		if (this.funcionarioService.buscarPorCPF(funcionarioDto.getId(), funcionarioDto.getCpf()).isPresent()){
			result.addError(new ObjectError("Funcionário", "CPF já cadastrado em outro funcionário"));
		};
	}
}
