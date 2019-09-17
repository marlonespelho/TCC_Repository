package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long>{
	
}
