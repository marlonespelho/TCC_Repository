package br.com.integrado.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.FormaPagamentoModel;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Long>{

	Page<FormaPagamentoModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

	Page<FormaPagamentoModel> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

	Page<FormaPagamentoModel> findByQntParcelas(Pageable pageable, Integer qntParcelas);

}
