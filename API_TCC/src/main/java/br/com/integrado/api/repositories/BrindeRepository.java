package br.com.integrado.api.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.BrindeModel;
@NamedQueries({
	@NamedQuery(name = "BrindeRepository.findByClienteId", 
			query = "SELECT b FROM BrindeModel b WHERE b.cliente.id")
})
public interface BrindeRepository extends JpaRepository<BrindeModel, Long>{

	Page<BrindeModel> findByClienteId(Pageable pageable, Long id);

}
