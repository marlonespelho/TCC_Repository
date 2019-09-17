package br.com.integrado.api.repositories;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.ProdutoModel;
@NamedQueries({
	@NamedQuery(name = "ProdutoRepository.findByMarcaId",
			query = "SELECT p FROM ProdutoModel p WHERE p.marca.id = :id"),
	@NamedQuery(name = "ProdutoRepository.findByMarcaDescricaoContainingIgnoreCase",
	query = "SELECT p FROM ProdutoModel p WHERE UPPER(p.marca.descricao) LIKE UPPER(:descricao)"),
	@NamedQuery(name = "ProdutoRepository.findByDescricaoContainingIgnoreCaseAndMarcaId",
	query = "SELECT p FROM ProdutoModel p WHERE UPPER(p.descricao) = UPPER(:descricao) AND p.marca.id = :idMarca")
})
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

	Page<ProdutoModel> findByMarcaId(Pageable pageable,@Param("id") Long id);

	Page<ProdutoModel> findByMarcaDescricaoContainingIgnoreCase(Pageable pageable,@Param("descricao") String descricao);

	Page<ProdutoModel> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

	Optional<ProdutoModel> findByDescricaoContainingIgnoreCaseAndMarcaId(@Param("descricao") String descricao,@Param("idMarca") Long idMarca);

	Page<ProdutoModel> findByInAtivo(Pageable pageable, Boolean inAtivo);

	Page<ProdutoModel> findByCodBarrasContainingIgnoreCase(Pageable pageable, String codigo);
	
}
