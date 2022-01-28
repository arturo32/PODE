package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.GradeCurricular;

import java.util.Set;
import java.util.stream.Collectors;

public class GradeCurricularDTO extends AbstratoDTO {
	private String nome;
	private Integer chm;
	private Set<Long> disciplinas;

	public GradeCurricularDTO(GradeCurricular entity) {
		this.setId(entity.getId());
		this.setNome(entity.getNome());
		this.setChm(entity.getChm());
		this.setDisciplinas(entity.getDisciplinas().stream().map(Disciplina::getId).collect(Collectors.toSet()));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Set<Long> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Long> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
