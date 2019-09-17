package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.ServicoAtendimentoModel;

public interface ServicoAtendimentoRepository extends JpaRepository<ServicoAtendimentoModel, Long> {

}
