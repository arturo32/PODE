package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;

public class DisciplinaPeriodoDTO extends AbstractDTO {

	private Long disciplina;

	private Integer periodo;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo disciplinaPeriodo) {
		this.setId(disciplinaPeriodo.getId());
		this.setDisciplina(disciplinaPeriodo.getDisciplina().getId());
		this.setPeriodo(disciplinaPeriodo.getPeriodo());
	}

	public Long getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Long disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
