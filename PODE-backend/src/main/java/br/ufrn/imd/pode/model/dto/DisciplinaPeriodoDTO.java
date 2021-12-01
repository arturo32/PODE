package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;

public class DisciplinaPeriodoDTO extends AbstractDTO {

	private DisciplinaDTO disciplina;

	private Integer periodo;

	public DisciplinaPeriodoDTO(DisciplinaPeriodo disciplinaPeriodo) {
		this.setId(disciplinaPeriodo.getId());
		this.setDisciplina(new DisciplinaDTO(disciplinaPeriodo.getDisciplina()));
		this.setPeriodo(disciplinaPeriodo.getPeriodo());
	}

	public DisciplinaDTO getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaDTO disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
