package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.PlanoCurso;

import java.util.HashSet;
import java.util.Set;

public class PlanoCursoDTO extends AbstractDTO {

	private Set<DisciplinaPeriodoDTO> disciplinasCursadas = new HashSet<>();

	private Set<DisciplinaPeriodoDTO> disciplinasPendentes = new HashSet<>();

	private Set<PesDTO> pesInteresse = new HashSet<>();

	public PlanoCursoDTO() {
	}

	public PlanoCursoDTO(PlanoCurso planoCurso) {
		this.setId(planoCurso.getId());
		for (DisciplinaPeriodo disciplinaPeriodo : planoCurso.getDisciplinasCursadas()) {
			this.disciplinasCursadas.add(new DisciplinaPeriodoDTO(disciplinaPeriodo));
		}
		for (DisciplinaPeriodo disciplinaPeriodo : planoCurso.getDisciplinasPendentes()) {
			this.disciplinasPendentes.add(new DisciplinaPeriodoDTO(disciplinaPeriodo));
		}
		for (Pes pes : planoCurso.getPesInteresse()) {
			this.pesInteresse.add(new PesDTO(pes));
		}
	}

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
