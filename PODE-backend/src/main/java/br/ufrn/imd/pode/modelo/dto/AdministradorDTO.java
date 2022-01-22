package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Administrador;

public class AdministradorDTO extends UsuarioDTO {

	public AdministradorDTO() {
	}

	public AdministradorDTO(Administrador administrador) {
		this.setId(administrador.getId());
		this.setNome(administrador.getNome());
		this.setEmail(administrador.getEmail());
		this.setSenha(administrador.getSenha());
	}
}
