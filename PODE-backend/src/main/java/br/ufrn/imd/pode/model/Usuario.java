package br.ufrn.imd.pode.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Usuario extends AbstractModel<Long> {

	@NotNull
	@NotBlank
	@Column(unique = true)
	protected String nome;

	@NotNull
	@NotBlank
	@Column(unique = true)
	protected String email;

	@NotNull
	@NotBlank
	protected String senha;

	public Usuario() {
	}

	public Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}