package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.ServicoModel;

public interface ServicoRepository extends JpaRepository<ServicoModel, Long>{

}
