package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.integrado.api.dtos.AgendaDTO;
import br.com.integrado.api.dtos.AgendamentoDTO;
import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.enums.StatusAgendamentoEnum;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.AgendaService;
import br.com.integrado.api.service.AgendamentosService;
import br.com.integrado.api.service.ClienteService;
import br.com.integrado.api.utils.DataUtils;

@RestController
@RequestMapping("api/agendamento")
@CrossOrigin(origins = "*")
public class AgendamentoController {
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	private DataUtils dataUtils = new DataUtils();
	@Autowired
	private AgendaService agendaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private AgendamentosService agendamentoService;
	
	@PostMapping
	public ResponseEntity<Response<AgendamentoDTO>> cadastrar(@Valid @RequestBody AgendamentoDTO agendamentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AgendamentoDTO> response = new Response<AgendamentoDTO>();
		AgendamentoModel agendamento = this.converterDtoParaAgendamento(agendamentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.agendamentoService.salvar(agendamento);
		response.setData(this.converterAgendamentoParaDto(agendamento));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<AgendamentoDTO>> alterar(@Valid @RequestBody AgendamentoDTO agendamentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AgendamentoDTO> response = new Response<AgendamentoDTO>();
		this.verificarAgendamentoExistente(agendamentoDto.getId(), result);
		AgendamentoModel agendamento = this.converterDtoParaAgendamento(agendamentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		agendamento.setId(agendamentoDto.getId());
		this.agendamentoService.salvar(agendamento);
		response.setData(this.converterAgendamentoParaDto(agendamento));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/agendar")
	public ResponseEntity<Response<List<AgendamentoDTO>>> agendar(@Valid @RequestBody List<AgendamentoDTO> agendamentosDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<List<AgendamentoDTO>> response = new Response<List<AgendamentoDTO>>();
		List<AgendamentoModel> agendamentos = new ArrayList<AgendamentoModel>();
		for (AgendamentoDTO agendamentoDto : agendamentosDto) {
			this.verificarAgendamentoExistente(agendamentoDto.getId(), result);
			agendamentos.add(this.converterDtoParaAgendamento(agendamentoDto, result));
			
		}		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		agendamentosDto = new ArrayList<AgendamentoDTO>();
		for (AgendamentoModel agendamento : agendamentos) {
			this.agendamentoService.salvar(agendamento);
			agendamentosDto.add(this.converterAgendamentoParaDto(agendamento));
		}
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		if (!this.agendamentoService.buscarPorId(id).isPresent()) {
			response.getErrors().add("Agendamento não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}
		this.agendamentoService.deletar(id);
		response.setData("Agendamento removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping()
	public ResponseEntity<Response<String>> deletarAgendamentosAbertos(){
		Response<String> response = new Response<String>();
		this.agendamentoService.deletarAgendamentosAbertos();
		response.setData("Agendamento removido da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<AgendamentoDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<AgendamentoDTO> response = new Response<AgendamentoDTO>();
		Optional<AgendamentoModel> agendamento = this.agendamentoService.buscarPorId(id);
		
		if (!agendamento.isPresent()) {
			response.getErrors().add("agendamento não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterAgendamentoParaDto(agendamento.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarTodos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarTodos(pageRequest);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/agenda-id/{id}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorAgenda( 
			@PathVariable("id") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorAgendaId(pageRequest, id);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/cliente-id/{id}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorCliente( 
			@PathVariable("id") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorClienteId(pageRequest, id);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/funcionario-id/{id}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorFuncionario( 
			@PathVariable("id") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorFuncionarioId(pageRequest, id);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/funcionario-id/{id}/data/{data}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorFuncionarioEData( 
			@PathVariable("id") Long id,
			@PathVariable("data") Date data,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorFuncionarioIdEData(pageRequest, id, data);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/funcionario-id/{id}/status/{status}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorStatusEFuncionario( 
			@PathVariable("id") Long id,
			@PathVariable("status") Integer status,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorStatusEFuncionarioId(pageRequest, 
																StatusAgendamentoEnum.ObterStatusPorId(status), id);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorStatus( 
			@PathVariable("id") Integer status,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorStatus(pageRequest, 
																StatusAgendamentoEnum.ObterStatusPorId(status));
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/cliente-id/{id}/status/{status}")
	public ResponseEntity<Response<Page<AgendamentoDTO>>> buscarPorClienteEStatus( 
			@PathVariable("id") Long id,
			@PathVariable("status") Integer status,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendamentoDTO>> response = new Response<Page<AgendamentoDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendamentoModel> agendamentos = this.agendamentoService.buscarPorStatusEClienteId(pageRequest, 
																StatusAgendamentoEnum.ObterStatusPorId(status), id);
		Page<AgendamentoDTO> agendamentosDto = agendamentos.map(agenda -> this.converterAgendamentoParaDto(agenda));
		response.setData(agendamentosDto);
		return ResponseEntity.ok(response);
	}
	
	private void verificarAgendamentoExistente(Long id, BindingResult result) {
		if (!this.agendamentoService.buscarPorId(id).isPresent()) {
			result.addError(new ObjectError("Agendamento", "Agendamento informado não encontrado"));
		}
	}
	
	private AgendamentoDTO converterAgendamentoParaDto(AgendamentoModel agendamento) {
		return new AgendamentoDTO(agendamento.getId(), agendamento.getAgenda().getId(), 
				agendamento.getCliente().getId(), this.dataUtils.DataParaHora(agendamento.getHoraInicio()), 
				this.dataUtils.DataParaHora(agendamento.getHoraFim()), agendamento.getStatus().getStatus());
	}

	private AgendamentoModel converterDtoParaAgendamento(AgendamentoDTO agendamentoDto, BindingResult result) {
		AgendaModel agenda = this.verificarAgendaExistente(agendamentoDto.getAgenda(), result);
		ClienteModel cliente = this.verificarClienteExistente(agendamentoDto.getCliente(), result);
		if (agenda == null) {
			return null;
		}
		if (cliente != null) {
			return new AgendamentoModel(agendamentoDto.getId(),agenda, cliente, 
										this.dataUtils.StringParaHora(agendamentoDto.getHoraInicio()), 
										this.dataUtils.StringParaHora(agendamentoDto.getHoraFim()), 
										StatusAgendamentoEnum.ObterStatusPorId(agendamentoDto.getStatus()));
		}
		if (agendamentoDto.getId() != null) {
			return new AgendamentoModel(agenda, this.dataUtils.StringParaHora(agendamentoDto.getHoraInicio()), 
					this.dataUtils.StringParaHora(agendamentoDto.getHoraFim()), 
					StatusAgendamentoEnum.ObterStatusPorId(agendamentoDto.getStatus()));
		}
		return new AgendamentoModel(agenda, this.dataUtils.StringParaHora(agendamentoDto.getHoraInicio()), 
									this.dataUtils.StringParaHora(agendamentoDto.getHoraFim()), 
									StatusAgendamentoEnum.ObterStatusPorId(agendamentoDto.getStatus()));
	}

	private ClienteModel verificarClienteExistente(Long id, BindingResult result) {
		Optional<ClienteModel> cliente = this.clienteService.buscarPorID(id);
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("Cliente", "Cliente informado não encontrado"));
			return null;
		}
		return cliente.get();
	}

	private AgendaModel verificarAgendaExistente(Long id, BindingResult result) {
		Optional<AgendaModel> agenda = this.agendaService.buscarPorId(id);
		if (!agenda.isPresent()) {
			result.addError(new ObjectError("Agenda", "Agenda informada não encontrada"));
			return null;
		}
		return agenda.get();
	}
	
}
