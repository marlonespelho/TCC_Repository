package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.AtendimentoModel;

public interface AtendimentoRepository extends JpaRepository<AtendimentoModel, Long>{

}
