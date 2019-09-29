package br.com.integrado.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.BrindeGanhoModel;
import br.com.integrado.api.repositories.BrindeGanhoRepository;

@Service
public class BrindeGanhoService {

	@Autowired
	private BrindeGanhoRepository brindeGanhoRepository;

	public BrindeGanhoModel salvar(BrindeGanhoModel brindeGanho) {
		return brindeGanhoRepository.save(brindeGanho);
	}
	
	public Optional<BrindeGanhoModel> buscarPorServicoAtendimentoId(Long id) {
		return this.brindeGanhoRepository.findByServicoAtendimentoId(id);
	}
	
}
