package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;

public class DisciplinaPeriodoDTO extends AbstratoDTO {

	private Integer periodo;

	private Long idDisciplinaBTI;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo entity) {
		this.setId(entity.getId());
		this.setIdDisciplinaBTI(entity.getDisciplina().getId());
		this.setPeriodo(entity.getPeriodo());
	}

	public Long getIdDisciplinaBTI() {
		return idDisciplinaBTI;
	}

	public void setIdDisciplinaBTI(Long idDisciplinaBTI) {
		this.idDisciplinaBTI = idDisciplinaBTI;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
