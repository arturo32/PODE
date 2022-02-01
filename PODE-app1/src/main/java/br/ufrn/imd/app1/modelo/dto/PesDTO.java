package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;

import br.ufrn.imd.app1.modelo.Pes;

public class PesDTO extends GradeCurricularDTO {

	private Integer chm;

	public PesDTO() {
	}

	public PesDTO(Pes entity) {
		super(entity);
		this.setChm(entity.getChm());
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}
}
