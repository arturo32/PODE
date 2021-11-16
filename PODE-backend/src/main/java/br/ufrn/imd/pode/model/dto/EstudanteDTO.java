package br.ufrn.imd.pode.model.dto;

import java.util.Set;

public class EstudanteDTO extends AbstractDTO {

	private Set<VinculoDTO> vinculos;

	public Set<VinculoDTO> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Set<VinculoDTO> vinculos) {
		this.vinculos = vinculos;
	}
}
