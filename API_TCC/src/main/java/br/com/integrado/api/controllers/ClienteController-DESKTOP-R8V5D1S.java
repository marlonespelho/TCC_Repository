package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.type.OffsetDateTimeType;
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
import br.com.integrado.api.models.CPFModel;
import br.com.integrado.api.models.ClienteModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.ClienteService;
import br.com.integrado.api.utils.DataUtils;

@RestController
@RequestMapping("api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired(required = true)
	private ClienteService clienteService;
	private DataUtils dataUtils = new DataUtils();
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<Response<ClienteDTO>> cadastrar(@Valid @RequestBody ClienteDTO clienteDto, 
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		this.verificarCPFExistente(clienteDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		ClienteModel cliente = this.converterDtoParaCliente(clienteDto);
		this.clienteService.salvar(cliente);
		
		response.setData(this.converterClienteParaDTO(cliente));
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/alterar")
	public ResponseEntity<Response<ClienteDTO>> alterar(@Valid @RequestBody ClienteDTO clienteDto, 
			BindingResult result) throws NoSuchAlgorithmException{
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		this.verificarCPFExistenteUpdate(clienteDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		ClienteModel cliente = this.converterDtoParaCliente(clienteDto);
		cliente.setId(clienteDto.getId());
		this.clienteService.salvar(cliente);
		
		response.setData(this.converterClienteParaDTO(cliente));
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		
		if (this.clienteService.buscarPorID(id) == null ) {
			response.getErrors().add("Cliente não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		this.clienteService.deletar(id.longValue());
		response.setData("Cliente removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/buscar/id/{id}")
	public ResponseEntity<Response<ClienteDTO>> buscarPorId(@PathVariable("id") Integer id){
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		
		ClienteModel cliente = this.clienteService.buscarPorID(id.longValue());
		
		if (cliente == null) {
			response.getErrors().add("Cliente não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterClienteParaDTO(cliente));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/buscar")
	public ResponseEntity<Response<Page<ClienteDTO>>> buscarTodos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<ClienteDTO>> response = new Response<Page<ClienteDTO>>();
		
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ClienteModel> clientes = this.clienteService.BuscarTodos(pageRequest);
		Page<ClienteDTO> clientesDTO = clientes.map(cliente -> this.converterClienteParaDTO(cliente));
		
		response.setData(clientesDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/buscar/nome/{nome}")
	public ResponseEntity<Response<Page<ClienteDTO>>> buscarPorNome(@PathVariable("nome") String nome,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<ClienteDTO>> response = new Response<Page<ClienteDTO>>();
		
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ClienteModel> clientes = this.clienteService.buscarPorNome(pageRequest, nome);
		Page<ClienteDTO> clientesDTO = clientes.map(cliente -> this.converterClienteParaDTO(cliente));
		
		response.setData(clientesDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/buscar/dt-nascimento/{data}")
	public ResponseEntity<Response<Page<ClienteDTO>>> buscarPorDtNascimento(@PathVariable("data") String data,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir)
	{
		Response<Page<ClienteDTO>> response = new Response<Page<ClienteDTO>>();
		
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ClienteModel> clientes = this.clienteService.buscarPorDtNascimento(pageRequest, this.dataUtils.StringParaDataSpring(data));
		Page<ClienteDTO> clientesDTO = clientes.map(cliente -> this.converterClienteParaDTO(cliente));
		
		response.setData(clientesDTO);
		return ResponseEntity.ok(response);
	}
	
	

	private ClienteDTO converterClienteParaDTO(ClienteModel cliente) {
		ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), 
											   cliente.getNome(),cliente.getInAtivo(),
											   this.dataUtils.DataParaStringSpring(cliente.getDtNascimento()), 
											   cliente.getCpf().getCPF());
		return clienteDTO;
	}

	private ClienteModel converterDtoParaCliente(@Valid ClienteDTO clienteDto) {
		ClienteModel cliente = new ClienteModel(clienteDto.getNome(),
												clienteDto.getInAtivo(),
												this.dataUtils.StringParaDataSpring(clienteDto.getDtNascimento()),
												new CPFModel(clienteDto.getCpf()));
		
		return cliente;
	}

	private void verificarCPFExistente(@Valid ClienteDTO clienteDto, BindingResult result) {
		if (this.clienteService.buscarPorCPF(clienteDto.getCpf()) != null){
			result.addError(new ObjectError("Cliente", "CPF já cadastrado"));
		};
	}
	
	private void verificarClienteCadastrado(@Valid ClienteDTO clienteDto, BindingResult result) {
		if (this.clienteService.buscarPorID(clienteDto.getId()) == null){
			result.addError(new ObjectError("Cliente", "Cliente não cadastrado"));
		};
	}
	
	private void verificarCPFExistenteUpdate(@Valid ClienteDTO clienteDto, BindingResult result) {
		if (this.clienteService.buscarPorCPF(clienteDto.getId(), clienteDto.getCpf()) != null){
			result.addError(new ObjectError("Cliente", "CPF já cadastrado em outro cliente"));
		};
	}

}
