package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, Long> {
	
}
