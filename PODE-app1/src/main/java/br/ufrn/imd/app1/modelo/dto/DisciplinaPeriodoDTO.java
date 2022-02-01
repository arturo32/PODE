package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;

public class DisciplinaPeriodoDTO extends AbstratoDTO {

	private Integer periodo;

	private Long idDisciplinaBTI;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo entity) {
		super(entity);
		this.setPeriodo(entity.getPeriodo());
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
