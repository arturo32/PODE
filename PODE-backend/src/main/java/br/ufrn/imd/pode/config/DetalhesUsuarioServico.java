package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.Usuario;
import br.ufrn.imd.pode.modelo.dto.AuthDto;
import br.ufrn.imd.pode.servico.EstudanteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class DetalhesUsuarioServico implements UserDetailsService {

	private EstudanteServico estudanteService;

	public EstudanteServico getEstudanteService() {
		return estudanteService;
	}

	@Autowired
	public void setEstudanteService(EstudanteServico estudanteService) {
		this.estudanteService = estudanteService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario;
		try {
			usuario = estudanteService.buscarPorEmail(username);
		} catch (EntidadeNaoEncontradaException enfe) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return new AuthDto(usuario.getNome(), usuario.getId(), usuario.getEmail(), usuario.getSenha(), authorities(usuario));
	}

	public Collection<? extends GrantedAuthority> authorities(Usuario usuario) {
		Collection<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		return auths;
	}
}
