package br.com.integrado.api.repositories;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.ContatoModel;

@NamedQueries({
	@NamedQuery(name = "ContatoRepository.findByPessoaId",
			query = "SELECT cont FROM ContatoModel cont WHERE cont.pessoa.id = :pessoaId"),
	@NamedQuery(name = "ContatoRepository.findByPessoaIdAndNumTelefone",
	query = "SELECT cont FROM ContatoModel cont WHERE cont.pessoa.id = :pessoaId and cont.numTelefone = :numTelefone"),
	@NamedQuery(name = "ContatoRepository.findByPessoaIdAndEmail",
	query = "SELECT cont FROM ContatoModel cont WHERE cont.pessoa.id = :pessoaId and cont.email = :email"),
	@NamedQuery(name = "ContatoRepository.findByIdAndPessoaId",
	query = "SELECT cont FROM ContatoModel cont WHERE cont.pessoa.id = :pessoaId and cont.id = :id")
})
public interface ContatoRepository extends JpaRepository<ContatoModel, Long> {
	Optional<ContatoModel> findByIdAndPessoaId (@Param("pessoaId")Long pessoaId, @Param("id")Long id);
	Page<ContatoModel> findByPessoaId(@Param("pessoaId") Long pessoaId, Pageable pegeable);
	Page<ContatoModel> findByPessoaIdAndNumTelefoneContaining(@Param("pessoaId") Long pessoaId, @Param("numTelefone") String numTelefone,Pageable pegeable);
	Page<ContatoModel> findByPessoaIdAndEmailContaining(@Param("pessoaId") Long pessoaId, @Param("email") String email ,Pageable pegeable);
}
