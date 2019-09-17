package br.com.integrado.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.CPFModel;
import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public Optional<FuncionarioModel> buscarPorUsuario(String usuario){
		return Optional.ofNullable(this.funcionarioRepository.findByUsuarioAndInAtivo(usuario, true));
	}
	
	public Optional<FuncionarioModel> buscarPorId(Long id){
		return this.funcionarioRepository.findById(id);
	}
	
	public FuncionarioModel salvar(FuncionarioModel funcionario) {
		return this.funcionarioRepository.save(funcionario);
	}
	
	public void deletar(Long id) {
		this.funcionarioRepository.deleteById(id);
	}
	
	public Page<FuncionarioModel> buscarTodos(PageRequest pageRequest){
		return this.funcionarioRepository.findByInAtivo(pageRequest, true);
	}
	
	public Page<FuncionarioModel> buscarInativos(PageRequest pageRequest){
		return this.funcionarioRepository.findByInAtivo(pageRequest, false);
	}
	
	public Optional<FuncionarioModel> buscarPorCPF(String cpf){
		return this.funcionarioRepository.findByCpf(new CPFModel(cpf));
	}
	
	public Page<FuncionarioModel> buscarPorNome(PageRequest pageRequest, String nome){
		return this.funcionarioRepository.findByNomeContainingIgnoreCase(pageRequest, nome);
	}

	public Optional<FuncionarioModel> buscarPorCPF(Long id, String cpf) {
		return this.funcionarioRepository.findByCpfAndIdNot(new CPFModel(cpf), id);
	}
	
}
