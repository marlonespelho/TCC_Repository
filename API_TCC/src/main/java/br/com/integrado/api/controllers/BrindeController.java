package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.integrado.api.dtos.BrindeDTO;
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.BrindeConfigService;
import br.com.integrado.api.service.BrindeService;
import br.com.integrado.api.utils.DataUtils;

@RestController
@RequestMapping("api/brinde")
@CrossOrigin(origins = "*")
public class BrindeController {

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	private DataUtils dataUtils = new DataUtils();
	@Autowired
	private BrindeService brindeService;
	@Autowired
	private BrindeConfigService brindeConfigService;
}
