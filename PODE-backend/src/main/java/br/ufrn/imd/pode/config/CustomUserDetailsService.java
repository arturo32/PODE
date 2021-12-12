package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.model.Usuario;
import br.ufrn.imd.pode.model.dto.AuthDto;
import br.ufrn.imd.pode.service.AdministradorService;
import br.ufrn.imd.pode.service.EstudanteService;
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
public class CustomUserDetailsService implements UserDetailsService {

	private EstudanteService estudanteService;

	private AdministradorService administradorService;

	public EstudanteService getEstudanteService() {
		return estudanteService;
	}

	@Autowired
	public void setEstudanteService(EstudanteService estudanteService) {
		this.estudanteService = estudanteService;
	}

	public AdministradorService getAdministradorService() {
		return administradorService;
	}

	@Autowired
	public void setAdministradorService(AdministradorService administradorService) {
		this.administradorService = administradorService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario;
		try {
			usuario = estudanteService.findByEmail(username);
		} catch (EntityNotFoundException enfe) {
			try {
				usuario = administradorService.findByEmail(username);
			} catch (EntityNotFoundException enfe2) {
				throw new UsernameNotFoundException("Usuário não encontrado!");
			}
		}
		return new AuthDto(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), authorities(usuario));
	}

	public Collection<? extends GrantedAuthority> authorities(Usuario usuario) {
		Collection<GrantedAuthority> auths = new ArrayList<>();

		if (usuario instanceof Administrador) {
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return auths;
	}
}
