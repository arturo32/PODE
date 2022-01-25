package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.GradeCurricular;

import java.util.Set;

public class Pes extends GradeCurricular {
	private Integer chm;

	private Integer cho;

	private Set<DisciplinaBTI> disciplinasOptativas;

	@Override
	public Integer getChm() {
		return chm;
	}

	@Override
	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Set<DisciplinaBTI> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaBTI> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
