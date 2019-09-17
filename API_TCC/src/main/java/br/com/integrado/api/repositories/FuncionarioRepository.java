package br.com.integrado.api.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.CPFModel;
import br.com.integrado.api.entities.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long>{
	FuncionarioModel findByUsuarioAndInAtivo(String usuario, Boolean inAtivo);

	Page<FuncionarioModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

	Optional<FuncionarioModel> findByCpf(CPFModel cpfModel);

	Page<FuncionarioModel> findByNomeContainingIgnoreCase(Pageable pageable, String nome);

	Optional<FuncionarioModel> findByCpfAndIdNot(CPFModel cpfModel, Long id);
}
