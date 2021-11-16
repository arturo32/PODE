package br.ufrn.imd.pode.model.dto;

public class DisciplinaPeriodoDTO extends AbstractDTO {

	private DisciplinaDTO disciplina;

	private Integer periodo;

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
