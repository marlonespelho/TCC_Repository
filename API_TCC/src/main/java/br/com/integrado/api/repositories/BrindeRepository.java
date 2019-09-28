package br.com.integrado.api.repositories;


import java.util.List;
import java.util.Optional;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.BrindeModel;
@NamedQueries({
	@NamedQuery(name = "BrindeRepository.findByClienteId", 
			query = "SELECT b FROM BrindeModel b WHERE b.cliente.id = :idCliente"),
	@NamedQuery(name = "BrindeRepository.findByClienteIdAndServicoId", 
	query = "SELECT b FROM BrindeModel b WHERE b.cliente.id = :idCliente AND b.servico.id = :idServico")
})
public interface BrindeRepository extends JpaRepository<BrindeModel, Long>{

	Page<BrindeModel> findByClienteId(Pageable pageable, @Param("idCliente")Long id);
	Optional<BrindeModel> findByClienteIdAndServicoId(@Param("idCliente") Long clienteId, @Param("idServico") Long servicoId);
	List<BrindeModel> findByClienteId(Long idCliente);

}
