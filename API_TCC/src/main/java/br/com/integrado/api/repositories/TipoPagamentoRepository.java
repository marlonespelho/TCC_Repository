package br.com.integrado.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.TipoPagamentoModel;

public interface TipoPagamentoRepository extends JpaRepository<TipoPagamentoModel, Long>{

	Page<TipoPagamentoModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

	Page<TipoPagamentoModel> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

}
