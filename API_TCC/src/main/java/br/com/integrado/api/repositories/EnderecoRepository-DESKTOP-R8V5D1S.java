package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long>{

}
