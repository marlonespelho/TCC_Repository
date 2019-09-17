package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.AtendimentoModel;

public interface AtendimentoRepository extends JpaRepository<AtendimentoModel, Long>{

}
