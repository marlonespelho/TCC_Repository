package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.MovEstoqueModel;

public interface MovEstoqueRepository extends JpaRepository<MovEstoqueModel, Long> {

}
