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
import br.com.integrado.api.entities.BrindeModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.repositories.BrindeRepository;
import br.com.integrado.api.repositories.ClienteRepository;

@Service
public class BrindeService {
	@Autowired
	private BrindeRepository brindeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
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
	
}
