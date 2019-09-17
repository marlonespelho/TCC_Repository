package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.MarcaModel;

public interface MarcaRepository extends JpaRepository<MarcaModel, Long> {

}
