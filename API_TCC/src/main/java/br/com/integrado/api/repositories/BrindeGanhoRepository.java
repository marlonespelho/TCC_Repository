package br.com.integrado.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.BrindeGanhoModel;

public interface BrindeGanhoRepository extends JpaRepository<BrindeGanhoModel, Long>{

	Optional<BrindeGanhoModel> findByServicoAtendimentoId(Long id);

}
