package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.AgendaModel;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long>{

}
