package br.ufrn.imd.pode.model.dto;

public class DisciplinaPeriodoDTO {

	private Long id;

	private DisciplinaDTO disciplina;

	private Integer periodo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
