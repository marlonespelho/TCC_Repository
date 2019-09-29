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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.integrado.api.dtos.AgendamentoDTO;
import br.com.integrado.api.dtos.AtendimentoDTO;
import br.com.integrado.api.dtos.ProdutosAtendimentoDTO;
import br.com.integrado.api.dtos.ServicosAtendimentoDTO;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.entities.AtendimentoModel;
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.entities.ProdutoAtendimentoModel;
import br.com.integrado.api.entities.ProdutoModel;
import br.com.integrado.api.entities.ServicoAtendimentoModel;
import br.com.integrado.api.entities.ServicoModel;
import br.com.integrado.api.enums.StatusAgendamento;
import br.com.integrado.api.enums.StatusAtendimento;
import br.com.integrado.api.responses.Response;
import br.com.integrado.api.service.AgendamentosService;
import br.com.integrado.api.service.AtendimentoService;
import br.com.integrado.api.service.BrindeConfigService;
import br.com.integrado.api.service.BrindeService;
import br.com.integrado.api.service.ClienteService;
import br.com.integrado.api.service.FormaPagamentoService;
import br.com.integrado.api.service.FuncionarioService;
import br.com.integrado.api.service.MovEstoqueService;
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
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private AgendamentosService agendamentosService;
	@Autowired
	private MovEstoqueService estoqueService;
	private DataUtils dataUtils = new DataUtils();
	
	@PostMapping
	public ResponseEntity<Response<AtendimentoDTO>> cadastrar(@Valid @RequestBody AtendimentoDTO atendimentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AtendimentoDTO> response = new Response<AtendimentoDTO>();
		this.validarCabecalho(atendimentoDto, result);
		this.validarProdutos(atendimentoDto, result);
		this.validarServicos(atendimentoDto, result);
		this.validarAgendamentos(atendimentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AtendimentoModel atendimento = this.converterDTOEmAtendimento(atendimentoDto);
		this.atendimentoService.salvar(atendimento);
		this.produtoAtendimentoService.salvar(this.converterDtoEmProdutoAtendimento(atendimentoDto, atendimento));
		this.servicoAtendimentoService.salvar(this.converterDtoEmServicoAtendimento(atendimentoDto, atendimento));
		this.agendamentosService.salvar(this.converterDtoEmAgendamentos(atendimentoDto, atendimento));
		response.setData(this.converterAtendimentoEmDTO(atendimento, atendimentoDto));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<AtendimentoDTO>> alterar(@Valid @RequestBody AtendimentoDTO atendimentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AtendimentoDTO> response = new Response<AtendimentoDTO>();
		this.verificarStatusAtendimento(atendimentoDto, result);
		this.validarCabecalho(atendimentoDto, result);
		this.validarProdutos(atendimentoDto, result);
		this.validarServicos(atendimentoDto, result);
		this.validarAgendamentos(atendimentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AtendimentoModel atendimento = this.converterDTOEmAtendimento(atendimentoDto);
		this.atendimentoService.salvar(atendimento);
		this.produtoAtendimentoService.salvar(this.converterDtoEmProdutoAtendimento(atendimentoDto, atendimento));
		this.servicoAtendimentoService.salvar(this.converterDtoEmServicoAtendimento(atendimentoDto, atendimento));
		this.agendamentosService.salvar(this.converterDtoEmAgendamentos(atendimentoDto, atendimento));
		response.setData(this.converterAtendimentoEmDTO(atendimento, atendimentoDto));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<AtendimentoDTO>> consolidar(@Valid @RequestBody AtendimentoDTO atendimentoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		Response<AtendimentoDTO> response = new Response<AtendimentoDTO>();
		this.verificarStatusAtendimento(atendimentoDto, result);
		this.validarCabecalho(atendimentoDto, result);
		this.validarProdutos(atendimentoDto, result);
		this.validarServicos(atendimentoDto, result);
		this.validarAgendamentos(atendimentoDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		AtendimentoModel atendimento = this.converterDTOEmAtendimento(atendimentoDto);
		this.atendimentoService.salvar(atendimento);
		this.estoqueService.movimentarAtendimento(this.produtoAtendimentoService.salvar(this.converterDtoEmProdutoAtendimento(atendimentoDto, atendimento)));
		List<ServicoAtendimentoModel> servicos = this.servicoAtendimentoService.salvar(this.converterDtoEmServicoAtendimento(atendimentoDto, atendimento));
		this.movimentarBrindes(servicos, atendimentoDto);
		this.agendamentosService.salvar(this.converterDtoEmAgendamentos(atendimentoDto, atendimento));
		response.setData(this.converterAtendimentoEmDTO(atendimento, atendimentoDto));
		return ResponseEntity.ok(response);
	}

	private void movimentarBrindes(List<ServicoAtendimentoModel> servicos, @Valid AtendimentoDTO atendimentoDto) {
			for (ServicosAtendimentoDTO servicoAtendimentoDto: atendimentoDto.getServicosAtendimentoDTO()) {
				if (servicoAtendimentoDto.getBrindeAniversario()) {
					ServicoAtendimentoModel servico = new ServicoAtendimentoModel();
					for (ServicoAtendimentoModel servicoAtendimento : servicos) {
						if (servicoAtendimento.getServico().getId() == servicoAtendimentoDto.getServicoId()) {
							servico = servicoAtendimento;
						}
					}
					this.brindeService.disponibilizarBrindeAniversario(servico);
				}
			}
	}

	private List<AgendamentoModel> converterDtoEmAgendamentos(@Valid AtendimentoDTO atendimentoDto,
			AtendimentoModel atendimento) {
		if (!atendimentoDto.getAgendamentoDtos().isEmpty()) {
			List<AgendamentoModel> agendamentos = new ArrayList<AgendamentoModel>();
			for (AgendamentoDTO agendamentoDto : atendimentoDto.getAgendamentoDtos()) {
				AgendamentoModel agendamento = this.agendamentosService.buscarPorId(agendamentoDto.getId()).get();
				agendamento.setAtendimento(atendimento);
				agendamento.setStatus(StatusAgendamento.ObterStatusPorId(agendamentoDto.getStatus()));
				agendamentos.add(agendamento);
			}
			return agendamentos;
		}
		return null;
	}

	private AtendimentoDTO converterAtendimentoEmDTO(AtendimentoModel atendimento, AtendimentoDTO atendimentoDto) {
		return new AtendimentoDTO(atendimento.getId(), atendimento.getFuncionario().getId(),
				atendimento.getCliente().getId(), atendimentoDto.getTipoPagamentoId(), 
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
		for (ServicoAtendimentoModel servicoAtendimento : this.servicoAtendimentoService.buscarPorAtendimentoId(id)) {
			servicosAtendimentoDto.add(new ServicosAtendimentoDTO(servicoAtendimento.getId(), servicoAtendimento.getServico().getId(), 
					servicoAtendimento.getQuantidade(), id, servicoAtendimento.getVal_servico()));
		}
		return servicosAtendimentoDto;
	}

	private List<ServicoAtendimentoModel> converterDtoEmServicoAtendimento(AtendimentoDTO atendimentoDto, AtendimentoModel atendimento) {
		if (!atendimentoDto.getServicosAtendimentoDTO().isEmpty()) {
			List<ServicoAtendimentoModel> servicoAtendimento = new ArrayList<ServicoAtendimentoModel>();
			for (ServicosAtendimentoDTO servicoAtendimentoDto : atendimentoDto.getServicosAtendimentoDTO()) {
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
		return null;
	}

	private List<ProdutoAtendimentoModel> converterDtoEmProdutoAtendimento(AtendimentoDTO atendimentoDto, AtendimentoModel atendimento) {
		if (!atendimentoDto.getProdutosAtendimentoDTO().isEmpty()) {
			List<ProdutoAtendimentoModel> produtosAtendimento = new ArrayList<ProdutoAtendimentoModel>();
			for (ProdutosAtendimentoDTO produtoAtendimentoDto : atendimentoDto.getProdutosAtendimentoDTO()) {
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
		return null;
	}

	private AtendimentoModel converterDTOEmAtendimento(AtendimentoDTO atendimentoDto) {
		ClienteModel cliente = this.clienteService.buscarPorID(atendimentoDto.getClienteId()).get();
		FuncionarioModel funcionario = this.funcionarioService.buscarPorId(atendimentoDto.getFuncionarioId()).get();
		Double valTotalProdutos = this.calcularValorTotalProdutos(atendimentoDto.getProdutosAtendimentoDTO());
		Double valTotalServicos = this.calcularValorTotalServicos(atendimentoDto.getServicosAtendimentoDTO(), cliente.getId());
		Double valDesconto = (valTotalProdutos + valTotalServicos) * (atendimentoDto.getValDesconto() / 100);	
		if (atendimentoDto.getId() != null) {
			return new AtendimentoModel(atendimentoDto.getId(), funcionario, cliente, 
					valDesconto, dataUtils.StringParaDataSpring(atendimentoDto.getDataAtendimento()), new Date(), 
					StatusAtendimento.ObterStatusPorId(atendimentoDto.getSituacao()), valTotalProdutos, valTotalServicos);
		}
		return new AtendimentoModel(funcionario, cliente, valDesconto, 
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
	
	private Double calcularValorTotalProdutos(List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		Double total = new Double(0);
		for (ProdutosAtendimentoDTO produtoAtendimentoDTO : produtosAtendimentoDTO) {
			total += this.produtoService.buscarPorId(produtoAtendimentoDTO.getProdutoId()).get().getValVendido() * produtoAtendimentoDTO.getQuantidade();
		}
		return total;
	}

	private void validarProdutos( AtendimentoDTO atendimentoDto, BindingResult result) {
		if (!atendimentoDto.getProdutosAtendimentoDTO().isEmpty()) {
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
	}
	
	private boolean verificarBrindeFidelidade(ServicoModel servico, Long clienteId, Integer quantidade) {
		BrindeModel brinde = this.brindeService.buscarPorClienteEServico(clienteId, servico.getId()).get();
		if (brinde.getBrindeConfig().getBrindeFidelidade() && (quantidade + brinde.getContadorBrinde())> brinde.getBrindeConfig().getQuantContador()) {
			return true;
		}
		return false;
	}

	private void validarServicos(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		if (!atendimentoDto.getServicosAtendimentoDTO().isEmpty()) {
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
				if (contBrindAniversario > 1) {
					result.addError(new ObjectError("Brinde Aniversário", "Somente um brinde de aniversário por atendimento"));
				}
				else if (servicoAtendimentoDto.getBrindeAniversario()) {
					contBrindAniversario++;
					this.validarBrindeAniversario(atendimentoDto, result);
				}
			}
		}
	}

	private void validarCabecalho(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		if (!this.funcionarioService.buscarPorId(atendimentoDto.getFuncionarioId()).isPresent()) {
			result.addError(new ObjectError("Funcionario", "Funcionário não informado ou não encontrado"));
		}
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
	
	private void validarAgendamentos(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		if (atendimentoDto.getAgendamentoDtos().isEmpty()) {
			for (AgendamentoDTO agendamentoDto : atendimentoDto.getAgendamentoDtos()) {
				Optional<AgendamentoModel> agendamento = this.agendamentosService.buscarPorId(agendamentoDto.getId());
				if (!agendamento.isPresent()) {
					result.addError(new ObjectError("Agendamento", "Agendamento com o id" + agendamentoDto.getId() + " não encontrado"));
				}
				else if (agendamento.get().getStatus() == StatusAgendamento.ATENDIDO) {
					result.addError(new ObjectError("Agendamento", "Agendamento com o id" + agendamentoDto.getId() + " já foi atendido"));
				}
			}
		}
	}
	
	private void verificarStatusAtendimento(@Valid AtendimentoDTO atendimentoDto, BindingResult result) {
		Optional<AtendimentoModel> atendimento = this.atendimentoService.buscarPorId(atendimentoDto.getId());
		if (!atendimento.isPresent()) {
			result.addError(new ObjectError("Atendimento", "Atendimento com o id" + atendimentoDto.getId() + " não encontrado"));
		}
		else if(atendimento.get().getSituacao() == StatusAtendimento.CONSOLIDADO){
			result.addError(new ObjectError("Atendimento", "Atendimento consolidado, não pode ser alterado"));
		}
	}
	
}
