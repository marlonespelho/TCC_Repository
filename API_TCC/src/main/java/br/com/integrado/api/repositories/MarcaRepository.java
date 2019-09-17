package br.com.integrado.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.MarcaModel;

public interface MarcaRepository extends JpaRepository<MarcaModel, Long> {

	Page<MarcaModel> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

	Page<MarcaModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

}
