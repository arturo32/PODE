package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;

public class DisciplinaPeriodoDTO extends DisciplinaBTIDTO {

	private Integer periodo;

	public DisciplinaPeriodoDTO(DisciplinaPeriodo entity) {
		super(entity.getDisciplina());
		this.setPeriodo(entity.getPeriodo());
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
