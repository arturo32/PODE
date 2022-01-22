package br.ufrn.imd.pode.modelo.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthDto extends User {

	private static final long serialVersionUID = 7523446953160941833L;

	private final String nome;

	private final Long id;

	public AuthDto(String nome, Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.nome = nome;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}
}
