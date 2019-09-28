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
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.entities.ProdutoAtendimentoModel;
import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.entities.ServicoAtendimentoModel;
import br.com.integrado.api.entities.ServicoModel;
import br.com.integrado.api.enums.StatusAtendimento;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.AtendimentoService;
import br.com.integrado.api.service.BrindeConfigService;
import br.com.integrado.api.service.BrindeService;
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
	@Autowired
	private BrindeService brindeService;
	@Autowired
	private BrindeConfigService brindeConfigService;
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
		this.produtoAtendimentoService.salvar(this.converterDtoEmProdutoAtendimento(atendimentoDto.getProdutosAtendimentoDTO(), atendimento));
		this.servicoAtendimentoService.salvar(this.converterDtoEmServicoAtendimento(atendimentoDto.getServicosAtendimentoDTO(), atendimento));
		response.setData(this.converterAtendimentoEmDTO(atendimento, atendimentoDto));
		return ResponseEntity.ok(response);
	}

	private AtendimentoDTO converterAtendimentoEmDTO(AtendimentoModel atendimento, AtendimentoDTO atendimentoDto) {
		return new AtendimentoDTO(atendimento.getId(), atendimento.getCliente().getId(), atendimentoDto.getTipoPagamentoId(), 
				atendimentoDto.getFormaPagamentoId(), atendimento.getValDesconto(), this.dataUtils.DataParaStringSpring(atendimento.getDataAtendimento()),
				atendimento.getSituacao().getStatus(), atendimento.getValTotalProdutos(), atendimento.getValTotalServicos(),
				this.converterServicosAtendimentoEmDto(atendimento.getId()), this.converterProdutosAtendimentoEmDto((atendimento.getId())));
	}

	private List<ProdutosAtendimentoDTO> converterProdutosAtendimentoEmDto(Long id) {
		List<ProdutosAtendimentoDTO> produtosDto = new ArrayList<ProdutosAtendimentoDTO>();
		for (ProdutoAtendimentoModel produtoAtendimento : this.produtoAtendimentoService.buscarPorAtendimentoId(id)) {
			produtosDto.add(new ProdutosAtendimentoDTO(produtoAtendimento.getId(), produtoAtendimento.getProduto().getId(),
					produtoAtendimento.getQuantidade(), produtoAtendimento.getValUnitario(), id));
		}
		return produtosDto;
	}

	private List<ServicosAtendimentoDTO> converterServicosAtendimentoEmDto(Long id) {
		List<ServicosAtendimentoDTO> servicosAtendimentoDto = new ArrayList<ServicosAtendimentoDTO>();
		for (ServicoAtendimentoModel servicosAtendimento : this.servicoAtendimentoService.buscarPorAtendimentoId(id)) {
			servicosAtendimentoDto.add(new ServicosAtendimentoDTO(servicosAtendimento.getId(), servicosAtendimento.getServico().getId(), 
					servicosAtendimento.getQuantidade(), id));
		}
		return servicosAtendimentoDto;
	}

	private List<ServicoAtendimentoModel> converterDtoEmServicoAtendimento(
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, AtendimentoModel atendimento) {
		List<ServicoAtendimentoModel> servicoAtendimento = new ArrayList<ServicoAtendimentoModel>();
		for (ServicosAtendimentoDTO servicoAtendimentoDto : servicosAtendimentoDTO) {
			ServicoModel servico = this.servicoService.buscarPorId(servicoAtendimentoDto.getServicoId()).get();
			if (servicoAtendimentoDto.getId()!= null) {
				servicoAtendimento.add(new ServicoAtendimentoModel(servicoAtendimentoDto.getId(), 
						servico, servicoAtendimentoDto.getQuantidade(), servico.getValor(), atendimento));
			}
			else{
				servicoAtendimento.add(new ServicoAtendimentoModel(	servico, servicoAtendimentoDto.getQuantidade(), 
						servico.getValor(), atendimento));
			}

		}
		return servicoAtendimento;
	}

	private List<ProdutoAtendimentoModel> converterDtoEmProdutoAtendimento(List<ProdutosAtendimentoDTO> produtosAtendimentoDTO, AtendimentoModel atendimento) {
		List<ProdutoAtendimentoModel> produtosAtendimento = new ArrayList<ProdutoAtendimentoModel>();
		for (ProdutosAtendimentoDTO produtoAtendimentoDto : produtosAtendimentoDTO) {
			ProdutoModel produto = this.produtoService.buscarPorId(produtoAtendimentoDto.getProdutoId()).get();
			if (produtoAtendimentoDto.getId()!= null) {
				produtosAtendimento.add(new ProdutoAtendimentoModel(produtoAtendimentoDto.getId(), atendimento, produto, 
														produtoAtendimentoDto.getQuantidade(), produto.getValVendido()));
			}
			else {
				produtosAtendimento.add(new ProdutoAtendimentoModel(atendimento, produto, 
						produtoAtendimentoDto.getQuantidade(), produto.getValVendido()));
			}
		}
		return produtosAtendimento;
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

	private AtendimentoModel converterDTOEmAtendimento(AtendimentoDTO atendimentoDto) {
		ClienteModel cliente = this.clienteService.buscarPorID(atendimentoDto.getClienteId()).get();
		Double valTotalProdutos = this.calcularValorTotalProdutos(atendimentoDto.getProdutosAtendimentoDTO());
		Double valTotalServicos = this.calcularValorTotalServicos(atendimentoDto.getServicosAtendimentoDTO(), cliente.getId());
		Double valDesconto = (valTotalProdutos + valTotalServicos) * (atendimentoDto.getValDesconto() / 100);	
		if (atendimentoDto.getId() != null) {
			return new AtendimentoModel(atendimentoDto.getId(), cliente, 
					valDesconto, dataUtils.StringParaDataSpring(atendimentoDto.getDataAtendimento()), new Date(), 
					StatusAtendimento.ObterStatusPorId(atendimentoDto.getSituacao()), valTotalProdutos, valTotalServicos);
		}
		return new AtendimentoModel(cliente, valDesconto, 
					dataUtils.StringParaDataSpring(atendimentoDto.getDataAtendimento()), new Date(), 
					StatusAtendimento.ObterStatusPorId(atendimentoDto.getSituacao()), valTotalProdutos, valTotalServicos);
	}

	private Double calcularValorTotalServicos(List<ServicosAtendimentoDTO> servicosAtendimentoDTO, Long clienteId) {
		Double total = new Double(0);
		for (ServicosAtendimentoDTO servicoAtendimentoDto : servicosAtendimentoDTO) {
			ServicoModel servico = this.servicoService.buscarPorId(servicoAtendimentoDto.getServicoId()).get();
			total += servicoAtendimentoDto.getQuantidade() * servico.getValor();
			if (servicoAtendimentoDto.getBrindeAniversario()) {
				total -= servico.getValor();
			}
			else if (this.verificarBrindeFidelidade(servico, clienteId,servicoAtendimentoDto.getQuantidade())) {
				total -= servico.getValor();
			}
		}
		return total;
	}

	private boolean verificarBrindeFidelidade(ServicoModel servico, Long clienteId, Integer quantidade) {
		BrindeModel brinde = this.brindeService.buscarPorClienteEServico(clienteId, servico.getId()).get();
		if (brinde.getBrindeConfig().getBrindeFidelidade() && (quantidade + brinde.getContadorBrinde())> brinde.getBrindeConfig().getQuantContador()) {
			return true;
		}
		return false;
	}

	private Double calcularValorTotalProdutos(List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		Double total = new Double(0);
		for (ProdutosAtendimentoDTO produtoAtendimentoDTO : produtosAtendimentoDTO) {
			total += this.produtoService.buscarPorId(produtoAtendimentoDTO.getProdutoId()).get().getValVendido() * produtoAtendimentoDTO.getQuantidade();
		}
		return total;
	}

	private void validarServicos(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		int contBrindAniversario = 0;
		for (ServicosAtendimentoDTO servicoAtendimentoDto : atendimentoDto.getServicosAtendimentoDTO()) {
			if (!this.servicoService.buscarPorId(servicoAtendimentoDto.getServicoId()).isPresent()) {
				result.addError(new ObjectError("Servico", "Servico do id " + servicoAtendimentoDto.getServicoId() +" não encontrado"));
			}	
			if (!this.brindeService.buscarPorClienteEServico(atendimentoDto.getClienteId(), servicoAtendimentoDto.getServicoId()).isPresent()) {
				this.brindeService.salvar(new BrindeModel(this.clienteService.buscarPorID(atendimentoDto.getClienteId()).get(),
						this.servicoService.buscarPorId(servicoAtendimentoDto.getServicoId()).get(), 0,
						this.brindeConfigService.buscarPorId(servicoAtendimentoDto.getBrindeConfig()).get()));
			}
			if (servicoAtendimentoDto.getBrindeAniversario()) {
				contBrindAniversario++;
				this.validarBrindeAniversario(atendimentoDto, result);
			}
			if (contBrindAniversario > 1) {
				result.addError(new ObjectError("Brinde Aniversário", "Somente um brinde de aniversário por atendimento"));
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
	
	private void validarBrindeAniversario(AtendimentoDTO atendimentoDto, BindingResult result) {
		if (!this.brindeService.verificarBrindeAniversario(atendimentoDto.getClienteId())) {
			result.addError(new ObjectError("Brinde Aniversário", "Brinde de aniversário não disponível"));
		}
	}
	
}
