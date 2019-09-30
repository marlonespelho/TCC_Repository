package br.com.integrado.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.dtos.ServicosAtendimentoDTO;
import br.com.integrado.api.entities.ServicoAtendimentoModel;
import br.com.integrado.api.repositories.ServicoAtendimentoRepository;

@Service
public class ServicoAtendimentoService {
	@Autowired
	private ServicoAtendimentoRepository servicoAtendimentoRepository;
	
	public  ServicoAtendimentoModel salvar(ServicoAtendimentoModel servicoAtendimento) {
		return this.servicoAtendimentoRepository.save(servicoAtendimento);
	}
	
	public  List<ServicoAtendimentoModel> salvar(List<ServicoAtendimentoModel>  servicosAtendimento) {
		if (servicosAtendimento != null) {
			if (servicosAtendimento.get(0).getAtendimento() != null) {
				this.deletarServicosForaDaLista(servicosAtendimento);
			}
			List<ServicoAtendimentoModel> servicosAtendimentoModel = new ArrayList<ServicoAtendimentoModel>();
			for (ServicoAtendimentoModel servicoAtendimento : servicosAtendimento) {
				servicosAtendimentoModel.add(this.servicoAtendimentoRepository.save(servicoAtendimento));
			}
			return servicosAtendimentoModel;
		}
		return null;
	}
	
	private void deletarServicosForaDaLista(List<ServicoAtendimentoModel> servicosAtendimento) {
		List<ServicoAtendimentoModel> servicosSalvos = this.buscarPorAtendimentoId(servicosAtendimento.get(0).getAtendimento().getId());
		for (ServicoAtendimentoModel servicoSalvo : servicosSalvos) {
			Boolean validador = false;
			int cont = 1;
			for (ServicoAtendimentoModel servico : servicosAtendimento) {
				if (servico.getId() == servicoSalvo.getId()) {
					validador = true;
					break;
				}
				if (cont == servicosSalvos.size() && !validador) {
					this.deletar(servicoSalvo);
					break;
				}
				cont++;
			}
			validador = false;
			cont = 1;
		}
		}

	public void deletar(Long id) {
		this.servicoAtendimentoRepository.deleteById(id);
	}
	
	public void deletar(ServicoAtendimentoModel servicoAtendimento) {
		this.servicoAtendimentoRepository.delete(servicoAtendimento);
	}

	public Optional<ServicoAtendimentoModel> buscarPorId(Long id){
		return this.servicoAtendimentoRepository.findById(id);
	}
	
	public Page<ServicoAtendimentoModel> buscarPorAtendimentoId(PageRequest pageRequest,Long id){
		return this.servicoAtendimentoRepository.findByAtendimentoId(pageRequest, id);
	}
	
	public List<ServicoAtendimentoModel> buscarPorAtendimentoId(Long id){
		return this.servicoAtendimentoRepository.findByAtendimentoId(id);
	}

}
