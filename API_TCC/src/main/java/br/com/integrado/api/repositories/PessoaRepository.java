package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.PessoaModel;

public interface PessoaRepository extends JpaRepository<PessoaModel, Long>{

}
