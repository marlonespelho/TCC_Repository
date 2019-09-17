package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.UFModel;

public interface UFRepository extends JpaRepository<UFModel, Integer>{
	
}
