package br.com.integrado.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.ServicoModel;

public interface ServicoRepository extends JpaRepository<ServicoModel, Long>{

	Page<ServicoModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

	Page<ServicoModel> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

}
