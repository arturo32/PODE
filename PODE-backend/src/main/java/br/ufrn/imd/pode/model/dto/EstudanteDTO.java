package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.Vinculo;

import java.util.HashSet;
import java.util.Set;

public class EstudanteDTO extends UsuarioDTO {

	private Set<VinculoDTO> vinculos = new HashSet<>();

	public EstudanteDTO(Estudante estudante) {
		this.setId(estudante.getId());
		this.setNome(estudante.getNome());
		this.setEmail(estudante.getEmail());
		for(Vinculo vinculo : estudante.getVinculos()){
			this.vinculos.add(new VinculoDTO(vinculo));
		}
	}

	public Set<VinculoDTO> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Set<VinculoDTO> vinculos) {
		this.vinculos = vinculos;
	}
}
