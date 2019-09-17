package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.BrindeModel;

public interface BrindeRepository extends JpaRepository<BrindeModel, Long>{

}
