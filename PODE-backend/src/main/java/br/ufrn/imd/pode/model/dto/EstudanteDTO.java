package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Vinculo;

import java.util.Set;

public class EstudanteDTO {

	private Long id;

	private Set<VinculoDTO> vinculos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<VinculoDTO> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Set<VinculoDTO> vinculos) {
		this.vinculos = vinculos;
	}
}
