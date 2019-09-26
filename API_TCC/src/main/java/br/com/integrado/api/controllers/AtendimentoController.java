package br.com.integrado.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import br.com.integrado.api.dtos.AtendimentoDTO;
import br.com.integrado.api.dtos.ProdutosAtendimentoDTO;
import br.com.integrado.api.dtos.ServicosAtendimentoDTO;
import br.com.integrado.api.entities.AtendimentoModel;
import br.com.integrado.api.entities.ProdutoAtendimentoModel;
import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.entities.ServicoAtendimentoModel;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.AtendimentoService;
import br.com.integrado.api.service.ClienteService;
import br.com.integrado.api.service.FormaPagamentoService;
import br.com.integrado.api.service.ProdutoAtendimentoService;
import br.com.integrado.api.service.ProdutoService;
import br.com.integrado.api.service.ServicoAtendimentoService;
import br.com.integrado.api.service.ServicoService;
import br.com.integrado.api.service.TipoPagamentoService;
import br.com.integrado.api.utils.DataUtils;

@RestController
@RequestMapping("api/atendimento")
@CrossOrigin(origins = "*")
public class AtendimentoController {

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	@Autowired
	private AtendimentoService atendimentoService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoAtendimentoService produtoAtendimentoService;
	@Autowired
	private ServicoAtendimentoService servicoAtendimentoService;
	@Autowired
	private ServicoService servicoService;
	@Autowired
	private TipoPagamentoService tipoPagamentoService;
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	@Autowired 
	private ClienteService clienteService;
	private DataUtils dataUtils = new DataUtils();
	
	@PostMapping
	public ResponseEntity<Response<AtendimentoDTO>> cadastrar(@Valid @RequestBody AtendimentoDTO atendimentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AtendimentoDTO> response = new Response<AtendimentoDTO>();
		this.validarCabecalho(atendimentoDto, result);
		this.validarProdutos(atendimentoDto, result);
		this.validarServicos(atendimentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AtendimentoModel atendimento = this.converterDTOEmAtendimento(atendimentoDto);
		this.atendimentoService.salvar(atendimento);
		this.produtoAtendimentoService.salvar(this.converterDtoEmProdutoAtendimento(atendimentoDto.getProdutosAtendimentoDTO(), atendimento.getId()));
		this.servicoAtendimentoService.salvar(this.converterDtoEmServicoAtendimento(atendimentoDto.getServicosAtendimentoDTO(), atendimento.getId()));
		return ResponseEntity.ok(response);
	}

	private ServicoAtendimentoModel converterDtoEmServicoAtendimento(
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, Long id) {
		List<ServicoAtendimentoModel> servicoAtendimento = new ArrayList<ServicoAtendimentoModel>();
		for (ServicosAtendimentoDTO servicoAtendimentoDto : servicosAtendimentoDTO) {
			if (servicoAtendimentoDto.getId()!= null) {
				
			}
		}
		return null;
	}

	private ProdutoAtendimentoModel converterDtoEmProdutoAtendimento(
			List<ProdutosAtendimentoDTO> produtosAtendimentoDTO, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	private void validarProdutos(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		for (ProdutosAtendimentoDTO produtoAtendimentoDto : atendimentoDto.getProdutosAtendimentoDTO()) {
			Optional<ProdutoModel> produto = this.produtoService.buscarPorId(produtoAtendimentoDto.getProdutoId());
			if (!produto.isPresent()) {
				result.addError(new ObjectError("Produto", "Produto do id " + produtoAtendimentoDto.getProdutoId() +" não encontrado"));
			}
			if (this.produtoService.buscarPorId(produtoAtendimentoDto.getProdutoId()).get().getQuantidade() < produtoAtendimentoDto.getQuantidade()) {
				result.addError(new ObjectError("Produto", produto.get().getDescricao() +" sem quantidade suficiente em estoque"));
			}
		}
	}

	private AtendimentoModel converterDTOEmAtendimento(@Valid AtendimentoDTO atendimentoDto) {
		return null;
	}

	private void validarServicos(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		for (ServicosAtendimentoDTO servicoAtendimentoDto : atendimentoDto.getServicosAtendimentoDTO()) {
			if (!this.servicoService.buscarPorId(servicoAtendimentoDto.getServicoId()).isPresent()) {
				result.addError(new ObjectError("Servico", "Servico do id " + servicoAtendimentoDto.getServicoId() +" não encontrado"));
			}	
		}
	}

	private void validarCabecalho(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		if (!this.tipoPagamentoService.buscarPorId(atendimentoDto.getTipoPagamentoId()).isPresent()){
			result.addError(new ObjectError("Tipo Pagamento", "Tipo de pagamento não informado ou não encontrado"));
		}
		if (!this.formaPagamentoService.buscarPorId(atendimentoDto.getFormaPagamentoId()).isPresent()){
			result.addError(new ObjectError("Forma Pagamento", "Forma de pagamento não informada ou não encontrada"));
		}
		if (!this.clienteService.buscarPorID(atendimentoDto.getClienteId()).isPresent()){
			result.addError(new ObjectError("Cliente", "Cliente não informado ou não encontrado"));
		}
		if (this.dataUtils.StringParaDataSpring(atendimentoDto.getDataAtendimento()).getTime() >  this.dataUtils.zerarHoras(new Date()).getTime()) {
			result.addError(new ObjectError("Data", "Data informada maior do que a atual"));
		}
	}
	
	
}
