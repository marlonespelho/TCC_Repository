package br.com.integrado.api.repositories;


import java.util.Date;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.CPFModel;
import br.com.integrado.api.entities.ClienteModel;

@NamedQueries({
	@NamedQuery(name = "ClienteRepository.findAll",
			query = "SELECT c FROM ClienteModel c")
})
public interface ClienteRepository extends JpaRepository<ClienteModel, Long>{
	ClienteModel findByCpf(CPFModel cpf);
	Page<ClienteModel> findByNomeContainingIgnoreCase(Pageable pageable, String nome);
	Page<ClienteModel> findByDtNascimento(Pageable pageable, Date dtNascimento);	
	Page<ClienteModel> findAll(Pageable pageable);
	ClienteModel findByCpfAndIdNot(CPFModel cpf, Long id);
	Page<ClienteModel> findByInAtivo(Pageable pageable, Boolean inAtivo);
}
