package br.ufrn.imd.app3.modelo.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.modelo.dto.VinculoDTO;

import br.ufrn.imd.app3.modelo.VinculoPlataforma;
import br.ufrn.imd.app3.modelo.Tema;

public class VinculoPlataformaTO extends VinculoDTO {
	private Set<Long> temasInteresse;

	public VinculoPlataformaTO(VinculoPlataforma vinculo) {
		super(vinculo);
		this.temasInteresse = vinculo.getTemasInteresse().stream().map(Tema::getId).collect(Collectors.toSet());
	}

	public Set<Long> getTemasInteresse() {
		return temasInteresse;
	}

	public void setTemasInteresse(Set<Long> temasInteresse) {
		this.temasInteresse = temasInteresse;
	}
}
