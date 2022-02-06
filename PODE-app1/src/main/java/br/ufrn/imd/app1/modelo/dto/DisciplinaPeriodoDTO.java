package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

public class DisciplinaPeriodoDTO extends DisciplinaCursadaDTO {

	private Integer periodo;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo entity) {
		super(entity);
		this.setPeriodo(entity.getPeriodo());
	}

	public DisciplinaPeriodoDTO(Long id, int periodo) {
		setDisciplinaId(id);
		this.periodo = periodo;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
