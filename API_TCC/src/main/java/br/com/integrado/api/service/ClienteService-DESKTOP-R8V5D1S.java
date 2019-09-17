package br.com.integrado.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.models.CPFModel;
import br.com.integrado.api.models.ClienteModel;
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
	
	public ClienteModel buscarPorID(Long id) {
		return this.clienteRepository.getOne(id);
	}
	
	public ClienteModel buscarPorCPF(String cpf){
		return this.clienteRepository.findByCpf(new CPFModel(cpf));
	}
	
	public ClienteModel buscarPorCPF(Long id, String cpf){
		return this.clienteRepository.findByCpfAndIdNot(new CPFModel(cpf), id);
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
		return this.clienteRepository.findAll(pageRequest);
	}
}
