package br.ufrn.imd.app3.modelo.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;

import br.ufrn.imd.app3.modelo.Curso;
import br.ufrn.imd.app3.modelo.Tema;

public class CursoDTO extends GradeCurricularDTO {
	private Integer chm;
	private Set<Long> temas;

	public CursoDTO() {}

	public CursoDTO(Curso curso) {
		super(curso);
		this.chm = curso.getChm();
		this.temas = curso.getTemas().stream().map(Tema::getId).collect(Collectors.toSet());
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Set<Long> getTemas() {
		return temas;
	}

	public void setTemas(Set<Long> temas) {
		this.temas = temas;
	}
}
