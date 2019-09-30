package br.com.integrado.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.AtendimentoModel;
import br.com.integrado.api.entities.FormaPagamentoModel;
import br.com.integrado.api.entities.ParcelaAtendimentoModel;
import br.com.integrado.api.entities.TipoPagamentoModel;
import br.com.integrado.api.repositories.ParcelaAtendimentoRepository;

@Service
public class ParcelaAtendimentoService {

	@Autowired
	private ParcelaAtendimentoRepository parcelaAtendimentoRepository;
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	@Autowired
	private TipoPagamentoService tipoPagamentoService;
	@Autowired
	private AtendimentoService atendimentoService;
	
	public void gerarParcelas(AtendimentoModel atendimento, Long idTpPagamento, Long idFmPagamento) {
		List<ParcelaAtendimentoModel> parcelas = new ArrayList<>();
		FormaPagamentoModel formaPagamento =this.formaPagamentoService.buscarPorId(idFmPagamento).get();
		TipoPagamentoModel tipoPagamento = this.tipoPagamentoService.buscarPorId(idTpPagamento).get();
		Double valParcela = (atendimento.getValTotalProdutos() + atendimento.getValTotalServicos() + atendimento.getValDesconto())
								/ formaPagamento.getQntParcelas();
		GregorianCalendar dtVencimento = new GregorianCalendar();
		dtVencimento.setTime(new Date());
		for (int i = 0; i < formaPagamento.getQntParcelas(); i++) {
			dtVencimento.add(Calendar.DAY_OF_YEAR, dtVencimento.get(Calendar.DAY_OF_YEAR) + (formaPagamento.getQntDias() * i));
			parcelas.add(new ParcelaAtendimentoModel(atendimento, dtVencimento.getTime(), valParcela, new Double(0), formaPagamento, tipoPagamento));
		}
		if (formaPagamento.getEntrada()) {
			parcelas.get(0).setDtPago(dtVencimento.getTime());
			parcelas.get(0).setValPago(valParcela);
		}
		this.salvar(parcelas);
	}

	private void salvar(List<ParcelaAtendimentoModel> parcelas) {
		Double valPago = new Double(0);
		for (ParcelaAtendimentoModel parcela : parcelas) {
			this.parcelaAtendimentoRepository.save(parcela);
			valPago += parcela.getValPago();
		}	
		if (this.verificarFinalPagamento(parcelas.get(0).getAtendimento(), valPago)) {
			parcelas.get(0).getAtendimento().setDataFinalPago(new Date());
			this.atendimentoService.salvar(parcelas.get(0).getAtendimento());
		}
		
	}

	private boolean verificarFinalPagamento(AtendimentoModel atendimento, Double valPago) {
		if ((atendimento.getValTotalProdutos() + atendimento.getValTotalServicos() - atendimento.getValDesconto()) == valPago) {
			return true;
		}
		return false;
	}
	
}
