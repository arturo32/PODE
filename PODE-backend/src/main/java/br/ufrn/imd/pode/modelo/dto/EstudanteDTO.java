package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Estudante;
import br.ufrn.imd.pode.modelo.Vinculo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EstudanteDTO extends UsuarioDTO {

	@JsonProperty("id-vinculos")
	private Set<Long> idVinculos;

	public EstudanteDTO() {
	}

	public EstudanteDTO(Estudante estudante) {
		this.setId(estudante.getId());
		this.setNome(estudante.getNome());
		this.setEmail(estudante.getEmail());
		this.setSenha(estudante.getSenha());
		this.setIdVinculos(estudante.getVinculos().stream().map(Vinculo::getId).collect(Collectors.toSet()));
	}

	public Set<Long> getIdVinculos() {
		return idVinculos;
	}

	public void setIdVinculos(Set<Long> idVinculos) {
		this.idVinculos = idVinculos;
	}
}
