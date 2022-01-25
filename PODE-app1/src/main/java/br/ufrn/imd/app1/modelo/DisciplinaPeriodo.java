package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.Disciplina;

public class DisciplinaPeriodo extends Disciplina {
	private DisciplinaBTI disciplina;

	private Integer periodo;

	public DisciplinaBTI getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaBTI disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
