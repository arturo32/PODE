package br.ufrn.imd.pode.model.dto;

import java.util.Set;

public class PlanoCursoDTO extends AbstractDTO {

	private Set<DisciplinaPeriodoDTO> disciplinasCursadas;

	private Set<DisciplinaPeriodoDTO> disciplinasPendentes;

	private Set<PesDTO> pesInteresse;

	public Set<DisciplinaPeriodoDTO> getDisciplinasCursadas() {
		return disciplinasCursadas;
	}

	public void setDisciplinasCursadas(Set<DisciplinaPeriodoDTO> disciplinasCursadas) {
		this.disciplinasCursadas = disciplinasCursadas;
	}

	public Set<DisciplinaPeriodoDTO> getDisciplinasPendentes() {
		return disciplinasPendentes;
	}

	public void setDisciplinasPendentes(Set<DisciplinaPeriodoDTO> disciplinasPendentes) {
		this.disciplinasPendentes = disciplinasPendentes;
	}

	public Set<PesDTO> getPesInteresse() {
		return pesInteresse;
	}

	public void setPesInteresse(Set<PesDTO> pesInteresse) {
		this.pesInteresse = pesInteresse;
	}
}
