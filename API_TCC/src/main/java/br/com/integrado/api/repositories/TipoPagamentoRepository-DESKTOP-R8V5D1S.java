package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.TipoPagamentoModel;

public interface TipoPagamentoRepository extends JpaRepository<TipoPagamentoModel, Long>{

}