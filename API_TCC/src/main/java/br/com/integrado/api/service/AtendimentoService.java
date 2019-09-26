package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.AtendimentoModel;
import br.com.integrado.api.repositories.AtendimentoRepository;

@Service
public class AtendimentoService {
	@Autowired
	private AtendimentoRepository atendimentoRepository;
	
	public AtendimentoModel salvar(AtendimentoModel atendimentoModel) {
		return this.atendimentoRepository.save(atendimentoModel);
	}
	
	public void deletar(Long id) {
		this.atendimentoRepository.deleteById(id);
	}
	
	public Optional<AtendimentoModel> buscarPorId(Long id){
		return this.atendimentoRepository.findById(id);
	}
}
