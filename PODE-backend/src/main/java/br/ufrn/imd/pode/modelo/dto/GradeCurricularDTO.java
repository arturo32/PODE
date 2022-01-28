package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;

import java.util.Set;
import java.util.stream.Collectors;

public class GradeCurricularDTO extends AbstratoDTO {
	private String nome;
	private Integer chobm;
	private Integer chopm;
	private Set<Long> disciplinasObrigatorias;
	private Set<Long> disciplinasOptativas;

	public GradeCurricularDTO(GradeCurricular entity) {
		this.setId(entity.getId());
		this.setNome(entity.getNome());
		this.setChobm(entity.getChobm());
		this.setChopm(entity.getChopm());
		this.setDisciplinasObrigatorias(entity.getDisciplinasObrigatorias().stream().map(DisciplinaInterface::getId).collect(Collectors.toSet()));
		this.setDisciplinasOptativas(entity.getDisciplinasOptativas().stream().map(DisciplinaInterface::getId).collect(Collectors.toSet()));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChobm() {
		return chobm;
	}

	public void setChobm(Integer chobm) {
		this.chobm = chobm;
	}

	public Integer getChopm() {
		return chopm;
	}

	public void setChopm(Integer chopm) {
		this.chopm = chopm;
	}

	public Set<Long> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<Long> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<Long> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<Long> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
