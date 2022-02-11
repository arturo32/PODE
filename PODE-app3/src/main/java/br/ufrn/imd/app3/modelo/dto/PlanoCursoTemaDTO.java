package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.app3.modelo.Tema;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;

import br.ufrn.imd.app3.modelo.PlanoCursoTema;

import java.util.Set;
import java.util.stream.Collectors;

public class PlanoCursoTemaDTO extends PlanoCursoDTO {

	private Set<Long> temas;

	public PlanoCursoTemaDTO() {
	}

	public PlanoCursoTemaDTO(PlanoCursoTema planoCurso) {
		super(planoCurso);
		temas = planoCurso.getTemas().stream().map(Tema::getId).collect(Collectors.toSet());
	}

	public Set<Long> getTemas() {
		return temas;
	}

	public void setTemas(Set<Long> temas) {
		this.temas = temas;
	}
}
