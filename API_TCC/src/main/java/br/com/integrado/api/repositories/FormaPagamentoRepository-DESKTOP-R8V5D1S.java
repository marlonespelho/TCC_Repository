package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.FormaPagamentoModel;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Long>{

}
