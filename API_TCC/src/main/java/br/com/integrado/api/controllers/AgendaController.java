package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.integrado.api.dtos.AgendaDTO;
import br.com.integrado.api.entities.AgendaModel;
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
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AgendaModel agenda = this.converterDtoParaAgenda(agendaDto);
		this.agendaService.criarAgenda(agenda);
		response.setData(this.converterAgendaParaDto(agenda));
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
	
	
}
