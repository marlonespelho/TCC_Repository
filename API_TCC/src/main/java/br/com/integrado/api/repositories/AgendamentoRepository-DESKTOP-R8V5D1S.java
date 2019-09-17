package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.AgendamentoModel;

public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long>{

}
