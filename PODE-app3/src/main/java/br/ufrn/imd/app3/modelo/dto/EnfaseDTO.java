package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;

import br.ufrn.imd.app3.modelo.Enfase;

public class EnfaseDTO extends GradeCurricularDTO {

	private Long cursoId;

	public EnfaseDTO() {
	}

	public EnfaseDTO(Enfase entity) {
		super(entity);
		this.setCursoId(entity.getCursoBTI().getId());
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}
}
