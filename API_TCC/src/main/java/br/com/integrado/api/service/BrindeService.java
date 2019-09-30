package br.com.integrado.api.service;


import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.BrindeGanhoModel;
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.entities.ServicoAtendimentoModel;
import br.com.integrado.api.enums.TipoBrinde;
import br.com.integrado.api.repositories.BrindeRepository;
import br.com.integrado.api.repositories.ClienteRepository;

@Service
public class BrindeService {
	@Autowired
	private BrindeRepository brindeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired 
	private BrindeGanhoService brindeGanhoService;
	
	public BrindeModel salvar(BrindeModel brinde) {
		return this.brindeRepository.save(brinde);
	}
	
	public void delete(Long id) {
		this.brindeRepository.deleteById(id);
	}
	
	public Optional<BrindeModel> buscarPorId(Long id){
		return this.brindeRepository.findById(id);
	}
	
	public Page<BrindeModel> buscarTodos(PageRequest pageRequest){
		return this.brindeRepository.findAll(pageRequest);
	}
	
	public Page<BrindeModel> buscarPorCliente(PageRequest pageRequest, Long id){
		return this.brindeRepository.findByClienteId(pageRequest, id);
	}
	
	public List<BrindeModel> buscarPorCliente(Long id){
		return this.brindeRepository.findByClienteId(id);
	}
	
	public Optional<BrindeModel> buscarPorClienteEServico(Long clienteId, Long servicoId){
		return this.brindeRepository.findByClienteIdAndServicoId(clienteId, servicoId);
	}
	
	public Boolean verificarBrindeAniversario(Long clienteId) {
		List<BrindeModel> brindes = this.buscarPorCliente(clienteId);
		ClienteModel cliente = this.clienteRepository.findById(clienteId).get();
		GregorianCalendar aniversario = new GregorianCalendar();
		aniversario.setTime(cliente.getDtNascimento());
		aniversario.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		for (BrindeModel brinde : brindes) {
			GregorianCalendar ultimoBrinde = new GregorianCalendar();
			ultimoBrinde.setTime(brinde.getDataBrindeAniversario());
			if (!brinde.getBrindeConfig().getBrindeAniversario()) {
				return false;
			}
			if (ultimoBrinde.get(Calendar.YEAR) >= aniversario.get(Calendar.YEAR)) {
				return false;
			}
		}
		return true;
	}
	
	public void movimentarBrindes(List<ServicoAtendimentoModel> servicosAtendimento) {
		for (ServicoAtendimentoModel servicoAtendimento : servicosAtendimento) {
			BrindeModel brinde = this.buscarPorClienteEServico(servicoAtendimento.getAtendimento().getCliente().getId(), 
												servicoAtendimento.getServico().getId()).get();
			if (brinde.getBrindeConfig().getBrindeFidelidade() || !this.verificarBrindeAniversarioExistente(servicoAtendimento)) {
				if ((brinde.getContadorBrinde() + servicoAtendimento.getQuantidade()) > brinde.getBrindeConfig().getQuantContador()) {
					int quantBrinde = (brinde.getContadorBrinde() + servicoAtendimento.getQuantidade()) / 
										brinde.getBrindeConfig().getQuantContador();
					brinde.setContadorBrinde(((brinde.getContadorBrinde() + servicoAtendimento.getQuantidade()) % 
												brinde.getBrindeConfig().getQuantContador()) - quantBrinde);
					this.salvar(brinde);
					this.brindeGanhoService.salvar(new BrindeGanhoModel(brinde, TipoBrinde.FIDELIDADE, servicoAtendimento, quantBrinde));
				}
				else if(this.verificarBrindeAniversarioExistente(servicoAtendimento) && servicoAtendimento.getQuantidade() > 1) {
					brinde.setContadorBrinde(brinde.getContadorBrinde() + servicoAtendimento.getQuantidade() - 1);
					this.salvar(brinde);
				}
				else{
					brinde.setContadorBrinde(brinde.getContadorBrinde() + servicoAtendimento.getQuantidade());
					this.salvar(brinde);	
				}
			}
		}
	}
	
	public Boolean verificarBrindeAniversarioExistente(ServicoAtendimentoModel servicoAtendimento) {
		Optional<BrindeGanhoModel> brindeGanho = this.brindeGanhoService.buscarPorServicoAtendimentoId(servicoAtendimento.getId());
		if (!brindeGanho.isPresent()) {
			return false;
		} else if (brindeGanho.get().getTipoBrinde() == TipoBrinde.ANIVERSARIO) {
			return true;
		}
		return false;
	}

	public void disponibilizarBrindeAniversario( ServicoAtendimentoModel servicoAtendimento) {
		BrindeModel brinde = this.buscarPorClienteEServico(servicoAtendimento.getAtendimento().getCliente().getId(), 
															servicoAtendimento.getServico().getId()).get();
		this.brindeGanhoService.salvar(new BrindeGanhoModel(brinde, TipoBrinde.ANIVERSARIO, servicoAtendimento, 1));
	}
	
}
