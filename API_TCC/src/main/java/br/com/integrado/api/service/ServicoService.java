package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.ServicoModel;
import br.com.integrado.api.repositories.ServicoRepository;

@Service
public class ServicoService {
	@Autowired
	private ServicoRepository servicoRepository;
	
	public ServicoModel salvar(ServicoModel servico) {
		return this.servicoRepository.save(servico);
	}
	
	public void deletar(Long id) {
		this.servicoRepository.deleteById(id);
	}
	
	public Optional<ServicoModel> buscarPorId(Long id) {
		return this.servicoRepository.findById(id);
	}
	
	public Page<ServicoModel> buscarTodos(PageRequest pageRequest){
		return this.servicoRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<ServicoModel> buscarInativos(PageRequest pageRequest){
		return this.servicoRepository.findByInAtivo(pageRequest, false);
	}
	
	public Page<ServicoModel> buscarPorDescricao(PageRequest pageRequest, String descricao){
		return this.servicoRepository.findByDescricaoContainingIgnoreCase(pageRequest, descricao);
	}
	
	
}
