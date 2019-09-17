package br.com.integrado.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.CPFModel;
import br.com.integrado.api.entities.ClienteModel;
import br.com.integrado.api.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired(required = true)
	private ClienteRepository clienteRepository;
	
	public ClienteModel salvar(ClienteModel cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	public void deletar(Long id) {
		this.clienteRepository.deleteById(id);;
	}
	
	public Optional<ClienteModel> buscarPorID(Long id) {
		return this.clienteRepository.findById(id);
	}
	
	public Optional<ClienteModel> buscarPorCPF(String cpf){
		return Optional.ofNullable(this.clienteRepository.findByCpf(new CPFModel(cpf)));
	}
	
	public Optional<ClienteModel> buscarPorCPF(Long id, String cpf){
		return Optional.ofNullable(this.clienteRepository.findByCpfAndIdNot(new CPFModel(cpf), id));
	}
	
	public Page<ClienteModel> buscarPorNome(PageRequest pageRequest, String nome) {
		return this.clienteRepository.findByNomeContainingIgnoreCase(pageRequest, nome);
	}
	
	public Page<ClienteModel> buscarPorDtNascimento(PageRequest pageRequest, Date data){
		return this.clienteRepository.findByDtNascimento(pageRequest, data);
	}
	
	public List<ClienteModel> BuscarTodos(){
		return this.clienteRepository.findAll();
	}
	
	public Page<ClienteModel> BuscarTodos(PageRequest pageRequest){
		return this.clienteRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<ClienteModel> BuscarInativos(PageRequest pageRequest){
		return this.clienteRepository.findByInAtivo(pageRequest, false);
	}
}
