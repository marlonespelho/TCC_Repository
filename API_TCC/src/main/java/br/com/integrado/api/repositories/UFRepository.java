package br.com.integrado.api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.UFModel;

public interface UFRepository extends JpaRepository<UFModel, Integer>{

	Optional<UFModel> findBySiglaUF(String sigla);

	Page<UFModel> findByDescUFContainingIgnoreCase(Pageable pageable, String desc);
	
	Page<UFModel> findAll(Pageable pageable);
}
