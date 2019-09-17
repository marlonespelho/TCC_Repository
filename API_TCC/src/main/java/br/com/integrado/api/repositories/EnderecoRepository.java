package br.com.integrado.api.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.EnderecoModel;

@NamedQueries({
	@NamedQuery(name = "EnderecoRepository.findByPessoaId",
			query = "SELECT end FROM EnderecoModel end WHERE end.pessoa.id = :pessoaId"),
	@NamedQuery(name = "EnderecoRepository.findByPessoaIdAndCep",
	query = "SELECT end FROM EnderecoModel end WHERE end.pessoa.id = :pessoaId AND end.cep = :cep"),
	@NamedQuery(name = "EnderecoRepository.findByPessoaIdAndCidadeUfSiglaUF",
	query = "SELECT end FROM EnderecoModel end WHERE end.pessoa.id = :pessoaId AND end.cidade.uf.siglaUF = UPPER(:siglaUF)"),
	@NamedQuery(name = "EnderecoRepository.findByPessoaIdAndCidadeNome",
	query = "SELECT end FROM EnderecoModel end WHERE end.pessoa.id = :pessoaId AND UPPER(end.cidade.nome) LIKE %UPPER(:cidadeNome)%")
})

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long>{

	Page<EnderecoModel> findByPessoaId(Pageable pageable,@Param("pessoaId") Long idPessoa);

	Page<EnderecoModel> findByPessoaIdAndCep(Pageable pageable,@Param("pessoaId") Long idPessoa,@Param("cep") String cep);

	Page<EnderecoModel> findByPessoaIdAndCidadeUfSiglaUF(Pageable pageable,@Param("pessoaId") Long idPessoa,@Param("siglaUF") String siglaUF);

	Page<EnderecoModel> findByPessoaIdAndCidadeNome(Pageable pageable,@Param("pessoaId") Long idPessoa,@Param("cidadeNome") String nomeCidade);

}
