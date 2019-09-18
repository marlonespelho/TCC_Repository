package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.integrado.api.dtos.AgendaDTO;
import br.com.integrado.api.dtos.FuncionarioDTO;
import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.AgendaService;
import br.com.integrado.api.service.FuncionarioService;
import br.com.integrado.api.utils.DataUtils;

@RestController
@RequestMapping("api/agenda")
@CrossOrigin(origins = "*")
public class AgendaController {
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	private DataUtils dataUtils = new DataUtils();
	@Autowired
	private AgendaService agendaService;
	@Autowired
	private FuncionarioService funcionarioService;
	
	@PostMapping
	public ResponseEntity<Response<AgendaDTO>> cadastrar(@Valid @RequestBody AgendaDTO agendaDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AgendaDTO> response = new Response<AgendaDTO>();
		this.verificarAgendaExistente(agendaDto, result);
		this.verificarDataAgenda(agendaDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AgendaModel agenda = this.converterDtoParaAgenda(agendaDto);
		this.agendaService.criarAgenda(agenda);
		response.setData(this.converterAgendaParaDto(agenda));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletar(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		
		if (!this.agendaService.verificarAgendamentosMarcados(id)) {
			response.getErrors().add("Agenda não existe ou há agendamentos marcados para ela.");
			return ResponseEntity.badRequest().body(response);
		}
		this.agendaService.deletar(id);
		response.setData("Agenda removida da base de dados.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<AgendaDTO>> buscarPorId(@PathVariable("id") Long id){
		Response<AgendaDTO> response = new Response<AgendaDTO>();
		Optional<AgendaModel> agenda = this.agendaService.buscarPorId(id);
		
		if (!agenda.isPresent()) {
			response.getErrors().add("agenda não encontrada");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterAgendaParaDto(agenda.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<AgendaDTO>>> buscarTodos( 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendaDTO>> response = new Response<Page<AgendaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendaModel> agendas = this.agendaService.buscarTodos(pageRequest);
		Page<AgendaDTO> agendasDto = agendas.map(agenda -> this.converterAgendaParaDto(agenda));
		response.setData(agendasDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/data/{data}")
	public ResponseEntity<Response<Page<AgendaDTO>>> buscarPorData( 
			@PathVariable("data") String data,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendaDTO>> response = new Response<Page<AgendaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendaModel> agendas = this.agendaService.buscarPorData(pageRequest, this.dataUtils.StringParaDataSpring(data));
		Page<AgendaDTO> agendasDto = agendas.map(agenda -> this.converterAgendaParaDto(agenda));
		response.setData(agendasDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/funcionarioid/{id}")
	public ResponseEntity<Response<Page<AgendaDTO>>> buscarPorFuncionario( 
			@PathVariable("id") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendaDTO>> response = new Response<Page<AgendaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendaModel> agendas = this.agendaService.buscarPorFuncionarioId(pageRequest, id);
		Page<AgendaDTO> agendasDto = agendas.map(agenda -> this.converterAgendaParaDto(agenda));
		response.setData(agendasDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/funcionarioid/{id}/data/{data}")
	public ResponseEntity<Response<Page<AgendaDTO>>> buscarPorFuncionarioEData( 
			@PathVariable("id") Long id,
			@PathVariable("data") String data,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		Response<Page<AgendaDTO>> response = new Response<Page<AgendaDTO>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<AgendaModel> agendas = this.agendaService.buscarPorFuncionarioId(pageRequest, id);
		Page<AgendaDTO> agendasDto = agendas.map(agenda -> this.converterAgendaParaDto(agenda));
		response.setData(agendasDto);
		return ResponseEntity.ok(response);
	}
	
	private AgendaDTO converterAgendaParaDto(AgendaModel agenda) {
		return new AgendaDTO(agenda.getId(), this.dataUtils.DataParaStringSpring(agenda.getData()), 
				agenda.getFuncionario().getId(), dataUtils.DataParaHora(agenda.getHoraInicial()), 
				dataUtils.DataParaHora(agenda.getHoraFinal()));
	}

	private AgendaModel converterDtoParaAgenda(@Valid AgendaDTO agendaDto) {
		return new AgendaModel(this.dataUtils.StringParaDataSpring(agendaDto.getData()), 
				this.funcionarioService.buscarPorId(agendaDto.getFuncionarioId()).get(), 
				this.dataUtils.StringParaHora(agendaDto.getHoraInicial()), 
				this.dataUtils.StringParaHora(agendaDto.getHoraFinal()));
	}

	private void verificarAgendaExistente(@Valid AgendaDTO agendaDto, BindingResult result) {
		if (this.agendaService.buscarPorData(this.dataUtils.StringParaDataSpring(agendaDto.getData()), 
				agendaDto.getFuncionarioId()).isPresent()) {
			result.addError(new ObjectError("Agenda", "já existe agenda nesta data para este funcionário"));
		}
		
	}
	
	private void verificarDataAgenda(@Valid AgendaDTO agendaDto, BindingResult result) {
		Date dataAtual = this.dataUtils.zerarHoras(new Date());
		if (this.dataUtils.StringParaDataSpring(agendaDto.getData()).getTime() < dataAtual.getTime()) {
			result.addError(new ObjectError("Agenda", "Data informada menor do que a atual"));
		}
	}
	
}
