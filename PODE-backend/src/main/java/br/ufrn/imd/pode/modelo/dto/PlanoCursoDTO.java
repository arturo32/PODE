package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

public class PlanoCursoDTO extends AbstratoDTO {

	@JsonProperty("id-disciplinas-cursadas")
	private Set<Long> idDisciplinasCursadas;

	@JsonProperty("id-disciplinas-pendentes")
	private Set<Long> idDisciplinasPendentes;

	public PlanoCursoDTO() {
	}

	public PlanoCursoDTO(PlanoCurso planoCurso) {
		this.setId(planoCurso.getId());
		this.setIdDisciplinasCursadas(planoCurso.getDisciplinasCursadas().stream().map(DisciplinaCursada::getId).collect(Collectors.toSet()));
		this.setIdDisciplinasPendentes(planoCurso.getDisciplinasPendentes().stream().map(DisciplinaCursada::getId).collect(Collectors.toSet()));
	}

	public Set<Long> getIdDisciplinasCursadas() {
		return idDisciplinasCursadas;
	}

	public void setIdDisciplinasCursadas(Set<Long> idDisciplinasCursadas) {
		this.idDisciplinasCursadas = idDisciplinasCursadas;
	}

	public Set<Long> getIdDisciplinasPendentes() {
		return idDisciplinasPendentes;
	}

	public void setIdDisciplinasPendentes(Set<Long> idDisciplinasPendentes) {
		this.idDisciplinasPendentes = idDisciplinasPendentes;
	}
}
