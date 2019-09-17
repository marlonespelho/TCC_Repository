package br.com.integrado.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.FuncionarioModel;
import br.com.integrado.api.security.JwtUserFactory;
import br.com.integrado.api.service.FuncionarioService;
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<FuncionarioModel> funcionario = this.funcionarioService.buscarPorUsuario(username);
		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}
		throw new UsernameNotFoundException("Usuário não encontrado");
	}

}
