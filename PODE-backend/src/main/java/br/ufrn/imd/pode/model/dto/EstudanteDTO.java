package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.Vinculo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EstudanteDTO extends UsuarioDTO {

	private Set<Long> vinculos = new HashSet<>();

	public EstudanteDTO() {
	}

	public EstudanteDTO(Estudante estudante) {
		this.setId(estudante.getId());
		this.setNome(estudante.getNome());
		this.setEmail(estudante.getEmail());
		this.setSenha(estudante.getSenha());
		this.setVinculos(estudante.getVinculos().stream().map(Vinculo::getId).collect(Collectors.toSet()));
	}

	public Set<Long> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Set<Long> vinculos) {
		this.vinculos = vinculos;
	}
}
