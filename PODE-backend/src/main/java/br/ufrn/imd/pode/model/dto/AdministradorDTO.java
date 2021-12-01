package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Administrador;

public class AdministradorDTO extends UsuarioDTO {

	public AdministradorDTO(Administrador administrador) {
		this.setId(administrador.getId());
		this.setNome(administrador.getNome());
		this.setEmail(administrador.getEmail());
	}
}
