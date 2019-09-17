package br.com.integrado.api.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.CidadeModel;

@NamedQueries({
	@NamedQuery(name = "CidadeRepository.findByUfId",
			query = "SELECT c FROM CidadeModel c WHERE c.uf.id =:idUF"),
	@NamedQuery(name = "CidadeRepository.findByUfSiglaUF",
	query = "SELECT c FROM CidadeModel c WHERE c.uf.siglaUF =:siglaUF"),
	@NamedQuery(name = "CidadeRepository.findByUfSiglaUF",
	query = "SELECT c FROM CidadeModel c WHERE UPPER(c.UF.descUF) LIKE UPPER(:descUF)"),
	@NamedQuery(name = "CidadeRepository.findByUfIdAndNome",
	query = "SELECT c FROM CidadeModel c WHERE c.uf.id =:idUF AND UPPER(c.nome) LIKE %UPPER(:nome)%"),
	@NamedQuery(name = "CidadeRepository.findByUfDescUF",
	query = "SELECT c FROM CidadeModel c WHERE c.uf.descUF =:descUF")
})
public interface CidadeRepository extends JpaRepository<CidadeModel, Integer> {
	
	Page<CidadeModel> findByNomeContainingIgnoreCase(Pageable pageable, String nome);
	Page<CidadeModel> findByUfIdAndNomeContainingIgnoreCase(Pageable pageable,@Param("idUF")Integer id,@Param("nome") String nome);
	Page<CidadeModel> findByUfId(@Param("idUF")Integer id, Pageable pageable);
	Page<CidadeModel> findByUfSiglaUF(@Param("siglaUF")String sigla, Pageable pageable);
	Page<CidadeModel> findByUfDescUF(@Param("descUF")String desc, Pageable pageable);
}
