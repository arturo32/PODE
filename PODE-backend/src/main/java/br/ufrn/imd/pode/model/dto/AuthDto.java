package br.ufrn.imd.pode.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthDto extends User {

	private String nome;

	public AuthDto(String nome, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.nome = nome;
	}

	public AuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public String getNome() {
		return nome;
	}
}
