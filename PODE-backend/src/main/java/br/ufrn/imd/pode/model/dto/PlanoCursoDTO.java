package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.PlanoCurso;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class PlanoCursoDTO extends AbstractDTO {

	@JsonProperty("id-disciplinas-cursadas")
	private Set<Long> idDisciplinasCursadas = new HashSet<>();

	@JsonProperty("id-disciplinas-pendentes")
	private Set<Long> idDisciplinasPendentes = new HashSet<>();

	@JsonProperty("id-pes-interesse")
	private Set<Long> idPesInteresse = new HashSet<>();

	public PlanoCursoDTO() {
	}

	public PlanoCursoDTO(PlanoCurso planoCurso) {
		this.setId(planoCurso.getId());
		for (DisciplinaPeriodo disciplinaPeriodo : planoCurso.getDisciplinasCursadas()) {
			this.idDisciplinasCursadas.add(disciplinaPeriodo.getId());
		}
		for (DisciplinaPeriodo disciplinaPeriodo : planoCurso.getDisciplinasPendentes()) {
			this.idDisciplinasPendentes.add(disciplinaPeriodo.getId());
		}
		for (Pes pes : planoCurso.getPesInteresse()) {
			this.idPesInteresse.add(pes.getId());
		}
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

	public Set<Long> getIdPesInteresse() {
		return idPesInteresse;
	}

	public void setIdPesInteresse(Set<Long> idPesInteresse) {
		this.idPesInteresse = idPesInteresse;
	}
}
