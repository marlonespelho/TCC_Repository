package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.CidadeModel;

public interface CidadeRepository extends JpaRepository<CidadeModel, Integer> {
	
}
