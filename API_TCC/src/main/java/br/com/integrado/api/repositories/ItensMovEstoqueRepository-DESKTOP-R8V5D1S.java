package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.ItensMovEstoqueModel;

public interface ItensMovEstoqueRepository extends JpaRepository<ItensMovEstoqueModel, Long> {

}
