package br.ufrn.imd.pode.model.dto;


public abstract class UsuarioDTO extends AbstractDTO {

	protected String nome;
	protected String email;

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

}
